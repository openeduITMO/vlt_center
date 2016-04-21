package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.info.LogStreamReader;
import com.spring.boot.vlt.mvc.model.Trial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rlcp.echo.RlcpEchoRequest;
import rlcp.echo.RlcpEchoRequestBody;
import rlcp.echo.RlcpEchoResponse;

import java.io.File;
import java.io.IOException;

@RestController
public class RlcpServerController {
    @Autowired
    private Environment env;
    @Autowired
    private Trial trial;
    private Process p;

    @RequestMapping(value = "/getServerStatus", method = RequestMethod.POST)
    public ResponseEntity<String> getStatusExternalServer() {
        RlcpEchoRequestBody body = new RlcpEchoRequestBody();
        RlcpEchoRequest rlcpEchoRequest = body.prepareRequest(trial.getUrl());
        RlcpEchoResponse rlcpEchoResponse;
        try{
            rlcpEchoResponse = rlcpEchoRequest.execute();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(trial.getUrl(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/runInteriorServer", method = RequestMethod.POST)
    public ResponseEntity<String> runInteriorServer() {
        File file = new File(
                env.getProperty("paths.uploadedFiles") +
                File.separator + trial.getVl().getDirName() + File.separator + "server");
        if (file.exists()) {
            ProcessBuilder process = new ProcessBuilder("java", "-jar", "-Dfile.encoding=utf-8", new File(file, "server.jar").getAbsolutePath());
            process.directory(file);
            try {
                p = process.start();
                LogStreamReader lsr = new LogStreamReader(p.getInputStream());
                Thread thread = new Thread(lsr, "LogStreamReader");
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/stopInteriorServer", method = RequestMethod.POST)
    public ResponseEntity<String> stopInteriorServer() {
        try{
            p.destroy();
        } catch (NullPointerException e){
//            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
