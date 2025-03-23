package demo.cucumber.common;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.chrome.ChromeOptions;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("demo.cucumber.features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "demo.cucumber")

public class RunCucumberTest {
    private static TestContext context;

    @Before
    public void setup() {
        System.out.println("SETUP");

        final String hubHost = System.getProperty("hubHost");
        final String hubPort = System.getProperty("hubPort");

        // Create RemoteWebDriver
        RemoteWebDriverBuilder driverBuilder = new RemoteWebDriverBuilder();

        // Create chrome options
        ChromeOptions options = new ChromeOptions();

        driverBuilder.setCapabilities(options);
        driverBuilder.setHub(hubHost, hubPort);

        // Create context
        context = new TestContext(driverBuilder);
    }

    public static TestContext getContext() {
        return context;
    }

    @After
    public void teardown() {
        System.out.println("TEARDOWN");

        // Close RemoteWebdriver
        if (context.isDriverCreated()) {
            context.getDriver().quit();
        }
    }
}
