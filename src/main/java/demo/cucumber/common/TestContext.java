package demo.cucumber.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

// Contains the context of the test to share between steps
// - driver which is used to interact with the browser
// - method to get the page object

public class TestContext {
    private WebDriver driver = null;
    private final RemoteWebDriverBuilder driverBuilder;

    public TestContext(RemoteWebDriverBuilder driverBuilder) {
        this.driverBuilder = driverBuilder;
    }

    public boolean isDriverCreated() {
        return this.driver != null;
    }

    public void setStartUrl(String url) {
        if (!isDriverCreated()) {
            driverBuilder.setStartURL(url);
        } else {
            throw new RuntimeException("Driver was already created. Cannot set start url anymore.");
        }
    }

    public RemoteWebDriver getDriver() {
        if (!isDriverCreated()) {
            this.driver = driverBuilder.build();
        }
        return (RemoteWebDriver) this.driver;
    }

    public <T> T getPage(Class<T> pageClass) {
        return PageFactory.initElements(getDriver(), pageClass);
    }
}
