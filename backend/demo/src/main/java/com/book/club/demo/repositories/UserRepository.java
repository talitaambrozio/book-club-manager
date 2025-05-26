package com.book.club.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.club.demo.models.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

}
