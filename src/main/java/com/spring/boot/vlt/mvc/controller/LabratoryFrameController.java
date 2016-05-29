package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.Trial;
import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import com.spring.boot.vlt.mvc.service.LaboratoryFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rlcp.check.ConditionForChecking;
import java.util.*;

@RestController
public class LabratoryFrameController {
    @Autowired
    LaboratoryFrameService laboratoryFrameService;

    @Autowired
    private Trial trial;

    @RequestMapping(value = "/getLabratoryFame", method = RequestMethod.POST, produces = "application/json")
    public List getLabratoryFrme(@RequestParam("name") String nameVl) {
        laboratoryFrameService.setNameVl(nameVl);
        return laboratoryFrameService.getLaboratoryFrame();
    }

    @RequestMapping(value = "/startVl/{name}/{frameId}", method = RequestMethod.GET)
    public ModelAndView startVl(@PathVariable("name") String nameVl, @PathVariable("frameId") String frameId) {
        laboratoryFrameService.setNameVl(nameVl);
        laboratoryFrameService.setFrameId(frameId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("startVl");

        VirtLab virtLab = laboratoryFrameService.getVirtLab();
        modelAndView.addObject("nameVl", virtLab.getName());

        modelAndView.addObject("frame", laboratoryFrameService.getFrame());
        modelAndView.addObject("generate", laboratoryFrameService.getGeneratingResult());
        modelAndView.addObject("algorithm", laboratoryFrameService.readAlgorithm());

        List<ConditionForChecking> checks = laboratoryFrameService.getCheckList();
        modelAndView.addObject("check", checks);

        modelAndView.addObject("js", laboratoryFrameService.getStatic("js"));
        modelAndView.addObject("css", laboratoryFrameService.getStatic("css"));

        String url = laboratoryFrameService.getUrl();
        modelAndView.addObject("url", url);

        trial.setFraimeId(frameId);
        trial.setUrl(url);
        trial.setConditionsList(checks);
        trial.setVl(virtLab);

        return modelAndView;
    }
}
