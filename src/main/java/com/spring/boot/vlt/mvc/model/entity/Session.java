package com.spring.boot.vlt.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "attempts")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "labs_id")
    @JsonIgnore
    private VirtLab lab;

    @Column(name = "session", nullable = false)
    private String session;

    public Session() {
    }

    public Session(User user, VirtLab lab, String session) {
        this.user = user;
        this.lab = lab;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VirtLab getLab() {
        return lab;
    }

    public void setLab(VirtLab lab) {
        this.lab = lab;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
