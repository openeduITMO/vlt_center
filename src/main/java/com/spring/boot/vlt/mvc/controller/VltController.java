package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.common.AccessUtils;
import com.spring.boot.vlt.common.ErrorCode;
import com.spring.boot.vlt.common.ErrorResponse;
import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.model.pojo_response.RegisterForVl;
import com.spring.boot.vlt.mvc.model.pojo_response.ResultInfo;
import com.spring.boot.vlt.mvc.service.VltService;
import com.spring.boot.vlt.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class VltController {
    @Autowired
    private VltService vltService;

    @RequestMapping(value = "/get_list_vl", method = RequestMethod.GET)
    public ResponseEntity<Set<VirtLab>> getVlList(JwtAuthenticationToken token) {
        UserContext userContext = (UserContext) token.getPrincipal();
        Set<VirtLab> virtList = AccessUtils.isStudent(userContext) ?
                vltService.getAvailableVarList(userContext.getUsername()) :
                vltService.getVirtListByAuthor(userContext.getUsername());
        return new ResponseEntity<>(virtList, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_other_list_vl", method = RequestMethod.GET)
    public ResponseEntity<Set<VirtLab>> getPublicVlList(JwtAuthenticationToken token) {
        UserContext userContext = (UserContext) token.getPrincipal();
        Set<VirtLab> otherVlList = vltService.getPublicVlList(userContext.getUsername());
        return new ResponseEntity<>(otherVlList, HttpStatus.OK);
    }

    @RequestMapping(value = "/get_users/{dirName}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<ResultInfo>>> getStudentForVl(JwtAuthenticationToken token, @PathVariable("dirName") String dirName) {
        UserContext userContext = (UserContext) token.getPrincipal();

        return AccessUtils.isDeveloperOrAdmin(userContext) ?
                new ResponseEntity<>(vltService.getRegisterUsers(dirName), HttpStatus.OK):
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/declaration/{dirName}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<VirtLab> register(JwtAuthenticationToken token, @PathVariable("dirName") String dirName) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isStudent(userContext) ?
                new ResponseEntity(vltService.registerOnVL(dirName, userContext.getUsername()), HttpStatus.OK) :
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/add_vl", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<VirtLab> addVl(JwtAuthenticationToken token, @RequestBody String name) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloperOrAdmin(userContext) ?
                new ResponseEntity(
                        vltService.addVl(new VirtLab(name), userContext.getUsername()),
                        HttpStatus.OK) :
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/get_property_vl/{dirName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<VirtLab> getPropertyVl(JwtAuthenticationToken token,
                                                 @PathVariable("dirName") String dirName) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloper(userContext) ?
                new ResponseEntity(vltService.foundVlByDirUnderUser(userContext.getUsername(), dirName), HttpStatus.OK) :
                AccessUtils.isAdmin(userContext) ?
                        new ResponseEntity(vltService.getVl(dirName), HttpStatus.OK) :
                        ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/save_property_vl/{dir}", method = RequestMethod.POST)
    public ResponseEntity<VirtLab> savePropertyVl(JwtAuthenticationToken token,
                                                  @Valid @RequestBody VirtLab vl,
                                                  @PathVariable("dir") String dir) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloper(userContext) ?
                new ResponseEntity(vltService.savePropertyVl(vl, dir, userContext.getUsername()), HttpStatus.OK) :
                AccessUtils.isAdmin(userContext) ?
                        new ResponseEntity(vltService.savePropertyVl(vl, dir), HttpStatus.OK) :
                        ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

}
