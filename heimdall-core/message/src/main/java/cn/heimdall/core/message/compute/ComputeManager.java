package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.compute.impl.EventLogCompute;
import cn.heimdall.core.message.compute.impl.SpanLogCompute;
import cn.heimdall.core.message.compute.impl.AbstractTraceLogCompute;

import java.util.ArrayList;
import java.util.List;

public class ComputeManager {
    //metricKey: domain:key
    private static List<AbstractTraceLogCompute> treeComputes;
    private static List<AbstractTraceLogCompute> heartComputes;

    public ComputeManager() {
        treeComputes = new ArrayList<>();
        treeComputes.add(new SpanLogCompute());
        treeComputes.add(new EventLogCompute());
    }

}
