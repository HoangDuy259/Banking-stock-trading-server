package com.example.demo.security;

import com.example.demo.exception.AppException;
import com.example.demo.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtils jwtUtils, @Lazy UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);
                if (jwtUtils.validateToken(token)) {
                    String username = jwtUtils.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            handleError(response, new AppException.TokenExpired("Token has expired"));
        }
        catch (JwtException ex) {
            handleError(response, new AppException.InvalidToken("Invalid token"));
        }
    }

    private void handleError(HttpServletResponse response, AppException ex) throws IOException {
        response.setStatus(ex.getStatus().value());
        response.setContentType("application/json");
        GlobalExceptionHandler.ErrorResponse errorResponse =
                new GlobalExceptionHandler.ErrorResponse(
                        ex.getStatus().value(),
                        ex.getCode(),
                        ex.getMessage()
                );
        String json = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}