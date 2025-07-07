package com.book.club.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.club.demo.enums.Role;
import com.book.club.demo.models.ModelRole;

import java.util.UUID;

public interface ModelRoleRepository extends JpaRepository<ModelRole, UUID> {

    ModelRole findByName(Role name);
}
