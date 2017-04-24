package com.spring.boot.vlt.mvc.model;

import com.spring.boot.vlt.mvc.model.entity.Attempts;

import java.util.ArrayList;

public class Test {
    private String login;
    private ArrayList<Attempts> attemptses;

    public Test(String login, ArrayList<Attempts> attemptses) {
        this.login = login;
        this.attemptses = attemptses;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Attempts> getAttemptses() {
        return attemptses;
    }

    public void setAttemptses(ArrayList<Attempts> attemptses) {
        this.attemptses = attemptses;
    }
}
