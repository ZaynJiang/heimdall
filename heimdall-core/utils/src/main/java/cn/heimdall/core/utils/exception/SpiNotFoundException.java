package cn.heimdall.core.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpiNotFoundException extends RuntimeException{
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiNotFoundException.class);

    private final ErrorCode errcode;


    public SpiNotFoundException() {
        this(ErrorCode.UnKnown);
    }


    public SpiNotFoundException(ErrorCode error) {
        this(error.getErrorMessage(), error);
    }


    public SpiNotFoundException(String msg) {
        this(msg, ErrorCode.UnKnown);
    }

    public SpiNotFoundException(String msg, ErrorCode errCode) {
        this(null, msg, errCode);
    }

    public SpiNotFoundException(Throwable cause, String msg, ErrorCode errCode) {
        super(msg, cause);
        this.errcode = errCode;
    }

    public SpiNotFoundException(Throwable th) {
        this(th, th.getMessage());
    }

    public SpiNotFoundException(Throwable th, String msg) {
        this(th, msg, ErrorCode.UnKnown);
    }


    public static SpiNotFoundException nestedException(Throwable e) {
        return nestedException("", e);
    }

    public static SpiNotFoundException nestedException(String msg, Throwable e) {
        LOGGER.error(msg, e.getMessage(), e);
        if (e instanceof SpiNotFoundException) {
            return (SpiNotFoundException)e;
        }

        return new SpiNotFoundException(e, msg);
    }
}
