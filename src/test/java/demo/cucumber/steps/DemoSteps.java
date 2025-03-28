package demo.cucumber.steps;

import demo.cucumber.common.RunCucumberTest;
import demo.cucumber.common.TestContext;
import demo.cucumber.common.utils.DemoLogger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DemoSteps {
    private static final Logger log = DemoLogger.getDemoLogger(DemoSteps.class.getName());
    private final TestContext context;

    private String expected;
    private String actual;

    public DemoSteps() {
        this.context = RunCucumberTest.getContext();
    }

    @Given("I set actual result to {string}")
    public void iSetActual(String actual) {
        this.actual = actual;
    }

    @When("I set expected result to {string}")
    public void iSetExpected(String expected) {
        this.expected = expected;
    }

    @Then("^I should be told \"(Yeah|Nope)\"$")
    public void i_should_be_told(String expectedAnswer) {
        String result = actual.equals(expected) ? "Yeah" : "Nope";
        assertThat("Wrong answer", result, equalTo(expectedAnswer));
    }

    @Then("This step should be skipped")
    public void skipped() {}
}
