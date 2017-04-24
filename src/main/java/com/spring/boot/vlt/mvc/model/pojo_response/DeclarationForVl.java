package com.spring.boot.vlt.mvc.model.pojo_response;

import rlcp.check.CheckingResult;

import java.util.Date;


public class DeclarationForVl {

    private String login;
    private Date startDate;
    private String session;
    private CheckingResult result;

    public DeclarationForVl(String login, Date startDate, String session, CheckingResult result) {
        this.login = login;
        this.startDate = startDate;
        this.session = session;
        this.result = result;
    }

    public DeclarationForVl() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public CheckingResult getResult() {
        return result;
    }

    public void setResult(CheckingResult result) {
        this.result = result;
    }
}
