package cn.heimdall.core.message.compute;

import cn.heimdall.core.message.compute.impl.Compute;
import cn.heimdall.core.message.compute.impl.EventLogCompute;
import cn.heimdall.core.message.compute.impl.SpanLogCompute;
import cn.heimdall.core.message.compute.impl.TraceLogCompute;

public class ComputeManager {
    private static volatile Compute spanMetricCompute;
    private static volatile Compute eventMetricCompute;
    private static volatile Compute heartbeatMetricCompute;
    private static volatile Compute traceLogCompute;

    public static Compute singleSpanMetricCompute(){
        if (spanMetricCompute == null){
            synchronized (Compute.class){
                if (spanMetricCompute == null){
                    spanMetricCompute = new SpanLogCompute();
                }
            }
        }
        return spanMetricCompute;
    }

    public static Compute singleTraceLogCompute(){
        if (traceLogCompute == null){
            synchronized (Compute.class){
                if (traceLogCompute == null){
                    traceLogCompute = new TraceLogCompute();
                }
            }
        }
        return traceLogCompute;
    }

    public static Compute singleEventMetricCompute(){
        if (eventMetricCompute == null){
            synchronized (Compute.class){
                if (eventMetricCompute == null){
                    eventMetricCompute = new EventLogCompute();
                }
            }
        }
        return eventMetricCompute;
    }

    public static Compute singleHeartbeatMetricCompute(){
        if (heartbeatMetricCompute == null){
            synchronized (Compute.class){
                if (heartbeatMetricCompute == null){
                    heartbeatMetricCompute = new EventLogCompute();
                }
            }
        }
        return heartbeatMetricCompute;
    }
}
