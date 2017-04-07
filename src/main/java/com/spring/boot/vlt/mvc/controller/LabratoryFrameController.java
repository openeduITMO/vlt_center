package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.model.frames.LaboratoryFrame;
import com.spring.boot.vlt.mvc.service.LaboratoryFrameService;
import com.spring.boot.vlt.mvc.service.UserService;
import com.spring.boot.vlt.security.JwtAuthenticationToken;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/frame")
public class LabratoryFrameController {
    @Autowired
    LaboratoryFrameService laboratoryFrameService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/get_labratory/{dir}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> getLabratoryFrame(JwtAuthenticationToken token, @PathVariable("dir") String dir) {
        UserContext userContext = (UserContext) token.getPrincipal();
        Optional<Document> documentOptional = laboratoryFrameService.setPreCondition(dir, null);
        if (!documentOptional.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(laboratoryFrameService.getLaboratoryFrame(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_property/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getNameVl(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        VirtLab virtLab = laboratoryFrameService.getVirtLab();
        return new ResponseEntity(virtLab, HttpStatus.OK);
    }

    @RequestMapping(value = "/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<LaboratoryFrame> getFrame(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        return new ResponseEntity(laboratoryFrameService.getFrame(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_generate/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<GeneratingResult> getGeneratingResult(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        return new ResponseEntity(laboratoryFrameService.getGeneratingResult(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_algorithm/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getAlgorithm(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        String algorithm = laboratoryFrameService.readAlgorithm();
        return new ResponseEntity("\"" + algorithm + "\"", HttpStatus.OK);
    }

    @RequestMapping(value = "/get_check/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ConditionForChecking>> getCheck(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        List<ConditionForChecking> checks = laboratoryFrameService.getCheckList();
        return new ResponseEntity(checks, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_url/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getUrl(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        String url = laboratoryFrameService.getUrl();
        return new ResponseEntity("\"" + url + "\"", HttpStatus.OK);
    }

    @RequestMapping(value = "/get_js/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getJs(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        return new ResponseEntity(laboratoryFrameService.getStatic("js"), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_css/{dirName}/{frameId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getCss(@PathVariable("dirName") String dirName, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setPreCondition(dirName, frameId);
        return new ResponseEntity(laboratoryFrameService.getStatic("css"), HttpStatus.OK);
    }

}
