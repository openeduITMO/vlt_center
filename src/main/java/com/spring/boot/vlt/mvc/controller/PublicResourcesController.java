package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.service.VltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping(value = "/public/resources")
public class PublicResourcesController {
    @Autowired
    private VltService vltService;

    @RequestMapping(value = "/{dir}/img/{name}.{suffix}", method = RequestMethod.GET)
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
