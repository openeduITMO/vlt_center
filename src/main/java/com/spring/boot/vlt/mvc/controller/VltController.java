package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.vl.VirtLab;
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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VltController {
    @Autowired
    private Environment env;

    @RequestMapping("/")
    public ModelAndView index() {
        final String path = env.getProperty("paths.uploadedFiles");
        PathMatcher requestPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.desc");
        List<VirtLab> vlList = new ArrayList<>();
        try {
            File vlabs = new File(path);
            if (vlabs.exists()) {
                Files.walk(vlabs.toPath()).filter(p -> requestPathMatcher.matches(p)).forEach(
                        p -> {
                            vlList.add(new VirtLab(p.toFile()));
                        }
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("index", "vlList", vlList);
    }

    @RequestMapping("/addVL")
    public VirtLab addVl(@Valid VirtLab vl) {
        final String path = env.getProperty("paths.uploadedFiles");
        File vlDir = new File(path, "lab" + System.currentTimeMillis());
        while (vlDir.exists()) {
            vlDir = new File(path, "lab" + System.currentTimeMillis());
        }
        if (!vlDir.exists()) {
            vlDir.mkdirs();
        }
        vl.setDirName(vlDir.getName());
        vl.save(path);
        return vl;
    }

    @RequestMapping("/getPropertyVl/{name}")
    public VirtLab getPropertyVl(@PathVariable("name") String nameVl) {
        final String path = env.getProperty("paths.uploadedFiles");
        File vlDir = new File(path, nameVl);
        return new VirtLab(new File(vlDir, "lab.desc"));
    }

    @RequestMapping(value = "/savePropertyVl", method = RequestMethod.POST)
    @ResponseBody
    public VirtLab savePropertyVl(@Valid VirtLab vl, BindingResult bindResult) {
        final String path = env.getProperty("paths.uploadedFiles");
        vl.save(path);
        return vl;
    }

    @RequestMapping(value = "/startVl/{dir}/img/{name}.{suffix}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImg(@PathVariable("dir") String dir, @PathVariable("name") String name, @PathVariable("suffix") String suffix) throws IOException {
        final String path = env.getProperty("paths.uploadedFiles");
        final File img = new File(path + File.separator + dir + File.separator + "tool" + File.separator + "img", name + "." + suffix);
        return getStatic(img);
    }

    @RequestMapping(value = "/VLabs/{dir}/tool/css/{d-l}/img/{name}.{suffix}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImg2(@PathVariable("dir") String dir, @PathVariable("name") String name, @PathVariable("suffix") String suffix) throws IOException {
        final String path = env.getProperty("paths.uploadedFiles");
        final File img = new File(path + File.separator + dir + File.separator + "tool" + File.separator + "img", name + "." + suffix);
        return getStatic(img);
    }

    private ResponseEntity<byte[]> getStatic(File img) throws IOException {
        RandomAccessFile f = new RandomAccessFile(img, "r");
        byte[] b = new byte[(int) f.length()];
        f.readFully(b);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(b, headers, HttpStatus.CREATED);
    }

}
