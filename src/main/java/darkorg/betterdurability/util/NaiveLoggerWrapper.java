package darkorg.betterdurability.util;

import org.apache.logging.log4j.Logger;

public class NaiveLoggerWrapper {
    private final Logger internal;
    public String prefix = "";

    public NaiveLoggerWrapper(Logger internal) {
        this.internal = internal;
    }

    public void debug(String message, Object... params) {
        internal.debug(prefix + message, params);
    }
    public void error(String message, Object... params) {
        internal.error(prefix + message, params);
    }
    public void fatal(String message, Object... params) {
        internal.fatal(prefix + message, params);
    }
    public void info(String message, Object... params) {
        internal.info(prefix + message, params);
    }
    public void trace(String message, Object... params) {
        internal.trace(prefix + message, params);
    }
    public void warn(String message, Object... params) {
        internal.warn(prefix + message, params);
    }

    public NaiveLoggerWrapper withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }
}
