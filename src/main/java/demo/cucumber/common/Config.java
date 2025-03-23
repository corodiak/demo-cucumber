package demo.cucumber.common;

public class Config {

    private static final int DEFAULT_DRIVER_TIMEOUT_SECONDS = 10;

    public static int getDriverTimeout() {
        return DEFAULT_DRIVER_TIMEOUT_SECONDS;
    }
}
