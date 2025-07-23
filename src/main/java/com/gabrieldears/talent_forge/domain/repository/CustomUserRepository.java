package com.gabrieldears.talent_forge.domain.repository;

import com.gabrieldears.talent_forge.domain.model.User;

import java.util.Optional;

public interface CustomUserRepository {

    boolean emailAlreadyExists(String email);

    boolean emailAlreadyExistsForAnotherUser(String email, String id);

    boolean userExists(String userId);

    Optional<User> findByEmail(String email);

}
