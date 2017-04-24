package com.spring.boot.vlt.mvc.model.staticFile;

import java.util.ArrayList;
import java.util.List;

public class StaticFile {
    private List<String> dev;
    private List<String> lib;

    public String getDirName() {
        return dirName;
    }

    private String dirName;

    public StaticFile(String dirName){
        this.dirName = dirName;
        dev = new ArrayList<>();
        lib = new ArrayList<>();
    }

    public void addLib(String libName){
        lib.add(libName);
    }

    public void addDev(String devName){
        dev.add(devName);
    }

    public List<String> getDev() {
        return dev;
    }

    public List<String> getLib() {
        return lib;
    }
}
