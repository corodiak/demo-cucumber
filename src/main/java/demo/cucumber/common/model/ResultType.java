package demo.cucumber.common.model;

import java.util.Arrays;

public enum ResultType {
    PASSED("Passed"),
    FAILED("Failed"),
    SKIPPED("Skipped");

    private final String label;

    ResultType(String label) {
        this.label = label;
    }

    public static ResultType getType(String label) {
        return Arrays.stream(ResultType.values())
                .filter(type -> type.label.equalsIgnoreCase(label))
                .findFirst()
                .orElse(null);
    }
}
