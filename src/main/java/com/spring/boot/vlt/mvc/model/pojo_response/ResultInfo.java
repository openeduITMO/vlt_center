package com.spring.boot.vlt.mvc.model.pojo_response;

import rlcp.check.CheckingResult;

import java.util.Date;

public class ResultInfo implements Comparable {
    private Date startDate;
    private String session;
    private CheckingResult result;

    public ResultInfo(Date startDate, String session, CheckingResult result) {
        this.startDate = startDate;
        this.session = session;
        this.result = result;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultInfo that = (ResultInfo) o;

        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        return session != null ? session.equals(that.session) : that.session == null;

    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        return result;
    }


    @Override
    public int compareTo(Object o) {
        ResultInfo info = (ResultInfo) o;
        int comp = this.startDate.compareTo(info.startDate);
        return comp == 0? this.session.compareTo(info.session) : comp;
    }
}
