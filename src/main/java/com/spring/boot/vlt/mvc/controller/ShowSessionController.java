package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.common.AccessUtils;
import com.spring.boot.vlt.common.ErrorCode;
import com.spring.boot.vlt.common.ErrorResponse;
import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.model.entity.rlcp.CheckRlcp;
import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import com.spring.boot.vlt.mvc.service.AttemptsService;
import com.spring.boot.vlt.mvc.service.LaboratoryFrameService;
import com.spring.boot.vlt.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/session")
public class ShowSessionController {
    @Autowired
    private AttemptsService attemptsService;

    @RequestMapping(value = "/get_property_vl/{session}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<VirtLab> getPropertyVl(JwtAuthenticationToken token,
                                                 @PathVariable("session") String session) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloperOrAdmin(userContext) ?
                new ResponseEntity(attemptsService.foundVlBySession(session), HttpStatus.OK) :
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/get_generate/{session}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<GenerateRlcp> getGenerate(JwtAuthenticationToken token,
                                                    @PathVariable("session") String session) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloperOrAdmin(userContext) ?
                new ResponseEntity(attemptsService.foundGenerateBySession(session), HttpStatus.OK) :
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/get_check/{session}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CheckRlcp> getCheck(JwtAuthenticationToken token,
                                              @PathVariable("session") String session) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloperOrAdmin(userContext) ?
                new ResponseEntity(attemptsService.foundCheckBySession(session), HttpStatus.OK) :
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);

    }

    @RequestMapping(value = "/get_js/{session}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getJs(@PathVariable("session") String session) {
        return new ResponseEntity(attemptsService.getStatic("js", session), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_css/{session}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getCss(@PathVariable("session") String session) {
        return new ResponseEntity(attemptsService.getStatic("css", session), HttpStatus.OK);
    }

}
