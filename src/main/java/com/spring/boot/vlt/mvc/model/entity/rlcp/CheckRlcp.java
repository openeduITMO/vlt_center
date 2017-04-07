package com.spring.boot.vlt.mvc.model.entity.rlcp;

import rlcp.check.CheckingResult;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "check")
public class CheckRlcp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "session", nullable = false)
    private String session;

    @Column(name = "response", nullable = false)
    private CheckingResult response;

    @Column(name = "date", nullable = false)
    private Date date;

    public CheckRlcp() {
    }

    public CheckRlcp(String session, CheckingResult response, Date date) {
        this.session = session;
        this.response = response;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public CheckingResult getResponse() {
        return response;
    }

    public void setResponse(CheckingResult response) {
        this.response = response;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
