package demo.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GooglePage extends WebPage {

    @FindBy(css = "#CXQnmb")
    private WebElement cookiePopupModal;

    @FindBy(css = "#S3BnEe")
    private WebElement cookiePopupHeader;

    @FindBy(css =  "#W0wltc")
    private WebElement rejectCookiesButton;

    @FindBy(css = "textarea")
    private WebElement searchInput;

    @FindBy(css = ".zReHs")
    private List<WebElement> searchResultHeaders;

    public GooglePage(WebDriver driver) {
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
                .map(e -> e.getAttribute("href"))
                .toList();
    }
}
