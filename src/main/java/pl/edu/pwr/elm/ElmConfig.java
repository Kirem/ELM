package pl.edu.pwr.elm;

public class ElmConfig {
    public static final String TAG = ElmConfig.class.getSimpleName();
    public static final int DEFAULT_TEST_DATA_SIZE = 25;
    public static final int DEFAULT_VALIDATE_DATA_SIZE = 25;
    public static final int DEFAULT_TRAIN_DATA_SIZE = 50;
    public int numberOfNodes = 0;
    public int numberOfInputClasses = 0;
    public int numberOfOutputClasses = 0;
    public int trainDataPercent = DEFAULT_TRAIN_DATA_SIZE;
    public int validateDataPercent = DEFAULT_VALIDATE_DATA_SIZE;
    public int testDataPercent = DEFAULT_TEST_DATA_SIZE;

}
