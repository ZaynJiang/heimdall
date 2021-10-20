package cn.heimdall.core.message.body.stroage;

import cn.heimdall.core.message.trace.TraceLog;

import java.util.List;

public class TraceLogBody extends StorageBody{

    private List<TraceLog> eventLogs;

    private List<TraceLog> spanLogs;

}
