package com.spring.boot.vlt.mvc.model.frames;

public class Frame {
    private int id;
    private int sheme;
    private String name;

    public Frame(int id, int sheme, String name, String data) {
        this.id = id;
        this.sheme = sheme;
        this.name = name;
        this.data = data;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSheme() {
        return sheme;
    }

    public void setSheme(int sheme) {
        this.sheme = sheme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String data;
}
