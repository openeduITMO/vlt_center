package com.spring.boot.vlt.mvc.controller;

import com.spring.boot.vlt.mvc.model.Trial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rlcp.calculate.CalculatingResult;
import rlcp.calculate.RlcpCalculateRequest;
import rlcp.calculate.RlcpCalculateRequestBody;
import rlcp.calculate.RlcpCalculateResponse;
import rlcp.check.*;
import rlcp.generate.GeneratingResult;
import rlcp.generate.RlcpGenerateRequest;
import rlcp.generate.RlcpGenerateRequestBody;
import rlcp.generate.RlcpGenerateResponse;
import java.util.List;

@RestController
public class RlcpMethodController {
    @Autowired
    private Trial trial;


    @RequestMapping(value = "/getGenerate", method = RequestMethod.POST)
    public GeneratingResult getGenerate(@RequestParam("algorithm") String algorithm) {
        trial.setConnect(true);
        RlcpGenerateRequestBody body = new RlcpGenerateRequestBody(algorithm);
        RlcpGenerateRequest rlcpGenerateRequest = body.prepareRequest(trial.getUrl());
        RlcpGenerateResponse rlcpResponse = null;
        try {
            rlcpResponse = rlcpGenerateRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GeneratingResult result = rlcpResponse.getBody().getGeneratingResult();
        trial.setGeneratingResult(result);
        return result;
    }

    @RequestMapping(value = "/repeat", method = RequestMethod.POST)
    public GeneratingResult repeat(){
        trial.setConnect(true);
        return trial.getGeneratingResult();
    }


    @RequestMapping(value = "/getCalculate", method = RequestMethod.POST)
    public CalculatingResult getCalculate(@RequestParam("instructions") String instructions,
                                          @RequestParam("condition") String condition) {
        CalculatingResult result = null;
        if(trial.isConnect()){
            RlcpCalculateRequestBody body = new RlcpCalculateRequestBody(condition, instructions, trial.getGeneratingResult());
            RlcpCalculateRequest rlcpCalculateRequest = body.prepareRequest(trial.getUrl());
            RlcpCalculateResponse rlcpResponse = null;
            try {
                rlcpResponse = rlcpCalculateRequest.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = rlcpResponse.getBody().getCalculatingResult();
            trial.setCalculatingResult(result);
        }

        return result;
    }

    @RequestMapping(value = "/getCheck", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<CheckingResult> getCheck(@RequestBody String instructions) {
        RlcpCheckResponse rlcpResponse = null;
        if(trial.isConnect()) {
            RlcpCheckRequestBody rlcpRequestBody = new RlcpCheckRequestBody(
                    trial.getConditionsList(),
                    instructions,
                    trial.getGeneratingResult());
            RlcpCheckRequest rlcpRequest = rlcpRequestBody.prepareRequest(trial.getUrl());
            try {
                rlcpResponse = rlcpRequest.execute();
                trial.setConnect(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rlcpResponse.getBody().getResults();
    }

}
