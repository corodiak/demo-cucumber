package demo.cucumber.common.model;

public class Result {

    private ResultType type;
    private String message;

    public Result(ResultType type) {
        this.type = type;
        this.message = null;
    }

    public Result(ResultType type, String errorMessage) {
        this.type = type;
        this.message = errorMessage;
    }

    public ResultType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result {" +
                "type: " + type +
                ", errorMessage: '" + message + "'" +
                '}';
    }
}

