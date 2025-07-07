package com.book.club.demo.services;

import com.book.club.demo.controllers.dtos.request.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.book.club.demo.models.ModelUserDetailsImpl;
import com.book.club.demo.controllers.dtos.security.JwtTokenDTO;
import com.book.club.demo.controllers.dtos.response.UserResponseDTO;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;
    
    public JwtTokenDTO authenticateUser(UserRequestDTO loginUserRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserRequestDto.username(), loginUserRequestDto.password());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl userDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }
}
