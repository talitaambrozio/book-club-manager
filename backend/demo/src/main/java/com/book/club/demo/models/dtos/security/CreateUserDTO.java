package com.book.club.demo.models.dtos.security;

import com.book.club.demo.enums.Role;

public record CreateUserDTO(String username, String password, Role role) {
}
