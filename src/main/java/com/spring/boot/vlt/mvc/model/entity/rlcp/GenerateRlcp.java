package com.spring.boot.vlt.mvc.model.entity.rlcp;

import rlcp.generate.GeneratingResult;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "generate")
public class GenerateRlcp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "session", nullable = false)
    private String session;

    @Column(name = "response", nullable = false)
    private GeneratingResult response;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "parent", nullable = true)
    private Long parent;

    public GenerateRlcp() {
    }

    public GenerateRlcp(String session, GeneratingResult response, Date date) {
        this.session = session;
        this.response = response;
        this.date = date;
    }

    public GenerateRlcp(String session, GeneratingResult response, Date date, Long idParent) {
        this.parent = idParent;
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

    public GeneratingResult getResponse() {
        return response;
    }

    public void setResponse(GeneratingResult response) {
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
        return "Generate{" +
                "session='" + session + '\'' +
                ", response=" + response +
                ", date=" + date +
                '}';
    }
}
