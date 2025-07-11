package com.gabrieldears.talent_forge.domain.repository;

public interface CustomUserRepository {

    boolean emailAlreadyExists(String email);

    boolean emailAlreadyExistsForAnotherUser(String email, String id);

    boolean userExists(String userId);

}
