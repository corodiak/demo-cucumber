package demo.cucumber.steps;

import demo.cucumber.common.RunCucumberTest;
import demo.cucumber.common.TestContext;
import demo.cucumber.pages.YoutubePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class YoutubePageSteps {

    private final TestContext context;

    public YoutubePageSteps() {
        this.context = RunCucumberTest.getContext();
    }

    @Given("I am on the YouTube start page")
    public void iStartOnGoogle() {
        context.setStartUrl("https://www.youtube.com");
    }

    @Given("I dismiss the YouTube cookie popup")
    public void iDismissCookiePopup() {
        context.getPage(YoutubePage.class).dismissCookiePopup();
    }

    @Given("I do not see the YouTube cookie popup")
    public void iCheckCookiePopup() {
        assertThat("Cookie popup is still visible",
                context.getPage(YoutubePage.class).isCookiePopupInvisible());
    }

    @When("^I search for \"(.*)\" in YouTube")
    public void iSearchFor(String query) {
        context.getPage(YoutubePage.class).enterSearchTerm(query);
    }


    @Then("^I see \"(.*)\" in YouTube search results$")
    public void iSeeSearchResults(String result) {
        assertThat("Search results do not contain expected text",
                context.getPage(YoutubePage.class).getSearchResults(), hasItem(result));
    }
}
