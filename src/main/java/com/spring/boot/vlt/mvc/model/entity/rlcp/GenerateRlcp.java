package com.spring.boot.vlt.mvc.model.entity.rlcp;

import com.spring.boot.vlt.mvc.model.entity.Session;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attempt")
    private Session attempt;

    @Column(name = "response", nullable = false)
    private GeneratingResult response;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "parent", nullable = true)
    private Long parent;

    public GenerateRlcp() {
    }

    public GenerateRlcp(Session attempt, GeneratingResult response, Date date) {
        this.attempt = attempt;
        this.response = response;
        this.date = date;
    }

    public GenerateRlcp(Session attempt, GeneratingResult response, Date date, Long idParent) {
        this.parent = idParent;
        this.attempt = attempt;
        this.response = response;
        this.date = date;
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
                "session='" + attempt == null ? null : attempt.getSession() + '\'' +
                ", response=" + response +
                ", date=" + date +
                '}';
    }
}
