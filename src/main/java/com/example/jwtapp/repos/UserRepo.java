package com.example.jwtapp.repos;

import com.example.jwtapp.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername (String username);
    AppUser getByUsername(String username);
}
