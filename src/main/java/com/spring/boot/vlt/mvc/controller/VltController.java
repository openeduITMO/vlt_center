package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.common.AccessUtils;
import com.spring.boot.vlt.common.ErrorCode;
import com.spring.boot.vlt.common.ErrorResponse;
import com.spring.boot.vlt.mvc.model.UserContext;
import com.spring.boot.vlt.mvc.model.entity.VirtLab;
import com.spring.boot.vlt.mvc.service.VltService;
import com.spring.boot.vlt.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
        // add check user's role
        Set<VirtLab> virtList = vltService.getVirtList(userContext.getUsername());
        return new ResponseEntity(virtList, HttpStatus.OK);
    }

    @RequestMapping(value = "/add_vl", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<VirtLab> addVl(JwtAuthenticationToken token, @RequestBody String name) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloperOrAdmin(userContext) ?
                new ResponseEntity(vltService.addVl(new VirtLab(name), userContext.getUsername()), HttpStatus.OK) :
                ErrorResponse.of("No access. Contact your administrator", ErrorCode.NOT_ACCESS_RIGHT, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/get_property_vl/{dirName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<VirtLab> getPropertyVl(JwtAuthenticationToken token,
                                                 @PathVariable("dirName") String nameVl) {
        UserContext userContext = (UserContext) token.getPrincipal();
        return AccessUtils.isDeveloper(userContext) ?
                new ResponseEntity(vltService.getPropertyVl(nameVl, userContext.getUsername()), HttpStatus.OK) :
                AccessUtils.isAdmin(userContext) ?
                        new ResponseEntity(vltService.getPropertyVl(nameVl), HttpStatus.OK) :
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

    @RequestMapping(value = "/start_vl/{dir}/img/{name}.{suffix}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImg(@PathVariable("dir") String dir, @PathVariable("name") String name, @PathVariable("suffix") String suffix) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(vltService.getImg(dir, name, suffix), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/VLabs/{dir}/tool/css/{d-l}/img/{name}.{suffix}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImg2(@PathVariable("dir") String dir, @PathVariable("name") String name, @PathVariable("suffix") String suffix) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(vltService.getImg2(dir, name, suffix), headers, HttpStatus.CREATED);
    }
}
