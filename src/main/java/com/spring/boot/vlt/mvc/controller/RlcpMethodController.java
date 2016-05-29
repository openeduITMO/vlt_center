package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.Trial;
import com.spring.boot.vlt.mvc.service.RlcpMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rlcp.calculate.CalculatingResult;
import rlcp.check.*;
import rlcp.generate.GeneratingResult;

import java.util.List;

@RestController
public class RlcpMethodController {
    @Autowired
    private Trial trial;
    @Autowired
    private RlcpMethodService rlcpMethodService;


    @RequestMapping(value = "/getGenerate", method = RequestMethod.POST)
    public GeneratingResult getGenerate(@RequestParam("algorithm") String algorithm) {
        trial.setConnect(true);
        if (trial.getUrl() != null) {
            GeneratingResult result = rlcpMethodService.getGenerate(algorithm);
            trial.setGeneratingResult(result);
            return result;
        }
        return null;
    }

    @RequestMapping(value = "/repeat", method = RequestMethod.POST)
    public GeneratingResult repeat() {
        trial.setConnect(true);
        return trial.getGeneratingResult();
    }

    @RequestMapping(value = "/getCalculate", method = RequestMethod.POST)
    public CalculatingResult getCalculate(@RequestParam("instructions") String instructions,
                                          @RequestParam("condition") String condition) {
        CalculatingResult result = rlcpMethodService.getCalculate(instructions, condition);
        trial.setCalculatingResult(result);
        return result;
    }

    @RequestMapping(value = "/getCheck", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<CheckingResult> getCheck(@RequestBody String instructions) {
        RlcpCheckResponse rlcpResponse = rlcpMethodService.getCheck(instructions);
        trial.setConnect(false);
        return rlcpResponse.getBody().getResults();
    }

}
