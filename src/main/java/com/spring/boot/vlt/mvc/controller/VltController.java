package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
public class VltController {
    @Autowired
    private Environment env;

    @RequestMapping("/")
    public ModelAndView index() {
        final String path = env.getProperty("paths.uploadedFiles");
        PathMatcher requestPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.desc");
        List<VirtLab> vlList = new ArrayList<>();
        try {
            Files.walk(new File(path).toPath()).filter(p -> requestPathMatcher.matches(p)).forEach(
                    p -> {
                        vlList.add(new VirtLab(p.toFile()));
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("index", "vlList", vlList);
    }

    @RequestMapping("/addVL")
    @ResponseBody
    public VirtLab addVl(@Valid VirtLab vl, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return null;
        }
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

    @RequestMapping("/getPropertyVl")
    @ResponseBody
    public VirtLab getPropertyVl(@RequestParam("name") String nameVl) {
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

}
