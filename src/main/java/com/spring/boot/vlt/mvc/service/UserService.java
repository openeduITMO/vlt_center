package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.entity.Role;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.UserRole;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.repository.UserRepository;
import com.spring.boot.vlt.mvc.repository.UserRoleRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    public Optional<User> getUserByLoginisPresent(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login));
    }

    public User getUserByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login)).orElseThrow(() -> new NullPointerException("User with login {" + login + "} not found"));
    }

    public boolean saveUser(User user) {
        Optional<User> saveUser = Optional.ofNullable(userRepository.save(user));
        saveUser.orElseThrow(() -> new HibernateException("exception while saving user: " + user.toString()));
        return saveUser.isPresent();
    }

    public boolean setRoleForUser(String login, Role role) {
        UserRole userRole = new UserRole(getUserByLogin(login).getId(), role);
        return Optional.ofNullable(userRoleRepository.save(userRole)).isPresent();
    }

    public Set<Role> getAllRoleForUser(Long userId){
        Optional.ofNullable(userRepository.findOne(userId)).orElseThrow(() -> new NullPointerException("User with id {" + userId + "} not found"));
        return userRoleRepository.findAllRoleForUser(userId);
    }

    public Set<VirtLab> getUserVirtLabs(String userLogin){
        return userRepository.getUserVirtLabs(userLogin);
    }

    @Transactional
    public VirtLab foundVlByDirUnderUser(String userLogin, String dirName){
        User user = getUserByLogin(userLogin);
        return user.getLabs().stream().filter(vl -> dirName.equals(vl.getDirName())).findFirst().orElseThrow(() ->
                new NullPointerException("User with login = " + userLogin + " not contain vl = " + dirName));
    }

}
