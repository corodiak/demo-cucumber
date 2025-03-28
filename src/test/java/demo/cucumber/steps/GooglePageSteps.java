package demo.cucumber.steps;

import demo.cucumber.common.RunCucumberTest;
import demo.cucumber.common.TestContext;
import demo.cucumber.common.utils.DemoLogger;
import demo.cucumber.pages.GooglePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GooglePageSteps {
    private static final Logger log = DemoLogger.getDemoLogger(GooglePageSteps.class.getName());
    private final TestContext context;

    public GooglePageSteps() {
        this.context = RunCucumberTest.getContext();
    }

    @Given("I am on the Google search page")
    public void iStartOnGoogle() {
        context.setStartUrl("https://www.google.com");
    }

    @Given("I dismiss the Google cookie popup")
    public void iDismissCookiePopup() {
        context.getPage(GooglePage.class).dismissCookiePopup();
    }

    @Given("I do not see the Google cookie popup")
    public void iCheckCookiePopup() {
        assertThat("Cookie popup is still visible",
                context.getPage(GooglePage.class).isCookiePopupInvisible());
    }

    @When("^I search for \"(.*)\" in Google$")
    public void iSearchFor(String query) {
        context.getPage(GooglePage.class).enterSearchTerm(query);
    }


    @Then("^I see \"(.*)\" in Google search results$")
    public void iSeeSearchResults(String result) {
        assertThat("Search results do not contain expected text",
                context.getPage(GooglePage.class).getSearchResults(), hasItem(result));
    }
}
