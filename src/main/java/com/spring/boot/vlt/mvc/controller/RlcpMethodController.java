package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.Trial;
import com.spring.boot.vlt.mvc.service.RlcpMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @RequestMapping(value = "/get_generate", method = RequestMethod.POST)
    public ResponseEntity<GeneratingResult> getGenerate(@RequestBody String algorithm) {
        trial.setConnect(true);
        if (trial.getUrl() != null) {
            GeneratingResult result = rlcpMethodService.getGenerate(algorithm);
            trial.setGeneratingResult(result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
    }

    @RequestMapping(value = "/repeat", method = RequestMethod.POST)
    public ResponseEntity<GeneratingResult> repeat() {
        trial.setConnect(true);
        return new ResponseEntity<>(trial.getGeneratingResult(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_calculate", method = RequestMethod.POST)
    public CalculatingResult getCalculate(@RequestParam("instructions") String instructions,
                                          @RequestParam("condition") String condition) {
        CalculatingResult result = rlcpMethodService.getCalculate(instructions, condition);
        trial.setCalculatingResult(result);
        return result;
    }

    @RequestMapping(value = "/get_check", method = RequestMethod.POST)
    public ResponseEntity<List<CheckingResult>> getCheck(@RequestBody String instructions) {
        RlcpCheckResponse rlcpResponse = rlcpMethodService.getCheck(instructions);
        trial.setConnect(false);
        return new ResponseEntity<>(rlcpResponse.getBody().getResults(), HttpStatus.OK);
    }

}
