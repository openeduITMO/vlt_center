package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserByLogin(String login){
        return Optional.of(userRepository.findByLogin(login));
    }
}
