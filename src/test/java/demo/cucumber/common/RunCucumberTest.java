package demo.cucumber.common;

import demo.cucumber.common.model.ResultType;
import demo.cucumber.common.model.Step;
import demo.cucumber.common.model.Scenario;
import demo.cucumber.common.utils.DemoLogger;
import demo.cucumber.common.utils.TestListener;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static demo.cucumber.common.utils.DemoLogger.AnsiColor.*;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("demo.cucumber.features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "me.jvt.cucumber.report.PrettyReports:target/cucumber-report," +
        "html:target/cucumber-report.html," +
        "json:target/cucumber-report.json," +
        "demo.cucumber.common.utils.TestListener")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "demo.cucumber")

public class RunCucumberTest {
    private static TestContext context;
    private static Scenario scenario;
    private static Iterator<Step> stepIterator;
    private static Step currentStep;
    private static final int LOG_REPEATER = 80;

    private static final Logger log = DemoLogger.getDemoLogger(RunCucumberTest.class.getName());

    public static TestContext getContext() {
        return context;
    }

    @Before
    public void setup() {
        // Get the current executed testcase
        TestCase testCase = TestListener.getTestCase();

        // Create scenario object
        scenario = new Scenario(
                testCase.getName(),
                populateTestSteps(testCase),
                testCase.getTags());

        // Create step iterator to keep track of current step
        stepIterator = scenario.scenarioSteps().iterator();

        printScenarioHeader();

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

    // Get all steps of the executed testcase
    private List<Step> populateTestSteps(TestCase testCase) {
        return testCase.getTestSteps().stream()
                .filter(step -> step instanceof PickleStepTestStep)
                .map(step -> (PickleStepTestStep) step)
                .map(pickleStep -> new Step(
                        pickleStep.getStep().getText(),
                        pickleStep.getStep().getKeyword()
                ))
                .collect(Collectors.toList());
    }

    @BeforeStep
    public void beforeStep() {
        currentStep = stepIterator.next();
        printStepHeader();
    }

    @AfterStep
    public void afterStep() {
        currentStep.setStepResult(TestListener.getStepResult());
        log.info(currentStep.getStepResult().getType().toString());
    }

    @After
    public void teardown() {
        // Print scenario summary
        printScenarioSummary();

        // Close RemoteWebdriver
        if (context.isDriverCreated()) {
            log.info("Closing RemoteWebDriver...");
            context.getDriver().quit();
        }
    }

    private void printScenarioHeader() {
        log.info("=".repeat(LOG_REPEATER));
        log.info("TAGS: " + String.join(" ", scenario.scenarioTags()));
        log.info("SCENARIO: \"" + scenario.scenarioName() + "\"");
        log.info("=".repeat(LOG_REPEATER));
    }

    private void printStepHeader() {
        log.info("-".repeat(LOG_REPEATER));
        log.info("STEP: \"" + currentStep.getStepName() + "\" STARTED");
        log.info("-".repeat(LOG_REPEATER));
    }

    private void printScenarioSummary() {
        log.info("=".repeat(LOG_REPEATER));
        log.info(":".repeat(31) + " SCENARIO SUMMARY " + ":".repeat(31));
        log.info("::- SCENARIO TAGS: " + String.join(" ", scenario.scenarioTags()));
        log.info("::- SCENARIO NAME: \"" + scenario.scenarioName() + "\"");
        for (Step step : scenario.scenarioSteps()) {
            if (step.getStepResult().getType() == ResultType.PASSED) {
                log.info(createStepSummaryString(ANSI_GREEN.getCode(), step));
            } else if (step.getStepResult().getType() == ResultType.FAILED) {
                log.info(createStepSummaryString(ANSI_RED.getCode(), step));
            } else {
                log.info(createStepSummaryString(ANSI_YELLOW.getCode(), step));
            }
        }
        log.info(":".repeat(LOG_REPEATER));
    }

    // Template for step summary string
    private String createStepSummaryString(String ansiColor, Step step) {
        return String.format("::--> %s%s\t\t\t%s\t%s%s",
                ansiColor, step.getStepResult().getType(), step.getKeyword(), step.getStepName(), ANSI_RESET.getCode());
    }
}
