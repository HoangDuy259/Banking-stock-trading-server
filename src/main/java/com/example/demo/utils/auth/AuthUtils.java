package com.example.demo.utils.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    //    hàm lấy username từ token
    public String getAuthenticationUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String sub = jwt.getSubject(); // tương đương getClaim("sub")
            username = jwt.getClaimAsString("preferred_username");

            System.out.println("sub = " + sub);
            System.out.println("username = " + username);
        }
        return username;
    }

//    hàm lấy userId từ token
//    public Long getAuthenticationUserId(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long userId = null;
//        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//            Jwt jwt = jwtAuth.getToken();
//            Object claim = jwt.getClaim("userId");
//            if (claim != null) {
//                return Long.valueOf(claim.toString());
//            }
//        }
//        return null;
//    }
}
