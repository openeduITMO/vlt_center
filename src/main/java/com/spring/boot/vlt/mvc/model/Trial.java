package com.spring.boot.vlt.mvc.model;

import com.spring.boot.vlt.mvc.model.vl.VirtLab;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import rlcp.calculate.CalculatingResult;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;

import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Trial {
    public Trial() {
    }

    private VirtLab vl;
    private boolean isConnect = true;
    private GeneratingResult generatingResult;
    private CalculatingResult calculatingResult;
    private List<ConditionForChecking> conditionsList;
    private String url;

    public VirtLab getVl() {
        return vl;
    }

    public void setVl(VirtLab vl) {
        this.vl = vl;
    }

    public GeneratingResult getGeneratingResult() {
        return generatingResult;
    }

    public void setGeneratingResult(GeneratingResult generatingResult) {
        this.generatingResult = generatingResult;
    }

    public CalculatingResult getCalculatingResult() {
        return calculatingResult;
    }

    public void setCalculatingResult(CalculatingResult calculatingResult) {
        this.calculatingResult = calculatingResult;
    }

    public List<ConditionForChecking> getConditionsList() {
        return conditionsList;
    }

    public void setConditionsList(List<ConditionForChecking> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
