package cn.heimdall.core.network.remote;

import cn.heimdall.core.message.Message;
import cn.heimdall.core.utils.exception.ImpossibleException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MessageFuture {
    private Message requestMessage;
    private long timeout;
    private long start = System.currentTimeMillis();
    private transient CompletableFuture<Object> future = new CompletableFuture<>();


    public boolean isTimeout() {
        return System.currentTimeMillis() - start > timeout;
    }

    public Object get(long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
        Object result;
        try {
            result = future.get(timeout, unit);
        } catch (ExecutionException e) {
            throw new ImpossibleException("MessageFuture get ExecutionException", e);
        } catch (TimeoutException e) {
            throw new TimeoutException("cost " + (System.currentTimeMillis() - start) + " ms");
        }
        if (result instanceof RuntimeException) {
            throw (RuntimeException)result;
        } else if (result instanceof Throwable) {
            throw new RuntimeException((Throwable)result);
        }

        return result;
    }

    public void setResultMessage(Object obj) {
        future.complete(obj);
    }

    public Message getRequestMessage() {
        return requestMessage;
    }


    public void setRequestMessage(Message requestMessage) {
        this.requestMessage = requestMessage;
    }


    public long getTimeout() {
        return timeout;
    }


    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
