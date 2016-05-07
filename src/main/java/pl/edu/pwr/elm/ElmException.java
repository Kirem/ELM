package pl.edu.pwr.elm;

public class ElmException extends RuntimeException {
    public static final String TAG = ElmException.class.getSimpleName();

    public ElmException() {
    }

    public ElmException(String message) {
        super(message);
    }

    public ElmException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElmException(Throwable cause) {
        super(cause);
    }

    public ElmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
