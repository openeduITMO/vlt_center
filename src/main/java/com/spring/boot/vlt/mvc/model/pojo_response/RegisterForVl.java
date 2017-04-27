package com.spring.boot.vlt.mvc.model.pojo_response;

import rlcp.check.CheckingResult;

import java.util.Date;


public class RegisterForVl {

    private String login;
    private ResultInfo info;


    public RegisterForVl(String login, Date startDate, String session, CheckingResult result) {
        this.login = login;
        this.info = new ResultInfo(startDate, session, result);
    }

    public RegisterForVl() {
    }

    public ResultInfo getInfo() {
        return info;
    }

    public void setInfo(ResultInfo info) {
        this.info = info;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
