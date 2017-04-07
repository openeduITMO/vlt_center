package com.spring.boot.vlt.mvc.controller.rlcp;

import com.spring.boot.vlt.mvc.service.rlcp.RlcpServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class RlcpServerController {
    @Autowired
    private RlcpServerService rlcpServerService;

    @RequestMapping(value = "/{dir}/get_server_status", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getStatusExternalServer(@PathVariable("dir") String dir) throws InterruptedException {
        if (rlcpServerService.getStatusExternalServer(dir)) {
            return new ResponseEntity<>(rlcpServerService.isInteriorServer(dir), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rlcpServerService.isInteriorServer(dir), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/get_servers_list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, String>> getServersList() {
        return new ResponseEntity<>(rlcpServerService.getServersList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{dir}/get_type_server", method = RequestMethod.POST)
    public ResponseEntity<Boolean> getTypeServer(@PathVariable("dir") String dir) {
        return new ResponseEntity<>(rlcpServerService.isInteriorServer(dir), HttpStatus.OK);
    }

    @RequestMapping(value = "/{dir}/{frameId}/run_interior_server", method = RequestMethod.POST)
    public ResponseEntity<String> runInteriorServer(@PathVariable("dir") String dir, @PathVariable("frameId") String frameId) throws InterruptedException {
        if (rlcpServerService.runInteriorServer(dir, frameId)) {
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
