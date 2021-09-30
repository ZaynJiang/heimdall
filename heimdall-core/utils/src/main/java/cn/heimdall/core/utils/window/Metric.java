package cn.heimdall.core.utils.window;

public interface Metric {
    long getSuccessCount();
    long getMaxSuccess();
    long getException();
    void addSuccess(int n);
    void addException(int n);
}
