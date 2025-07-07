package com.book.club.demo.controllers;


import com.book.club.demo.controllers.dtos.request.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.book.club.demo.controllers.dtos.security.JwtTokenDTO;
import com.book.club.demo.services.AuthenticationService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody UserRequestDTO loginDTO) {
        JwtTokenDTO token = authService.authenticateUser(loginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
