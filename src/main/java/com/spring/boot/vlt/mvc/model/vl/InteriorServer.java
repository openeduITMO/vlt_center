package com.spring.boot.vlt.mvc.model.vl;

import org.springframework.stereotype.Component;

public class InteriorServer {
    private String url;
    private Process process;

    public InteriorServer(String url, Process process) {
        this.url = url;
        this.process = process;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InteriorServer that = (InteriorServer) o;

        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
