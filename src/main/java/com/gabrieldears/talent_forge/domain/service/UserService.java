package com.gabrieldears.talent_forge.domain.service;

public interface UserService {

    boolean emailAlreadyExists(String email);

    boolean emailAlreadyExistsForAnotherUser(String email, String id);

    boolean userExists(String userId);

}
