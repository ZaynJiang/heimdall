package cn.heimdall.storage.lucence.ssm;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;
import cn.heimdall.core.message.trace.TraceLog;
import cn.heimdall.core.utils.common.CollectionUtil;
import cn.heimdall.core.utils.enums.MetricType;
import cn.heimdall.core.utils.enums.StoreDataType;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TraceIndex extends AbstractIndex {
    public static final String FIELD_NAME_TRACE_ID = "h_traceId";
    public static final String FIELD_NAME_TRACE_TYPE = "h_traceType";
    public static final String FIELD_NAME_ITEM_ID = "h_itemId";
    public static final String FIELD_NAME_ITEM_TYPE = "h_itemType";
    public static final String FIELD_NAME_ITEM_NAME = "h_itemName";
    public static final String FIELD_NAME_ITEM_CONTENT = "h_itemContent";
    public static final String FIELD_NAME_ITEM_ERROR_TAG = "h_errorTag";
    public static final String FIELD_NAME_ITEM_EVENT_TIME = "h_eventTime";
    public static final String FIELD_NAME_ITEM_COST_TIME = "h_costInMillis";
    public static final String FIELD_NAME_ITEM_START_TIME = "h_startTime";
    public static final String FIELD_NAME_ITEM_END_TIME = "h_endTime";
    public static final String FIELD_NAME_ITEM_COMPLETED = "h_completed";

    public TraceIndex() throws IOException {
        super("/heimdall/lucene/trace");
        indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
    }

    @Override
    public StoreDataType getStoreDataType() {
        return StoreDataType.STORE_DATA_TYPE_TRACE;
    }

    @Override
    public MessageBody write(MessageBody messageBody) throws Exception {
        IndexWriter writer = getWriter();
        StoreTraceRequest storeTrace = (StoreTraceRequest) messageBody;
        List<Document> storeItems = new ArrayList<>();
        if (!CollectionUtil.isEmpty(storeTrace.getEventLogs())) {
            storeItems.addAll(storeTrace.getEventLogs().stream().map(log -> {
                Document doc = transToDocFunction().apply(storeTrace, log);
                doc.add(new NumericDocValuesField(FIELD_NAME_ITEM_EVENT_TIME, log.getEventTime()));
                doc.add(new StringField(FIELD_NAME_TRACE_TYPE, MetricType.MetricTypeEvent.getName(), Field.Store.YES));
                return doc;
            }).collect(Collectors.toList()));
        }
        if (!CollectionUtil.isEmpty(storeTrace.getSpanLogs())) {
            storeItems.addAll(storeTrace.getSpanLogs().stream().map(log -> {
                Document doc = transToDocFunction().apply(storeTrace, log);
                doc.add(new NumericDocValuesField(FIELD_NAME_ITEM_COST_TIME, log.getCostInMillis()));
                doc.add(new NumericDocValuesField(FIELD_NAME_ITEM_START_TIME, log.getStartTime()));
                doc.add(new NumericDocValuesField(FIELD_NAME_ITEM_END_TIME, log.getEndTime()));
                doc.add(new StringField(FIELD_NAME_TRACE_TYPE, MetricType.MetricTypeSpan.getName(), Field.Store.YES));
                doc.add(new StringField(FIELD_NAME_ITEM_COMPLETED, log.isErrorTag() ? "1" : "0", Field.Store.YES));
                return doc;
            }).collect(Collectors.toList()));
        }
        writer.addDocuments(storeItems);
        writer.close();
        return new StoreTraceResponse();
    }


    public static BiFunction<StoreTraceRequest, TraceLog, Document> transToDocFunction() {
        return (request, log) -> {
            Document doc = new Document();
            doc.add(new StringField(FIELD_NAME_ITEM_ID, log.getLogId(), Field.Store.YES));
            doc.add(new StringField(FIELD_COMPUTE_IP, request.getAddressIp(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_ITEM_TYPE, log.getType(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_ITEM_NAME, log.getName(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_ITEM_CONTENT, log.getContent(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_APP_NAME, request.getAppName(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_IP, request.getAddressIp(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_TRACE_ID, request.getAddressIp(), Field.Store.YES));
            doc.add(new StringField(FIELD_NAME_ITEM_ERROR_TAG, log.isErrorTag() ? "1" : "0", Field.Store.YES));
            return doc;
        };
    }
}
