package cn.heimdall.core.utils.property;

public interface HeimdallProperty<T> {
    void addListener(PropertyListener<T> listener);

    void removeListener(PropertyListener<T> listener);

    boolean updateValue(T newValue);
}
