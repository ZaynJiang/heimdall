package cn.heimdall.storage.lucence;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.search.SearchRequest;
import cn.heimdall.core.message.body.store.AbstractStoreRequest;
import cn.heimdall.storage.core.StoreManager;

public class LuceneStoreManager implements StoreManager {
    @Override
    public MessageBody store(AbstractStoreRequest messageBody) {
        return null;
    }

    @Override
    public MessageBody search(SearchRequest searchRequest) {
        return null;
    }
}
