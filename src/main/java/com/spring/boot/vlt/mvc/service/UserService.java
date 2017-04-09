package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.entity.Role;
import com.spring.boot.vlt.mvc.model.entity.User;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.repository.UserRepository;
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


    public Role getRoleForUser(Long userId){
        User user = userRepository.findOne(userId);
        Optional.ofNullable(user).orElseThrow(() -> new NullPointerException("User with id {" + userId + "} not found"));
        return user.getRole();
    }

    @Transactional
    public Set<VirtLab> getUserVirtLabs(String userLogin){
        return userRepository.getUserVirtLabs(userLogin);
    }

    @Transactional
    public VirtLab foundVlByDirUnderUser(String userLogin, String dirName){
        return getUserVirtLabs(userLogin).stream().filter(vl -> dirName.equals(vl.getDirName())).findFirst().orElseThrow(() ->
                new NullPointerException("User with login = " + userLogin + " not contain vl = " + dirName));
    }

}
