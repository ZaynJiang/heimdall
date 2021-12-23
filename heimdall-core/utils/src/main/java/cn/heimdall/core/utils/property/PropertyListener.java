
package cn.heimdall.core.utils.property;


public interface PropertyListener<T> {

    void configUpdate(T value);

    void configLoad(T value);
}
