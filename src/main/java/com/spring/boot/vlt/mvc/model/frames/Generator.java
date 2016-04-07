package com.spring.boot.vlt.mvc.model.frames;

import rlcp.generate.GeneratingResult;
import rlcp.generate.RlcpGenerateRequestBody;

public class Generator {

    private GeneratingResult result;
    private RlcpGenerateRequestBody body;


    public Generator(GeneratingResult generatingResult, RlcpGenerateRequestBody rlcpGenerateRequestBody) {
        this.result = generatingResult;
        this.body = rlcpGenerateRequestBody;
    }

    public GeneratingResult getResult() {
        return result;
    }

    public void setResult(GeneratingResult result) {
        this.result = result;
    }

    public RlcpGenerateRequestBody getBody() {
        return body;
    }

    public void setBody(RlcpGenerateRequestBody body) {
        this.body = body;
    }
}
