package com.spring.boot.vlt.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    protected Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "labs_register",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "labs_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<VirtLab> register = new HashSet<>();

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long id, String username, String password, Role role) {
        this.id = id;
        this.login = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<VirtLab> getRegister() {
        return register;
    }

    public void setRegister(Set<VirtLab> register) {
        this.register = register;
    }

    public void addRegister(VirtLab register) {
        this.register.add(register);
    }

    public void deleteRegister(User Register) {
        this.register.remove(Register);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
