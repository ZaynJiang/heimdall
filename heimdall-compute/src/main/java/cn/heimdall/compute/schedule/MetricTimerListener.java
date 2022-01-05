package cn.heimdall.compute.schedule;

import cn.heimdall.compute.analyzer.compute.AbstractMetricCompute;
import cn.heimdall.compute.metric.MetricWhatPulse;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.metric.MetricNode;
import cn.heimdall.core.network.client.StorageRemotingClient;
import cn.heimdall.core.network.remote.RemotingClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;

public class MetricTimerListener implements Runnable {

    private final Logger log = LogManager.getLogger(getClass());

    private final RemotingClient remotingClient;

    public MetricTimerListener(StorageRemotingClient client) {
        remotingClient = client;
    }

    private long lastSecond = -1;

    @Override
    public void run() {
        Map<Long, List<MetricNode>> maps = new TreeMap<>();
        for (Map.Entry<MetricKey, MetricWhatPulse> e : AbstractMetricCompute.getWhatPluses().entrySet()) {
            MetricWhatPulse node = e.getValue();
            Map<Long, MetricNode> metrics = node.metrics();
            aggregate(maps, metrics, node);
        }
        if (!maps.isEmpty()) {
            for (Map.Entry<Long, List<MetricNode>> entry : maps.entrySet()) {
                try {
                    entry.getValue().stream().forEach(metricNode -> {
                        try {
                            metricNode.setTimestamp(entry.getKey());

                            long second = entry.getKey() / 1000;

                            //TODO 远程发送
                            remotingClient.sendSyncRequest(StoreMetricRequest.getRpcMessage(metricNode));

                            lastSecond = second;

                        } catch (TimeoutException e) {
                            log.error("remotingClient sendSyncRequest is error, ", e);
                        }
                    });
                } catch (Exception e) {
                    log.error("metric timer listener is error, ", e);
                }
            }
        }
    }


    private void aggregate(Map<Long, List<MetricNode>> maps, Map<Long, MetricNode> metrics, MetricWhatPulse node) {
        for (Map.Entry<Long, MetricNode> entry : metrics.entrySet()) {
            long time = entry.getKey();
            MetricNode metricNode = entry.getValue();
            metricNode.setMetricKey(node.getMetricKey());
            if (maps.get(time) == null) {
                maps.put(time, new ArrayList<>());
            }
            List<MetricNode> nodes = maps.get(time);
            nodes.add(entry.getValue());
        }
    }

}
