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

import java.net.UnknownHostException;
import java.util.List;

@RestController
public class RlcpController {
    @Autowired
    private Trial trial;

    @RequestMapping(value = "/getGenerate", method = RequestMethod.POST)
    public GeneratingResult getGenerate(@RequestParam("algorithm") String algorithm) {
        RlcpGenerateRequestBody body = new RlcpGenerateRequestBody(algorithm);
        RlcpGenerateRequest rlcpGenerateRequest = body.prepareRequest(trial.getUrl());
        RlcpGenerateResponse rlcpResponse = null;
        try {
            rlcpResponse = rlcpGenerateRequest.execute();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        GeneratingResult result = rlcpResponse.getBody().getGeneratingResult();
        trial.setGeneratingResult(result);
        return result;
    }

    @RequestMapping(value = "/getCalculate", method = RequestMethod.POST, produces = "application/json")
    public CalculatingResult getCalculate(@RequestParam("instructions") String instructions,
                                          @RequestParam("condition") String condition) {
        RlcpCalculateRequestBody body = new RlcpCalculateRequestBody(condition, instructions, trial.getGeneratingResult());
        RlcpCalculateRequest rlcpCalculateRequest = body.prepareRequest(trial.getUrl());
        RlcpCalculateResponse rlcpResponse = null;
        try {
            rlcpResponse = rlcpCalculateRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CalculatingResult result = rlcpResponse.getBody().getCalculatingResult();
        trial.setCalculatingResult(result);
        return result;
    }

    @RequestMapping(value = "/getCheck", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<CheckingResult> getCheck(@RequestBody String instructions) {
        RlcpCheckRequestBody rlcpRequestBody = new RlcpCheckRequestBody(
                trial.getConditionsList(),
                instructions,
                trial.getGeneratingResult());
        RlcpCheckRequest rlcpRequest = rlcpRequestBody.prepareRequest(trial.getUrl());
        RlcpCheckResponse rlcpResponse = null;
        try {
            rlcpResponse = rlcpRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rlcpResponse.getBody().getResults();
    }

}
