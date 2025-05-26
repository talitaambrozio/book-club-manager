package com.book.club.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.club.demo.models.dtos.UserDTO;
import com.book.club.demo.services.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO dto){
        return new ResponseEntity<String>(userService.saveUser(dto),
                HttpStatus.CREATED);
    }

}
