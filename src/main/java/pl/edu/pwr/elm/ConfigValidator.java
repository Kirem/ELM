package pl.edu.pwr.elm;

import static java.lang.String.format;

public class ConfigValidator {
    public static final String TAG = ConfigValidator.class.getSimpleName();
    public static final String EXCEPTION_TEXT = "Check your configuration: %s";
    private ElmConfig config;

    private ConfigValidator(ElmConfig config) {
        this.config = config;
    }

    static ConfigValidator with(ElmConfig config) {
        return new ConfigValidator(config);
    }

    public void validate() {
        if (config.numberOfInputClasses <= 0) {
            throwExc(format("wrong number of input classes: %d. Value must be > 0", config.numberOfInputClasses));
        }
        if (config.numberOfNodes <= 0) {
            throwExc(format("wrong number of nodes classes: %d. Value must be > 0", config.numberOfNodes));
        }
        if (config.numberOfOutputClasses <= 0) {
            throwExc(format("wrong number of input classes: %d. Value must be > 0", config.numberOfOutputClasses));
        }
        int percent = config.testDataPercent + config.validateDataPercent + config.trainDataPercent;
        if (percent != 100) {
            throwExc(format("Sum of percent must sum up to 100, but was %d", percent));
        }
    }

    private void throwExc(String text) {
        throw new ElmException(format(EXCEPTION_TEXT, text));
    }
}
