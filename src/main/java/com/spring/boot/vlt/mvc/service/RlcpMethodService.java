package com.spring.boot.vlt.mvc.service;

import com.spring.boot.vlt.mvc.model.Trial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import rlcp.calculate.CalculatingResult;
import rlcp.calculate.RlcpCalculateRequest;
import rlcp.calculate.RlcpCalculateRequestBody;
import rlcp.calculate.RlcpCalculateResponse;
import rlcp.check.CheckingResult;
import rlcp.check.RlcpCheckRequest;
import rlcp.check.RlcpCheckRequestBody;
import rlcp.check.RlcpCheckResponse;
import rlcp.generate.GeneratingResult;
import rlcp.generate.RlcpGenerateRequest;
import rlcp.generate.RlcpGenerateRequestBody;
import rlcp.generate.RlcpGenerateResponse;

import java.util.List;

@Service
public class RlcpMethodService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private Trial trial;

    public GeneratingResult getGenerate(String algorithm) {
        RlcpGenerateRequestBody body = new RlcpGenerateRequestBody(algorithm);
        RlcpGenerateRequest rlcpGenerateRequest = body.prepareRequest(trial.getUrl());
        RlcpGenerateResponse rlcpResponse = null;
        try {
            rlcpResponse = rlcpGenerateRequest.execute();
        } catch (Exception e) {
            LOGGER.error("Rlcp GENERATE response exception", e.fillInStackTrace());
        }
        GeneratingResult result = rlcpResponse.getBody().getGeneratingResult();
        LOGGER.info("GENERATE successfully with result: {\n" +
                "\tcode: \"" + result.getCode() + "\",\n" +
                "\ttext: \"" + result.getText() + "\",\n" +
                "\tinstraction: \"" + result.getInstructions() + "\"}");
        return result;
    }

    public CalculatingResult getCalculate(String instructions,
                                          String condition) {
        CalculatingResult result = null;
        if (trial.isConnect()) {
            RlcpCalculateRequestBody body = new RlcpCalculateRequestBody(condition, instructions, trial.getGeneratingResult());
            RlcpCalculateRequest rlcpCalculateRequest = body.prepareRequest(trial.getUrl());
            RlcpCalculateResponse rlcpResponse = null;
            try {
                rlcpResponse = rlcpCalculateRequest.execute();
            } catch (Exception e) {
                LOGGER.error("Rlcp CALCULATE response exception", e.fillInStackTrace());
            }
            result = rlcpResponse.getBody().getCalculatingResult();
        }

        return result;
    }

    public RlcpCheckResponse getCheck(@RequestBody String instructions) {
        RlcpCheckResponse rlcpResponse = null;
        if (trial.isConnect()) {
            RlcpCheckRequestBody rlcpRequestBody = new RlcpCheckRequestBody(
                    trial.getConditionsList(),
                    instructions,
                    trial.getGeneratingResult());
            RlcpCheckRequest rlcpRequest = rlcpRequestBody.prepareRequest(trial.getUrl());
            try {
                rlcpResponse = rlcpRequest.execute();
            } catch (Exception e) {
                LOGGER.error("Rlcp CHECK response exception", e.fillInStackTrace());
            }
        }
        List<CheckingResult> results = rlcpResponse.getBody().getResults();
        for (CheckingResult res : results) {
            LOGGER.info("CHECK successfully for answer = \"" + instructions + "\" with result: {\n" +
                    "\tmark: \"" + res.getResult() + "\",\n" +
                    "\tcomment: \"" + res.getOutput() + "\",\n" +
                    "\tGenerate: {\n" +
                    "\t\tcode: \"" + trial.getGeneratingResult().getCode() + "\",\n" +
                    "\t\ttext: \"" + trial.getGeneratingResult().getText() + "\",\n" +
                    "\t\tinstraction: \"" + trial.getGeneratingResult().getInstructions() + "\"}}");
        }
        return rlcpResponse;
    }
}
