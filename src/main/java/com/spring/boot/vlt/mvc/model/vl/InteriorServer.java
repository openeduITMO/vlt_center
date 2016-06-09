package com.spring.boot.vlt.mvc.model.vl;

public class InteriorServer {
    private String name;
    private Process process;

    public InteriorServer(String url, Process process) {
        this.name = url;
        this.process = process;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.name = url;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
