package demo.cucumber.common.utils;

import demo.cucumber.common.model.ResultType;
import demo.cucumber.common.model.Result;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

import java.util.logging.Logger;

public class TestListener implements ConcurrentEventListener {
    private static final Logger log = DemoLogger.getDemoLogger(TestListener.class.getName());

    private static TestCase testCase;
    private static Result stepResult;

    private final EventHandler<TestStepFinished> stepFinishedHandler = this::handleTestStepFinished;

    private final EventHandler<TestCaseStarted> caseHandler = this::handleTestCaseStarted;

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, caseHandler);
    }

    private void handleTestStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            String status = event.getResult().getStatus().toString();
            String message = (event.getResult().getError() != null)
                    ? event.getResult().getError().getMessage()
                    : null;

            log.fine(String.format("Set result of step '%s' to '%s'",
                    ((PickleStepTestStep) event.getTestStep()).getStep().getText(), status));

            stepResult = new Result(
                    ResultType.getType(status),
                    message);
        }
    }

    public static Result getStepResult() {
        return stepResult;
    }

    private void handleTestCaseStarted(TestCaseStarted event) {

        testCase = event.getTestCase();
    }

    public static TestCase getTestCase() {
        return testCase;
    }
}
