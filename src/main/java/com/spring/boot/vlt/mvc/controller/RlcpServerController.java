package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.service.RlcpServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RlcpServerController {
    @Autowired
    private RlcpServerService rlcpServerService;

    @RequestMapping(value = "/getServerStatus", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getStatusExternalServer() throws InterruptedException {
        if (rlcpServerService.getStatusExternalServer()) {
            return new ResponseEntity<>(rlcpServerService.isInteriorServer(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rlcpServerService.isInteriorServer(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/getServersList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<String, String>> getServersList() {
        return new ResponseEntity<>(rlcpServerService.getServersList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getTypeServer", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getTypeServer() {
        return new ResponseEntity<>(rlcpServerService.isInteriorServer(), HttpStatus.OK);
    }

    @RequestMapping(value = "/runInteriorServer", method = RequestMethod.POST)
    public ResponseEntity<String> runInteriorServer() throws InterruptedException {
        if (rlcpServerService.runInteriorServer()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/stopInteriorServer", method = RequestMethod.POST)
    public ResponseEntity<String> stopInteriorServer(@RequestParam("url") String url) {
        if (rlcpServerService.stopInteriorServer(url)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
    }
}
