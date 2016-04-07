package com.spring.boot.vlt.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@RestController
public class UploadFileController {
    @Autowired
    private Environment env;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("uploadfile") MultipartFile uploadfile, @RequestParam("nameDir") String dir) {
        final String path = env.getProperty("paths.uploadedFiles");
        File vlDir = new File(path, dir);
        String name = uploadfile.getOriginalFilename();
        name.lastIndexOf('.');
        File newLabZip = new File(vlDir, uploadfile.getOriginalFilename());
        if (name.substring(name.lastIndexOf('.') + 1).equalsIgnoreCase("zip")) {
            if (!uploadfile.isEmpty()) {
                if (uploadedFiles(uploadfile, newLabZip)) {
                    System.out.println(HttpStatus.OK);
                    if (unZipFile(newLabZip)) {
                        newLabZip.delete();
                        return "OK";
                    }
                }
            }
        }
        return "BAD";
    }

    private boolean uploadedFiles(MultipartFile uploadfile, File newLab) {
        try {
            byte[] bytes = uploadfile.getBytes();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newLab));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean unZipFile(File zipFile) {
        try {
            String property = "lab.desc";
            ZipFile zip = new ZipFile(zipFile);
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println(entry.getName());

                if (entry.isDirectory()) {
                    new File(zipFile.getParent(), entry.getName()).mkdirs();
                } else {
                    if (entry.getName().equals(property)) {
                    } else {
                        write(zip.getInputStream(entry),
                                new BufferedOutputStream(new FileOutputStream(
                                        new File(zipFile.getParent(), entry.getName()))));
                    }

                }
            }

            zip.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
