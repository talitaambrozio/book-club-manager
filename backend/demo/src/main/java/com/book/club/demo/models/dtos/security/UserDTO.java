package com.book.club.demo.models.dtos.security;



import java.util.List;
import java.util.UUID;

import com.book.club.demo.enums.Role;

public record UserDTO(UUID id, String username, List<Role> roles) {
}
