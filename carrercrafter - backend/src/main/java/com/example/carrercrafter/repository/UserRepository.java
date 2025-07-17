package com.example.carrercrafter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.carrercrafter.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}
