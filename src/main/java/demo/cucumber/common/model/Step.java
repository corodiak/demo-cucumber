package demo.cucumber.common.model;

public class Step {
    private final String stepName;
    private final String keyword;
    private Result stepResult;

    public Step(String stepName, String keyword) {
        this.stepName = stepName;
        this.keyword = keyword.toUpperCase();
        this.stepResult = new Result(ResultType.SKIPPED);
    }

    public String getStepName() {
        return stepName;
    }

    public String getKeyword() {
        return keyword;
    }

    public Result getStepResult() {
        return stepResult;
    }

    public void setStepResult(Result stepResult) {
        this.stepResult = stepResult;
    }

    @Override
    public String toString() {
        return "Step {" +
                "stepName: '" + keyword + " " + stepName + "'" +
                ", stepResult: " + stepResult +
                '}';
    }
}
