package demo.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DemoSteps {
    private String expected;
    private String actual;

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
    public void skipped() {

    }
}
