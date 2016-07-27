package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/upload-file/{dir}", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(
            @RequestParam("uploadfile") MultipartFile uploadfile, @PathVariable("dir") String dir) {
        boolean upload = uploadFileService.upload(uploadfile, dir);
        if (upload) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


}
