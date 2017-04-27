package com.spring.boot.vlt.mvc.model.entity.rlcp;

import com.spring.boot.vlt.mvc.model.entity.Session;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attempt")
    private Session attempt;

    @Column(name = "response", nullable = false)
    private CheckingResult response;

    @Column(name = "request", nullable = false)
    private String request;

    @Column(name = "date", nullable = false)
    private Date date;

    public CheckRlcp() {
    }

    public CheckRlcp(Session attempt, String request, CheckingResult response, Date date) {
        this.attempt = attempt;
        this.request = request;
        this.response = response;
        this.date = date;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Long getId() {
        return id;
    }

    public Session getAttempts() {
        return attempt;
    }

    public void setAttempts(Session attempt) {
        this.attempt = attempt;
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

    @Override
    public String toString() {
        return "CheckRlcp{" +
                "attempt=" + attempt +
                ", request=" + request +
                ", response=" + response +
                ", date=" + date +
                '}';
    }
}
