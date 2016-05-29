package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("uploadfile") MultipartFile uploadfile, @RequestParam("nameDir") String dir) {
        boolean upload = uploadFileService.upload(uploadfile, dir);
        if (upload) {
            return "OK";
        }
        return "BAD";
    }


}
