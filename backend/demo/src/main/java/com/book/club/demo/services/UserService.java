package com.book.club.demo.services;


import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.book.club.demo.enums.Role;
import com.book.club.demo.models.ModelRole;
import com.book.club.demo.models.ModelUserDetailsImpl;
import com.book.club.demo.models.User;
import com.book.club.demo.models.dtos.UserDTO;
import com.book.club.demo.models.dtos.security.JwtTokenDTO;
import com.book.club.demo.models.dtos.security.LoginUserDTO;
import com.book.club.demo.repositories.ModelRoleRepository;
import com.book.club.demo.repositories.UserRepository;
import com.book.club.demo.security.SecurityConfig;
import com.book.club.demo.exceptions.BadRequestException;
import com.book.club.demo.exceptions.ConflictException;
import com.book.club.demo.exceptions.ResourceNotFoundException;


@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ModelRoleRepository modelRoleRepository;

    @Transactional
    public String saveUser(UserDTO userDTO){

        boolean userAlreadyRegistered = userRepository
            .findByUsername(userDTO.username()).isPresent();
        if(userAlreadyRegistered){
            throw new ConflictException("User already registered.");
        }

        ModelRole role = modelRoleRepository.findByName(Role.ROLE_USER);

        User newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setPassword(securityConfig.passwordEncoder().encode(userDTO.password()));
        newUser.setRoles(List.of(role));

        userRepository.save(newUser);

        return "User registered successfully.";
    }

    @Transactional
    public String updatePassword(UserDTO userDTO){

        User user = userRepository
            .findByUsername(userDTO.username()).get();

        if(user == null){
            throw new ResourceNotFoundException("User not found.");
        }

        if(!user.getPassword().equals(userDTO.password())){
            throw new BadRequestException("Invalid username or password.");
        }
  
        user.setPassword(securityConfig.passwordEncoder().encode(userDTO.password()));

        userRepository.save(user);

        return "Password updated successfully.";
    }

    public JwtTokenDTO authenticateUser(LoginUserDTO loginUserDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.username(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl userDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

}
