package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.entity.Role;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.UserRole;
import com.spring.boot.vlt.mvc.repository.UserRepository;
import com.spring.boot.vlt.mvc.repository.UserRoleRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    public Optional<User> getUserByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login));
    }

    public boolean saveUser(User user) {
        Optional<User> saveUser = Optional.ofNullable(userRepository.save(user));
        saveUser.orElseThrow(() -> new HibernateException("exception while saving user: " + user.toString()));

        return saveUser.isPresent();
    }

    public boolean setRoleForUser(Long userId, Role role) {
        Optional.ofNullable(userRepository.findOne(userId)).orElseThrow(() -> new NullPointerException("User with id {" + userId + "} not found"));

        UserRole userRole = new UserRole(userId, role);
        return Optional.ofNullable(userRoleRepository.save(userRole)).isPresent();
    }

    public Set<Role> getAllRoleForUser(Long userId){
        Optional.ofNullable(userRepository.findOne(userId)).orElseThrow(() -> new NullPointerException("User with id {" + userId + "} not found"));

        return userRoleRepository.findAllRoleForUser(userId);

    }

}
