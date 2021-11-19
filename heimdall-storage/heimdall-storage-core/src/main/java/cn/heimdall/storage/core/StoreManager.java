package cn.heimdall.storage.core;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.search.SearchRequest;
import cn.heimdall.core.message.body.store.AbstractStoreRequest;

public interface StoreManager {
    MessageBody store(AbstractStoreRequest messageBody);
    MessageBody search(SearchRequest searchRequest);
}
