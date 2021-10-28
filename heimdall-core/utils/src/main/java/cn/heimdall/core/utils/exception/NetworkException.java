package cn.heimdall.core.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkException extends RuntimeException{
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkException.class);

    private final ErrorCode errcode;


    public NetworkException() {
        this(ErrorCode.UnKnown);
    }


    public NetworkException(ErrorCode error) {
        this(error.getErrorMessage(), error);
    }


    public NetworkException(String msg) {
        this(msg, ErrorCode.UnKnown);
    }

    public NetworkException(String msg, ErrorCode errCode) {
        this(null, msg, errCode);
    }

    public NetworkException(Throwable cause, String msg, ErrorCode errCode) {
        super(msg, cause);
        this.errcode = errCode;
    }

    public NetworkException(Throwable th) {
        this(th, th.getMessage());
    }

    public NetworkException(Throwable th, String msg) {
        this(th, msg, ErrorCode.UnKnown);
    }


    public static NetworkException nestedException(Throwable e) {
        return nestedException("", e);
    }

    public static NetworkException nestedException(String msg, Throwable e) {
        LOGGER.error(msg, e.getMessage(), e);
        if (e instanceof NetworkException) {
            return (NetworkException)e;
        }

        return new NetworkException(e, msg);
    }
}
