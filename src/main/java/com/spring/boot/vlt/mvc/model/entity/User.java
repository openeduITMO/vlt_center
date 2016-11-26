package com.spring.boot.vlt.mvc.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="login")
    private String login;
    
    @Column(name="password")
    private String password;
    
    @OneToMany
    @JoinColumn(name="user_id", referencedColumnName="ID")
    private List<UserRole> roles;
    
    public User() { }
    
    public User(Long id, String username, String password, List<UserRole> roles) {
        this.id = id;
        this.login = username;
        this.password = password;
        this.roles = roles;
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

    public List<UserRole> getRoles() {
        return roles;
    }
}
