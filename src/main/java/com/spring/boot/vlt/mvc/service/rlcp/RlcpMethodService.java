package com.spring.boot.vlt.mvc.service.rlcp;

import com.spring.boot.vlt.mvc.model.entity.rlcp.GenerateRlcp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.Optional;

@Service
public class RlcpMethodService {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    @Autowired
    private RlcpDataBaseService rlcpDataBaseService;

    public GeneratingResult getGenerate(String algorithm, String url) {
        RlcpGenerateRequestBody body = new RlcpGenerateRequestBody(algorithm);
        RlcpGenerateRequest rlcpGenerateRequest = body.prepareRequest(url);
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

    public CalculatingResult getCalculate(String url, String session, String instructions, String condition) {
        CalculatingResult result = null;
        Optional<GenerateRlcp> generateBySession = getGenerateBySession(session);
        if (generateBySession.isPresent()) {
            RlcpCalculateRequestBody body = new RlcpCalculateRequestBody(condition, instructions, generateBySession.get().getResponse());
            RlcpCalculateRequest rlcpCalculateRequest = body.prepareRequest(url);
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

    public RlcpCheckResponse getCheck(String url, String session, List<ConditionForChecking> checks, String instructions) {
        RlcpCheckResponse rlcpResponse = null;
        Optional<GenerateRlcp> generateBySession = getGenerateBySession(session);
        if (generateBySession.isPresent()) {
            RlcpCheckRequestBody rlcpRequestBody = new RlcpCheckRequestBody(
                    checks,
                    instructions,
                    generateBySession.get().getResponse()
            );
            RlcpCheckRequest rlcpRequest = rlcpRequestBody.prepareRequest(url);
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
                    "\t\tcode: \"" + generateBySession.get().getResponse().getCode() + "\",\n" +
                    "\t\ttext: \"" + generateBySession.get().getResponse().getText() + "\",\n" +
                    "\t\tinstraction: \"" + generateBySession.get().getResponse().getInstructions() + "\"}}");
        }
        return rlcpResponse;
    }

    private Optional<GenerateRlcp> getGenerateBySession (String session){
        return rlcpDataBaseService.getGenerateBySession(session);
    }
}
