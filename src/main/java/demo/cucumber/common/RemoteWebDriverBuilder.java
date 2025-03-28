package demo.cucumber.common;

import demo.cucumber.common.utils.DemoLogger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Logger;

public class RemoteWebDriverBuilder {
    private static final Logger log = DemoLogger.getDemoLogger(RemoteWebDriverBuilder.class.getName());

    private String url;
    private URL hubUrl = null;
    private Capabilities capabilities = null;

    // Set the URL to open the browser with
    public RemoteWebDriverBuilder setStartURL(String url) {
        log.info("Setting start url to " + url);

        this.url = url;
        return this;
    }

    public String getStartUrl() {
        return url;
    }

    public RemoteWebDriverBuilder setHub(String hubHost, String hubPort) {
        String url = "http://" + hubHost + ":" + hubPort + "/wd/hub";
        log.info(String.format("Setting hubURL to '%s'", url));

        try {
            this.hubUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Wrong hub url: " + url, e);
        }
        return this;
    }

    public RemoteWebDriverBuilder setCapabilities(Capabilities capabilities) {
        log.info(String.format("Setting capabilities to '%s'", capabilities));
        this.capabilities = capabilities;
        return this;
    }

    // Build the WebDriver instance
    public WebDriver build() {

        // RemoteWebDriver needs a URL to connect to the Selenium Grid
        if (hubUrl == null) {
            throw new RuntimeException("No hub url specified.");
        }
        // Browser capabilities are needed to specify the browser and its settings
        if (capabilities == null) {
            throw new RuntimeException("No capabilities specified.");
        }
        log.info(String.format("Creating driver on '%s' with capabilities '%s'", hubUrl, capabilities));

        RemoteWebDriver driver = new RemoteWebDriver(hubUrl, capabilities);

        // Set the timeout for the driver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.getDriverTimeout()));

        // Open the browser with the specified URL
        driver.get(url);

        return driver;
    }
}
