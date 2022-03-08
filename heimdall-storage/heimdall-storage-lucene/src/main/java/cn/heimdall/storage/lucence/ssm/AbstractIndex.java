package cn.heimdall.storage.lucence.ssm;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.utils.enums.StoreDataType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class AbstractIndex {
    public static final String FIELD_NAME_APP_NAME = "h_appName";
    public static final String FIELD_NAME_IP = "h_ip";
    public static final String FIELD_COMPUTE_IP = "h_computeIp";

    protected Directory dir;
    protected IndexWriterConfig indexWriterConfig;

    public AbstractIndex(String path) throws IOException {
        this.dir = FSDirectory.open(Paths.get(path));
    }

    protected IndexWriter getWriter() throws Exception {
        IndexWriter writer = new IndexWriter(dir, indexWriterConfig);
        return writer;
    }

    public abstract StoreDataType getStoreDataType();
    public abstract MessageBody write(MessageBody messageBody) throws Exception;
}
