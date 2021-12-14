package com.example.jwtapp.repos;

import com.example.jwtapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository <Role, Long> {

    Optional<Role> findByName(String role);
}
