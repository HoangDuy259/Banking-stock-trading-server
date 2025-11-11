package com.example.demo.service.auth;

import com.example.demo.dto.IdentityClient;
import com.example.demo.dto.identity.ClientTokenExchangeParam;
import com.example.demo.dto.identity.KeycloakProvider;
import com.example.demo.dto.identity.LogoutRequest;
import com.example.demo.dto.identity.TokenExchangeResponse;
import com.example.demo.dto.request.auth.ForgotPassword;
import com.example.demo.dto.request.auth.LoginRequest;
import com.example.demo.dto.request.auth.UserRegisterRequest;
import com.example.demo.dto.response.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService implements IAuthService{
    UserMapper userMapper;
    UserRepository userRepository;
    KeycloakProvider keycloakProvider;
    IdentityClient identityClient;
    PasswordEncoder passwordEncoder;
//    dùng cho reset password
    private final OtpRedisService otpRedisService;
    private final EmailService emailService;

//    đăng ký
    public UserResponse register(UserRegisterRequest userRegisterRequest) {
        boolean exists = userRepository.existsByUsername(userRegisterRequest.getUsername());
        log.info("Check username exists: " + exists);
        if (exists) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }
        if (userRepository.existsByUsername(userRegisterRequest.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        log.info("in a method");

        User user = userMapper.toUser(userRegisterRequest);
        log.info("user: " + userRegisterRequest.getUsername());
        log.info("user: " + user.getUsername());
        log.info("email: " + user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("password: " + user.getPassword());
        user.setEnabled(true);
        log.info("register successed: " + userRepository.count());
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

//    đăng nhập
    public TokenExchangeResponse login(LoginRequest loginRequest) throws AuthenticationException {
        User user = userRepository.findUserByEmail(loginRequest.getEmail()).orElseThrow( () ->
                new AuthenticationException("User not found")
        );

        // Kiểm tra nếu tài khoản bị vô hiệu hóa
        if (!user.getEnabled()) {
            throw new DisabledException("Tài khoản chưa được kích hoạt.");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Sai mật khẩu");
        }

        // Gửi request đến Keycloak để lấy token
        return identityClient.exchangeTokenClient(ClientTokenExchangeParam.builder()
                .grant_type("password")
                .client_id(keycloakProvider.getClientID())
                .client_secret(keycloakProvider.getClientSecret())
                .username(user.getUsername())
                .password(loginRequest.getPassword())
                .scope("openid")
                .build());
    }

    public void logout(String refreshToken) {
        LogoutRequest param = LogoutRequest.builder()
                .client_id(keycloakProvider.getClientID())
                .client_secret(keycloakProvider.getClientSecret())
                .refresh_token(refreshToken)
                .build();

        identityClient.logout(param);
    }

//    reset password
public void sendOtp(String email) {
    User user = userRepository.findUserByEmail(email)
            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

    String otp = String.format("%06d", new Random().nextInt(999999));

    // lưu OTP vào Redis 5 phút
    otpRedisService.saveOtp(user.getEmail(), otp, 5);

    // gửi otp
    emailService.sendOtpEmail(user.getEmail(), otp);
}

    public boolean resetPassword(ForgotPassword request) {
        System.out.println("request" + request);
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        String cachedOtp = otpRedisService.getOtp(request.getEmail());

        if (cachedOtp == null || !cachedOtp.equals(request.getOtp())) {
            return false; // OTP sai hoặc đã hết hạn
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        otpRedisService.deleteOtp(request.getEmail());
        return true;
    }

    public void verifyCredentials(String email, String password) throws AuthenticationException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User không tồn tại"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Sai mật khẩu");
        }
    }
}
