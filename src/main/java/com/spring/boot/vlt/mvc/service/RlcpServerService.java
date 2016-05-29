package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.info.LogStreamReader;
import com.spring.boot.vlt.mvc.model.MapServer;
import com.spring.boot.vlt.mvc.model.Trial;
import com.spring.boot.vlt.mvc.model.vl.InteriorServer;
import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import rlcp.echo.RlcpEchoRequest;
import rlcp.echo.RlcpEchoRequestBody;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RlcpServerService {
    @Autowired
    LaboratoryFrameService laboratoryFrameService;
    @Autowired
    private Environment env;
    @Inject
    private MapServer servers;
    @Autowired
    private Trial trial;

    public boolean getStatusExternalServer() {
        return status(trial.getUrl());
    }

    public boolean isInteriorServer(){
        for(InteriorServer server: servers.getMapServers().values()){
            if(server.getUrl().equalsIgnoreCase(trial.getUrl())){
                return true;
            }
        }
        return false;
    }

    public Map<VirtLab, String> getServersList(){
        Map<VirtLab, String> virtLab = new HashMap<>();
        servers.getMapServers().forEach((key, val) ->{
            virtLab.put(key, val.getUrl());
        });
        return virtLab;
    }

    private boolean status(String url) {
        RlcpEchoRequestBody body = new RlcpEchoRequestBody();
        RlcpEchoRequest rlcpEchoRequest = body.prepareRequest(url);
        try {
            rlcpEchoRequest.execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean runInteriorServer() throws InterruptedException {
        laboratoryFrameService.setNameVl(trial.getVl().getDirName());
        laboratoryFrameService.setFrameId(trial.getFraimeId());
        if (!status(trial.getUrl())) {
            File file = new File(
                    System.getProperty("user.dir") + File.separator +
                            env.getProperty("paths.uploadedFiles") +
                            File.separator + trial.getVl().getDirName() + File.separator + "server");
            if (file.exists()) {
                ProcessBuilder process = new ProcessBuilder("java", "-jar", "-Dfile.encoding=utf-8", new File(file, "server.jar").getAbsolutePath());
                process.directory(file);
                try {
                    Process p = process.start();
                    LogStreamReader lsr = new LogStreamReader(p.getInputStream());
                    Thread thread = new Thread(lsr, "LogStreamReader");
                    thread.start();
                    servers.put(trial.getVl(), laboratoryFrameService.getUrl(), p);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean stopInteriorServer(String url) {
        try {
            Optional<Process> process = servers.getProcess(url);
            if(process.isPresent()){
                process.get().destroy();
                servers.remove(trial.getVl());
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
