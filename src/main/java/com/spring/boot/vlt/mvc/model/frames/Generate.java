package com.spring.boot.vlt.mvc.model.frames;

public class Generate {
    private String defaultCode;
    private String defaultText;
    private String deafaultInstructions;
    private String algorithm;


    public Generate(String defaultCode, String defaultText, String deafaultInstructions, String algorithm) {
        this.defaultCode = defaultCode;
        this.defaultText = defaultText;
        this.deafaultInstructions = deafaultInstructions;
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {

        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getDeafaultInstructions() {
        return deafaultInstructions;
    }

    public void setDeafaultInstructions(String deafaultInstructions) {
        this.deafaultInstructions = deafaultInstructions;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

}
