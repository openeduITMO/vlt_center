package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.service.RlcpServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RlcpServerController {
    @Autowired
    private RlcpServerService rlcpServerService;

    @RequestMapping(value = "/get_server_status", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getStatusExternalServer() throws InterruptedException {
        if (rlcpServerService.getStatusExternalServer()) {
            return new ResponseEntity<>(rlcpServerService.isInteriorServer(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rlcpServerService.isInteriorServer(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/get_servers_list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, String>> getServersList() {
        return new ResponseEntity<>(rlcpServerService.getServersList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_type_server", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getTypeServer() {
        return new ResponseEntity<>(rlcpServerService.isInteriorServer(), HttpStatus.OK);
    }

    @RequestMapping(value = "/run_interior_server", method = RequestMethod.POST)
    public ResponseEntity<String> runInteriorServer() throws InterruptedException {
        if (rlcpServerService.runInteriorServer()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/stop_interior_server", method = RequestMethod.POST)
    public ResponseEntity<String> stopInteriorServer(@RequestBody String url) {
        if (rlcpServerService.stopInteriorServer(url)) {
            return new ResponseEntity<>("\"" + url + "\"", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
    }
}
