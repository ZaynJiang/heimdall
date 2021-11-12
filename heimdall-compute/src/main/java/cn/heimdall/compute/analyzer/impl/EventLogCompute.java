package cn.heimdall.compute.analyzer.impl;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.MessageTreeBody;
import cn.heimdall.core.config.constants.MessageConstants;
import cn.heimdall.compute.metric.DefaultMetricKey;
import cn.heimdall.compute.metric.EventMetric;
import cn.heimdall.compute.metric.Metric;
import cn.heimdall.compute.metric.MetricKey;
import cn.heimdall.compute.metric.SpanMetricInvoker;
import cn.heimdall.core.message.trace.EventLog;
import cn.heimdall.core.message.trace.SpanLog;
import cn.heimdall.core.message.trace.TraceLog;
import cn.heimdall.core.utils.common.CollectionUtil;

import java.util.List;

public class EventLogCompute extends AbstractMetricCompute {

    @Override
    public void compute(MessageBody messageBody) {
        MessageTreeBody messageTreeBody = (MessageTreeBody) messageBody;
        List<EventLog> eventLogs = messageTreeBody.getEventLogs();
        if (!CollectionUtil.isEmpty(eventLogs)) {
            eventLogs.stream().forEach(this::doInvokeMetric);
        }
    }

    @Override
    protected void doInvokeMetric(TraceLog tracelog) {
        Metric metric = getMetricInvoker(wrapMetricKey(tracelog));
        EventMetric eventMetric = (EventMetric) metric;
        EventLog eventLog = (EventLog) tracelog;
        eventMetric.addCount(1);
        if (eventLog.isErrorTag()) {
            eventMetric.addException(1);
        }
    }


    @Override
    protected Metric newMetric() {
        return new SpanMetricInvoker(MessageConstants.METRIC_EVENT_WINDOW_INTERVAL,
                MessageConstants.METRIC_EVENT_WINDOW_COUNT);
    }

    @Override
    protected MetricKey wrapMetricKey(TraceLog tracelog) {
        SpanLog spanLog = (SpanLog) tracelog;
        return new DefaultMetricKey(spanLog.getDomain(), spanLog.getIpAddress(),
                spanLog.getType(), spanLog.getName());
    }

}
