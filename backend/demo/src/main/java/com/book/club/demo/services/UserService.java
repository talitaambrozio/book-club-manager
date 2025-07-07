package com.book.club.demo.services;


import com.book.club.demo.controllers.dtos.mapper.UserMapper;
import com.book.club.demo.controllers.dtos.request.UserRequestDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.club.demo.enums.Role;
import com.book.club.demo.models.ModelRole;
import com.book.club.demo.models.User;
import com.book.club.demo.controllers.dtos.response.UserResponseDTO;
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
    private ModelRoleRepository modelRoleRepository;

    @Transactional
    public UserResponseDTO saveUser(UserRequestDTO userDTO){

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

        return UserMapper.INSTANCE.userToDTO(newUser);
    }

    @Transactional
    public String updatePassword(UUID userId, UserRequestDTO userRequestDTO){

        User user = userRepository
            .findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if(!user.getPassword().equals(userRequestDTO.password())){
            throw new BadRequestException("Invalid username or password.");
        }
  
        user.setPassword(securityConfig.passwordEncoder().encode(userRequestDTO.password()));

        userRepository.save(user);

        return "Password updated successfully.";
    }

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.INSTANCE.usersToDTOs(users);
    }

    public UserResponseDTO findUserById(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        return UserMapper.INSTANCE.userToDTO(user);
    }

    @Transactional
    public String deleteUser(UUID userID){
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        userRepository.delete(user);
        return "User deleted successfully.";
    }
}
