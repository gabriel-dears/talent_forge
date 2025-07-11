package com.gabrieldears.talent_forge.application.service;

import com.gabrieldears.talent_forge.domain.repository.CustomUserRepository;
import com.gabrieldears.talent_forge.domain.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final CustomUserRepository customUserRepository;

    public UserServiceImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public boolean emailAlreadyExists(String email) {
        return customUserRepository.emailAlreadyExists(email);
    }

    @Override
    public boolean emailAlreadyExistsForAnotherUser(String email, String id) {
        return customUserRepository.emailAlreadyExistsForAnotherUser(email, id);
    }

    @Override
    public boolean userExists(String userId) {
        return customUserRepository.userExists(userId);
    }
}
