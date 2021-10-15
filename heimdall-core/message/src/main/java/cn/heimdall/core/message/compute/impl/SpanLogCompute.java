package cn.heimdall.core.message.compute.impl;

import cn.heimdall.core.message.body.MessageBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.core.message.metric.DefaultMetricKey;
import cn.heimdall.core.message.metric.Metric;
import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.metric.SpanMetric;
import cn.heimdall.core.message.metric.SpanMetricInvoker;
import cn.heimdall.core.message.trace.SpanLog;
import cn.heimdall.core.message.trace.TraceLog;
import cn.heimdall.core.utils.common.CollectionUtil;

import java.util.List;

public class SpanLogCompute extends AbstractTraceLogCompute {

    public SpanLogCompute() {
        super();
    }

    @Override
    public void compute(MessageBody messageBody) {
        MessageTreeBody treeBody = (MessageTreeBody) messageBody;
        List<SpanLog> childSpanLogs = treeBody.getSpanLogs();
        if (CollectionUtil.isEmpty(childSpanLogs)) {
            childSpanLogs.stream().forEach(this::doInvokeMetric);
        }
    }

    @Override
    protected void doInvokeMetric(TraceLog tracelog) {
        MetricKey metricKey = wrapMetricKey(tracelog);
        SpanMetric spanMetric = (SpanMetric) getMetricInvoker(metricKey);
        SpanLog spanLog = (SpanLog) tracelog;
        spanMetric.addRT(spanLog.getCostInMillis());
        spanMetric.addCount(1);
        if (spanLog.isErrorTag()) {
            spanMetric.addException(1);
        }
    }

    @Override
    protected Metric newMetric() {
        return new SpanMetricInvoker(MessageConstants.METRIC_SPAN_WINDOW_INTERVAL,
                MessageConstants.METRIC_SPAN_WINDOW_COUNT);
    }

    @Override
    protected MetricKey wrapMetricKey(TraceLog tracelog) {
        SpanLog spanLog = (SpanLog) tracelog;
        return new DefaultMetricKey(spanLog.getDomain(), spanLog.getIpAddress(),
                spanLog.getType(), spanLog.getName());
    }

}
