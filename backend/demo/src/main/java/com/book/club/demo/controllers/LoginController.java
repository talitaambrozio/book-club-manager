package com.book.club.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.book.club.demo.models.dtos.UserDTO;
import com.book.club.demo.models.dtos.security.JwtTokenDTO;
import com.book.club.demo.models.dtos.security.LoginUserDTO;
import com.book.club.demo.services.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
