package com.spring.boot.vlt.mvc.model.entity.rlcp;

import com.spring.boot.vlt.mvc.model.entity.Attempts;
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
    private Attempts attempt;

    @Column(name = "response", nullable = false)
    private CheckingResult response;

//    @Column(name = "request", nullable = false)
//    private CheckingRe request;

    @Column(name = "date", nullable = false)
    private Date date;

    public CheckRlcp() {
    }

    public CheckRlcp(Attempts attempt, CheckingResult response, Date date) {
        this.attempt = attempt;
        this.response = response;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Attempts getSession() {
        return attempt;
    }

    public void setSession(Attempts attempt) {
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
                ", response=" + response +
                ", date=" + date +
                '}';
    }
}
