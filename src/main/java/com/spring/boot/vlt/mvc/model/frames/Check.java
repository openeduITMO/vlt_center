package com.spring.boot.vlt.mvc.model.frames;

public class Check {
    private int testId;
    private int limitOnTest;

//    private enum TimeScale {Day, Hour, Minute, Second}
//    private TimeScale scale;
    private String timeScale;

    private String testInput;
    private String testOutput;

    public Check(int testId, int limitOnTest, String timeScale, String testInput, String testOutput) {
        this.testId = testId;
        this.limitOnTest = limitOnTest;
        this.timeScale = timeScale;
        this.testInput = testInput;
        this.testOutput = testOutput;
    }

    public int getTestId() {

        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getLimitOnTest() {
        return limitOnTest;
    }

    public void setLimitOnTest(int limitOnTest) {
        this.limitOnTest = limitOnTest;
    }

    public String getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(String timeScale) {
        this.timeScale = timeScale;
    }

    public String getTestInput() {
        return testInput;
    }

    public void setTestInput(String testInput) {
        this.testInput = testInput;
    }

    public String getTestOutput() {
        return testOutput;
    }

    public void setTestOutput(String testOutput) {
        this.testOutput = testOutput;
    }
}
