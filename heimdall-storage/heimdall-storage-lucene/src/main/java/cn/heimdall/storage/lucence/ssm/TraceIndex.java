package cn.heimdall.storage.lucence.ssm;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;
import cn.heimdall.core.utils.enums.StoreDataType;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;

public class TraceIndex extends AbstractIndex {
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
        Document doc = new Document();
        StoreTraceRequest request = (StoreTraceRequest) messageBody;
        doc.add(new StringField("traceId", request.getTraceId(), Field.Store.YES));
        doc.add(new StringField("appName", request.getAppName(), Field.Store.YES));
        doc.add(new StringField("ip", request.getAddressIp(), Field.Store.YES));
        //TODO 更多
        writer.addDocument(doc);
        writer.close();
        return new StoreTraceResponse();
    }
}
