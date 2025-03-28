package demo.cucumber.steps;

import demo.cucumber.common.RunCucumberTest;
import demo.cucumber.common.TestContext;
import demo.cucumber.common.utils.DemoLogger;
import demo.cucumber.pages.WikipediaPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WikipediaPageSteps {
    private static final Logger log = DemoLogger.getDemoLogger(WikipediaPageSteps.class.getName());
    private final TestContext context;

    public WikipediaPageSteps() {
        this.context = RunCucumberTest.getContext();
    }

    @Given("I am on the Wikipedia search page")
    public void iStartOnGoogle() {
        context.setStartUrl("https://www.wikipedia.com");
    }

    @When("^I search for \"(.*)\" in Wikipedia$")
    public void iSearchFor(String query) {
        context.getPage(WikipediaPage.class).enterSearchTerm(query);
    }

    @Then("^I see \"(.*)\" in Website infobox$")
    public void iSeeSearchResults(String result) {
        assertThat("Website infobox entry is different",
                context.getPage(WikipediaPage.class).getWebsiteInfoboxEntry(), containsString(result));
    }
}
