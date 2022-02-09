package cn.heimdall.storage.lucence.ssm;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;
import cn.heimdall.core.message.metric.MetricKey;
import cn.heimdall.core.message.metric.MetricNode;
import cn.heimdall.core.utils.enums.StoreDataType;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;

public class MetricIndex extends AbstractIndex {

    public static final String FILE_NAME_APP_NAME = "h_appName";
    public static final String FILE_NAME_IP = "h_ip";
    public static final String FILE_NAME_METRIC_TYPE = "h_metricType";
    public static final String FILE_NAME_COMPUTE_TYPE = "h_computeType";
    public static final String FILE_NAME_COMPUTE_NAME = "h_computeName";
    public static final String FILE_NAME_METRIC_TIME = "h_metricTime";
    public static final String FILE_NAME_SUCCESS_QPS = "h_successQps";
    public static final String FILE_NAME_EXCEPTION_QPS = "h_exceptionQps";
    public static final String FILE_NAME_RT = "h_rt";

    public MetricIndex() throws IOException {
        super("/heimdall/lucene/metric");
        indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
    }

    @Override
    public StoreDataType getStoreDataType() {
        return StoreDataType.STORE_DATA_TYPE_METRIC;
    }

    @Override
    public MessageBody write(MessageBody messageBody) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        StoreMetricRequest request = (StoreMetricRequest) messageBody;
        MetricNode metricNode = request.getMetricNode();
        MetricKey metricKey = metricNode.getMetricKey();
        //app info
        doc.add(new StringField(FILE_NAME_APP_NAME, request.getAppName(), Field.Store.YES));
        doc.add(new StringField(FILE_NAME_IP, request.getAddressIp(), Field.Store.YES));

        //metric key
        doc.add(new StringField(FILE_NAME_METRIC_TYPE, metricKey.getMetricType().getName(), Field.Store.YES));
        doc.add(new StringField(FILE_NAME_COMPUTE_TYPE, metricKey.getType(), Field.Store.YES));
        doc.add(new StringField(FILE_NAME_COMPUTE_NAME, metricKey.getName(), Field.Store.YES));

        // metric value
        doc.add(new LongPoint(FILE_NAME_SUCCESS_QPS, metricNode.getSuccessQps()));
        doc.add(new LongPoint(FILE_NAME_EXCEPTION_QPS, metricNode.getExceptionQps()));
        doc.add(new LongPoint(FILE_NAME_RT, metricNode.getRt()));

        // time
        doc.add(new LongPoint(FILE_NAME_METRIC_TIME, metricNode.getTimestamp()));
        //TODO 更多
        writer.addDocument(doc);
        writer.close();
        return new StoreTraceResponse();
    }
}
