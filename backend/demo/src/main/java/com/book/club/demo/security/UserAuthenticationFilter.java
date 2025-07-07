package com.book.club.demo.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.book.club.demo.models.ModelUserDetailsImpl;
import com.book.club.demo.models.User;
import com.book.club.demo.repositories.UserRepository;
import com.book.club.demo.services.JwtTokenService;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkPublicEndpoints(request)) {
            String token = retrieveToken(request);
            if (token != null) {
                String subject = jwtTokenService.getToken(token);
                User user = usuarioRepository.findByUsername(subject).get();
                ModelUserDetailsImpl modelUserDetails = new ModelUserDetailsImpl(user);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                modelUserDetails.getUsername(),
                                null,
                                modelUserDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new RuntimeException("Token does not exist!");
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkPublicEndpoints(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
       // return !Arrays.asList("/api/v1/login", "/api/v1/users").contains(requestURI);
        // Allow all endpoints under "/api/v1/login" to be public
        if (requestURI.startsWith("/api/v1/login")) {
            return false;
        }

        // Allow only POST requests to "/api/v1/users" to be public
        if (requestURI.equals("/api/v1/users") && method.equals("POST")) {
            return false;
        }

        // All other endpoints require authentication
        return true;
    }

    private String retrieveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/login/**");
    }


}
