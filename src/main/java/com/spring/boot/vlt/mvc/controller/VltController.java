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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("vlList", vltService.getVirtList());
        return modelAndView;
    }

    @RequestMapping("/addVL")
    public VirtLab addVl(@Valid VirtLab vl) {
        return vltService.addVl(vl);
    }

    @RequestMapping("/getPropertyVl/{name}")
    public VirtLab getPropertyVl(@PathVariable("name") String nameVl) {
        return vltService.getPropertyVl(nameVl);
    }

    @RequestMapping(value = "/savePropertyVl/{dir}", method = RequestMethod.POST)
    public VirtLab savePropertyVl(@Valid VirtLab vl, @PathVariable("dir") String dir, BindingResult bindResult) {
        return vltService.savePropertyVl(vl, dir);
    }

    @RequestMapping(value = "/startVl/{dir}/img/{name}.{suffix}", method = RequestMethod.GET)
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
