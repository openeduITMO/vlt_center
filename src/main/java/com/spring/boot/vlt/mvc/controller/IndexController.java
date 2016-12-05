package com.spring.boot.vlt.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
public class IndexController {
    @RequestMapping("/api/test_connect")
    public ResponseEntity index() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
