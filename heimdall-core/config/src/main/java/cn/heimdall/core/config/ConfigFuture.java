package cn.heimdall.core.config;


import cn.heimdall.core.utils.exception.ImpossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConfigFuture {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFuture.class);
    private static final long DEFAULT_CONFIG_TIMEOUT = 5 * 1000;
    private long timeoutMills;
    private long start = System.currentTimeMillis();
    private String dataId;
    private String content;
    private ConfigOperation operation;
    private transient CompletableFuture<Object> origin = new CompletableFuture<>();

    public ConfigFuture(String dataId, String content, ConfigOperation operation) {
        this(dataId, content, operation, DEFAULT_CONFIG_TIMEOUT);
    }


    public ConfigFuture(String dataId, String content, ConfigOperation operation, long timeoutMills) {
        this.dataId = dataId;
        this.content = content;
        this.operation = operation;
        this.timeoutMills = timeoutMills;
    }

    public boolean isTimeout() {
        return System.currentTimeMillis() - start > timeoutMills;
    }

    public Object get() {
        return get(this.timeoutMills, TimeUnit.MILLISECONDS);
    }

    public Object get(long timeout, TimeUnit unit) {
        this.timeoutMills = unit.toMillis(timeout);
        Object result;
        try {
            result = origin.get(timeout, unit);
        } catch (ExecutionException e) {
            throw new ImpossibleException("Should not get results in a multi-threaded environment", e);
        } catch (TimeoutException e) {
            LOGGER.error("config operation timeout,cost:{} ms,op:{},dataId:{}", System.currentTimeMillis() - start, operation.name(), dataId);
            return getFailResult();
        } catch (InterruptedException exx) {
            LOGGER.error("config operate interrupted,error:{}", exx.getMessage(), exx);
            return getFailResult();
        }
        if (operation == ConfigOperation.GET) {
            return result == null ? content : result;
        } else {
            return result == null ? Boolean.FALSE : result;
        }
    }

    private Object getFailResult() {
        if (operation == ConfigOperation.GET) {
            return content;
        } else {
            return Boolean.FALSE;
        }
    }

    public void setResult(Object result) {
        origin.complete(result);
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ConfigOperation getOperation() {
        return operation;
    }

    public void setOperation(ConfigOperation operation) {
        this.operation = operation;
    }

    public enum ConfigOperation {
        GET,
        PUT,
        PUTIFABSENT,
        REMOVE
    }
}
