package cn.heimdall.core.utils.spi;

import cn.heimdall.core.utils.common.StringUtil;
import cn.heimdall.core.utils.enums.Scope;

final class ExtensionDefinition {
    private String name;
    private Class serviceClass;
    private Integer order;
    private Scope scope;

    public Integer getOrder() {
        return this.order;
    }

    public Class getServiceClass() {
        return this.serviceClass;
    }

    public Scope getScope() {
        return this.scope;
    }

    public ExtensionDefinition(String name, Integer order, Scope scope, Class clazz) {
        this.name = name;
        this.order = order;
        this.scope = scope;
        this.serviceClass = clazz;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((serviceClass == null) ? 0 : serviceClass.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExtensionDefinition other = (ExtensionDefinition)obj;
        if (!StringUtil.equals(name, other.name)) {
            return false;
        }
        if (!serviceClass.equals(other.serviceClass)) {
            return false;
        }
        if (!order.equals(other.order)) {
            return false;
        }
        return !scope.equals(other.scope);
    }


}
