package demo.cucumber.steps;

import demo.cucumber.common.RunCucumberTest;
import demo.cucumber.common.TestContext;
import demo.cucumber.pages.WikipediaPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class CommonSteps {
    private final TestContext context;

    public CommonSteps() {
        this.context = RunCucumberTest.getContext();
    }

    @When("^I wait for (\\d+) seconds?$")
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("^I see \"(.*)\" in page title$")
    public void iCheckTitle(String title) {
        assertThat("Wrong page title",
                context.getPage(WikipediaPage.class).getPageTitle(), containsStringIgnoringCase(title));
    }
}
