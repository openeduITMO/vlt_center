package com.spring.boot.vlt.mvc.model;

import com.spring.boot.vlt.mvc.model.vl.InteriorServer;
import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Scope(value = "application")
public class MapServer {
    Map<VirtLab, InteriorServer> mapServers;

    public MapServer() {
        mapServers = new HashMap<>();
    }

    public void put(VirtLab virtLab, String url, Process process) {
        mapServers.put(virtLab, new InteriorServer(url, process));
    }

    public Optional<Process> getProcess(String url){
        Process p = null;
        for(InteriorServer i: mapServers.values()){
            if(i.getUrl().equals(url)){
                p = i.getProcess();
            }
        }
        return Optional.ofNullable(p);
    }

    public void remove(VirtLab virtLab) {
        mapServers.remove(virtLab);
    }

    public Map<VirtLab, InteriorServer> getMapServers() {
        return mapServers;
    }


}
