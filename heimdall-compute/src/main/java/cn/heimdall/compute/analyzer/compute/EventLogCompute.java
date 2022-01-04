package cn.heimdall.compute.analyzer.compute;

import cn.heimdall.compute.metric.DefaultMetricKey;
import cn.heimdall.compute.metric.MetricKey;
import cn.heimdall.compute.metric.MetricWhatPulse;
import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.origin.MessageTreeRequest;
import cn.heimdall.core.message.trace.EventLog;
import cn.heimdall.core.message.trace.SpanLog;
import cn.heimdall.core.message.trace.TraceLog;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.constants.LoadLevelConstants;

import java.util.List;

@LoadLevel(name = LoadLevelConstants.EVENT_COMPUTE)
public class EventLogCompute extends AbstractMetricCompute {

    @Override
    public void compute(MessageBody messageBody) {
        MessageTreeRequest messageTreeRequest = (MessageTreeRequest) messageBody;
        List<EventLog> eventLogs = messageTreeRequest.getEventLogs();
        if (!CollectionUtil.isEmpty(eventLogs)) {
            eventLogs.stream().forEach(this::doInvokeMetric);
        }
    }

    @Override
    protected void doInvokeMetric(TraceLog tracelog) {
        MetricWhatPulse metric = getMetricInvoker(wrapMetricKey(tracelog));
        EventLog eventLog = (EventLog) tracelog;
        metric.addSuccess(1);
        if (eventLog.isErrorTag()) {
            metric.addException(1);
        }
    }


    @Override
    protected MetricWhatPulse newMetric() {
        return new MetricWhatPulse();
    }

    @Override
    protected MetricKey wrapMetricKey(TraceLog tracelog) {
        SpanLog spanLog = (SpanLog) tracelog;
        return new DefaultMetricKey(spanLog.getDomain(), spanLog.getIpAddress(),
                spanLog.getType(), spanLog.getName());
    }

}
