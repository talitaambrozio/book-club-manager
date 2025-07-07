package com.book.club.demo.controllers;

import com.book.club.demo.controllers.dtos.request.UserRequestDTO;
import com.book.club.demo.models.User;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.book.club.demo.controllers.dtos.response.UserResponseDTO;
import com.book.club.demo.services.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO dto){

        UserResponseDTO response = userService.saveUser(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(response.userId())
                .toUri();
        return new ResponseEntity<UserResponseDTO>(response,
                HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        return new ResponseEntity<>(userService.findAllUsers(),
                HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("userId") UUID userId){
        UserResponseDTO userResponseDTO = userService.findUserById(userId);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updatePassword(@PathVariable("userId") UUID userId, @RequestBody UserRequestDTO userDTO) {
        String response = userService.updatePassword(userId, userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") UUID userId) {
        String response = userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
