package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.MapServer;
import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import com.spring.boot.vlt.mvc.service.UploadFileService;
import com.spring.boot.vlt.mvc.service.VltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;

@RestController
public class VltController {
    @Autowired
    private VltService vltService;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/get_list_vl", method = RequestMethod.GET)
    public ResponseEntity<List<VirtLab>> getVlList() {
        return new ResponseEntity(vltService.getVirtList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add_vl", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<VirtLab> addVl(@RequestBody String name) {
        return new ResponseEntity(vltService.addVl(new VirtLab(name)), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_property_vl/{name}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<VirtLab> getPropertyVl(@PathVariable("name") String nameVl) {
        return new ResponseEntity(vltService.getPropertyVl(nameVl), HttpStatus.OK);
    }

    @RequestMapping(value = "/save_property_vl/{dir}", method = RequestMethod.POST)
    public ResponseEntity<VirtLab> savePropertyVl(@Valid @RequestBody VirtLab vl, @PathVariable("dir") String dir, BindingResult bindResult) {
        return new ResponseEntity(vltService.savePropertyVl(vl, dir), HttpStatus.OK);
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
