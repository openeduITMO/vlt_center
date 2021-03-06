package com.spring.boot.vlt.mvc.service.rlcp;

import com.spring.boot.vlt.config.property.VltSettings;
import com.spring.boot.vlt.info.LogStreamReader;
import com.spring.boot.vlt.mvc.model.vl.MapServer;
import com.spring.boot.vlt.mvc.model.vl.InteriorServer;
import com.spring.boot.vlt.mvc.service.LaboratoryFrameService;
import com.spring.boot.vlt.mvc.service.VltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rlcp.echo.RlcpEchoRequest;
import rlcp.echo.RlcpEchoRequestBody;

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
    private VltSettings vltSettings;
    @Autowired
    private MapServer servers;
    @Autowired
    private VltService vltService;

    public boolean getStatusExternalServer(String dirName) {
        return status(vltService.getUrl(dirName));
    }

    public boolean isInteriorServer(String dirName) {
        Optional<InteriorServer> isInteriorServer = Optional.ofNullable(servers.getMapServers().get(vltService.getUrl(dirName)));
        if (isInteriorServer.isPresent()) {
            return true;
        }
        return false;
    }

    public Map<String, String> getServersList() {
        Map<String, String> virtLab = new HashMap<>();
        servers.getMapServers().forEach((key, val) -> {
            virtLab.put(key, val.getName());
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

    public Boolean runInteriorServer(String dirNmae, String frameId) throws InterruptedException {
        laboratoryFrameService.setPreCondition(dirNmae, frameId);
        if (!status(laboratoryFrameService.getUrl())) {
            File file = new File(
                    System.getProperty("user.dir") + File.separator +
                            vltSettings.getPathsUploadedFiles() +
                            File.separator + dirNmae + File.separator + "server");
            if (file.exists()) {
                ProcessBuilder process = new ProcessBuilder("java", "-jar", "-Dfile.encoding=utf-8", new File(file, "server.jar").getAbsolutePath());
                process.directory(file);
                try {
                    Process p = process.start();
                    LogStreamReader lsr = new LogStreamReader(p.getInputStream());
                    Thread thread = new Thread(lsr, "LogStreamReader");
                    thread.start();
                    servers.put(vltService.getVl(dirNmae), laboratoryFrameService.getUrl(), p);
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
            if (process.isPresent()) {
                process.get().destroy();
                servers.remove(url);
            }
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}
