package cn.heimdall.core.network.remote;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class RemoteStatus {

    private static final ConcurrentMap<String, RemoteStatus> SERVICE_STATUS_MAP = new ConcurrentHashMap<>();
    private final AtomicLong active = new AtomicLong();
    private final LongAdder total = new LongAdder();

    private RemoteStatus() {
    }

    public static RemoteStatus getStatus(String service) {
        return SERVICE_STATUS_MAP.computeIfAbsent(service, key -> new RemoteStatus());
    }

    public static void removeStatus(String service) {
        SERVICE_STATUS_MAP.remove(service);
    }

    public static void beginCount(String service) {
        getStatus(service).active.incrementAndGet();
    }

    public static void endCount(String service) {
        RemoteStatus rpcStatus = getStatus(service);
        rpcStatus.active.decrementAndGet();
        rpcStatus.total.increment();
    }

    public long getActive() {
        return active.get();
    }

    public long getTotal() {
        return total.longValue();
    }
}
