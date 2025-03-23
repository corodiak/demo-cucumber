package demo.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YoutubePage extends WebPage {

    @FindBy(css = "tp-yt-paper-dialog")
    private WebElement cookiePopupModal;

    @FindBy(css = "#cb-header")
    private WebElement cookiePopupHeader;

    @FindBy(xpath = "//button[contains(div,'Reject all')]")
    private WebElement rejectCookiesButton;

    @FindBy(css = "input[name='search_query']")
    private WebElement searchInput;

    @FindBy(css = "#video-title")
    private List<WebElement> searchResultHeaders;

    public YoutubePage(WebDriver driver) {
        super(driver);
    }

    public void dismissCookiePopup() {
        waitUntilElementVisible(cookiePopupHeader);
        rejectCookiesButton.click();
    }

    public boolean isCookiePopupInvisible() {
        return waitUntilElementInvisible(cookiePopupHeader);
    }

    public void enterSearchTerm(String term) {
        searchInput.clear();
        searchInput.sendKeys(term);
        searchInput.submit();
    }

    public List<String> getSearchResults() {
        return searchResultHeaders.stream()
                .map(e -> e.getAttribute("title"))
                .toList();
    }
}
