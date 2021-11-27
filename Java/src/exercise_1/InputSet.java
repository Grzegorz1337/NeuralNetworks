package exercise_1;

public class InputSet {
    private double T;

    public int[] getInput() {
        return input;
    }

    public void setInput(int[] input) {
        this.input = input;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public InputSet(int[] input, int result, int digit, boolean passed) {
        this.input = input;
        this.result = result;
        this.digit = digit;
    }

    public InputSet(int[] input) {
        this.input = input;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    private int digit;
    private int[] input;
    private int result;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    private boolean passed;

    public void setTheta(double acceptableLimes) {
        this.T = acceptableLimes;
    }

    public double getTheta() {
        return this.T;
    }
}
