package demo.cucumber.common.model;


import java.util.List;

public record Scenario(String scenarioName, List<Step> scenarioSteps, List<String> scenarioTags) {

    @Override
    public String toString() {
        return "Scenario {" +
                "scenarioName: '" + scenarioName + "'" +
                ", scenarioSteps: " + scenarioSteps +
                ", scenarioTags: " + scenarioTags +
                '}';
    }
}
