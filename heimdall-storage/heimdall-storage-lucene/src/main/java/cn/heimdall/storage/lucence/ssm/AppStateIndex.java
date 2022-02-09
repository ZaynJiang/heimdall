package cn.heimdall.storage.lucence.ssm;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.utils.enums.StoreDataType;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;

public class AppStateIndex extends AbstractIndex {

    public AppStateIndex() throws IOException {
        super("/heimdall/lucene/appstate");
        indexWriterConfig = new IndexWriterConfig(new StandardAnalyzer());
    }

    @Override
    public StoreDataType getStoreDataType() {
        return StoreDataType.STORE_DATA_TYPE_APP_STATE;
    }

    @Override
    public MessageBody write(MessageBody messageBody) {
        return null;
    }

}
