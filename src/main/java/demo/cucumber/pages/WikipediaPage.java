package demo.cucumber.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WikipediaPage extends WebPage {

    @FindBy(css = "input#searchInput")
    private WebElement searchInput;

    @FindBy(css = ".infobox tr")
    private List<WebElement> infoboxEntries;

    public WikipediaPage(WebDriver driver) {
        super(driver);
    }

    public void enterSearchTerm(String query) {
        searchInput.clear();
        searchInput.sendKeys(query);
        searchInput.submit();
    }

    public String getWebsiteInfoboxEntry() {
        for (WebElement row : infoboxEntries) {
            if (row.getText().contains("Website")) {
                return row.getText();
            }
        }
        return "No such entry found";
    }

}
