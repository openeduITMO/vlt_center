package com.spring.boot.vlt.mvc.model;

import com.spring.boot.vlt.mvc.model.entity.Session;

import java.util.ArrayList;

public class Test {
    private String login;
    private ArrayList<Session> attemptses;

    public Test(String login, ArrayList<Session> attemptses) {
        this.login = login;
        this.attemptses = attemptses;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Session> getAttemptses() {
        return attemptses;
    }

    public void setAttemptses(ArrayList<Session> attemptses) {
        this.attemptses = attemptses;
    }
}
