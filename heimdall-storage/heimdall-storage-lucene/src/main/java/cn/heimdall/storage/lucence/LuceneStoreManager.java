package cn.heimdall.storage.lucence;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.search.SearchRequest;
import cn.heimdall.core.message.body.store.AbstractStoreRequest;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.storage.core.StoreManager;

@LoadLevel(name = LoadLevelConstants.STORE_MANAGER_LUCENE)
public class LuceneStoreManager implements StoreManager {

    @Override
    public MessageBody store(AbstractStoreRequest messageBody) {
        //TODO 进行lucene存储.
        return null;
    }

    @Override
    public MessageBody search(SearchRequest searchRequest) {
        return null;
    }
}
