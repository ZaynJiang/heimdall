package cn.heimdall.core.utils.exception;

public class ImpossibleException extends RuntimeException {

    public ImpossibleException() {
        super();
    }


    public ImpossibleException(String message) {
        super(message);
    }


    public ImpossibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImpossibleException(Throwable cause) {
        super(cause);
    }
}
