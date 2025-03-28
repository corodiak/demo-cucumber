package demo.cucumber.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DemoLogger {

    public enum AnsiColor {
        ANSI_RESET("\u001B[0m"),
        ANSI_RED("\u001B[31m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_WHITE("\u001B[37m");

        private final String code;

        AnsiColor(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    private static class DemoLogFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(record.getInstant(), ZoneId.systemDefault());
            return String.format("%1$tT %2$.4s (%3$.9s) %4$s %n%5$s",
                    dateTime,
                    record.getLevel().getName(),
                    getRealClassName(record.getSourceClassName()),
                    record.getMessage(),
                    record.getThrown() != null ? record.getThrown().getMessage() : ""
            );
        }

        private String getRealClassName(String className) {
            String[] parts = className.split("\\.");
            return parts[parts.length - 1];
        }
    }

    public static Logger getDemoLogger(String className) {
        Logger.getLogger("").getHandlers()[0].setFormatter(new DemoLogFormatter());
        return Logger.getLogger(className);
    }
}
