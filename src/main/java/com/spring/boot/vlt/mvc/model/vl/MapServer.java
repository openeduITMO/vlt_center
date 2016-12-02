package com.spring.boot.vlt.mvc.model.vl;

import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Scope(value = "application")
public class MapServer {
    private Map<String, InteriorServer> mapServers;

    public MapServer() {
        mapServers = new HashMap<>();
    }

    public void put(VirtLab virtLab, String url, Process process) {
        mapServers.put(url, new InteriorServer(virtLab.getName(), process));
    }

    public Optional<Process> getProcess(String url) {
        return Optional.ofNullable(mapServers.get(url).getProcess());
    }

    public boolean remove(String url) {
        return Optional.ofNullable(mapServers.remove(url)).isPresent();
    }

    public Map<String, InteriorServer> getMapServers() {
        return mapServers;
    }

}
