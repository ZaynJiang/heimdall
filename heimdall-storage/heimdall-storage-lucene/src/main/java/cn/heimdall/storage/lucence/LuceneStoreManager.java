package cn.heimdall.storage.lucence;

import cn.heimdall.core.message.MessageBody;
import cn.heimdall.core.message.body.store.StoreAppStateRequest;
import cn.heimdall.core.message.body.store.StoreAppStateResponse;
import cn.heimdall.core.message.body.store.StoreMetricRequest;
import cn.heimdall.core.message.body.store.StoreMetricResponse;
import cn.heimdall.core.message.body.store.StoreTraceRequest;
import cn.heimdall.core.message.body.store.StoreTraceResponse;
import cn.heimdall.core.message.body.store.search.SearchAppStateRequest;
import cn.heimdall.core.message.body.store.search.SearchAppStateResponse;
import cn.heimdall.core.message.body.store.search.SearchMetricRequest;
import cn.heimdall.core.message.body.store.search.SearchMetricResponse;
import cn.heimdall.core.message.body.store.search.SearchTraceRequest;
import cn.heimdall.core.message.body.store.search.SearchTraceResponse;
import cn.heimdall.core.utils.annotation.LoadLevel;
import cn.heimdall.core.utils.constants.LoadLevelConstants;
import cn.heimdall.core.utils.enums.StoreDataType;
import cn.heimdall.core.utils.spi.EnhancedServiceLoader;
import cn.heimdall.core.utils.spi.Initialize;
import cn.heimdall.storage.core.StoreManager;
import cn.heimdall.storage.lucence.ssm.AbstractIndex;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LoadLevel(name = LoadLevelConstants.STORE_MANAGER_LUCENE)
public class LuceneStoreManager implements StoreManager, Initialize {
    private final Logger log = LogManager.getLogger(getClass());
    Map<StoreDataType, AbstractIndex> abstractIndexMap;

    @Override
    public void init() {
        List<AbstractIndex> indices = EnhancedServiceLoader.loadAll(AbstractIndex.class);
        abstractIndexMap = indices.stream().collect(Collectors.
                toMap(AbstractIndex::getStoreDataType, a -> a, (k1, k2) -> k1));
    }

    @Override
    public StoreMetricResponse handle(StoreMetricRequest request) {
        MessageBody response;
        try {
            response = abstractIndexMap.get(StoreDataType.STORE_DATA_TYPE_METRIC).write(request);
            return (StoreMetricResponse) response;
        } catch (Exception e) {
            log.error("StoreMetricRequest handle error", e);
        }
        //TODO 返回特定的错误码
        return null;
    }

    @Override
    public StoreTraceResponse handle(StoreTraceRequest request) {
        MessageBody response;
        try {
            response = abstractIndexMap.get(StoreDataType.STORE_DATA_TYPE_TRACE).write(request);
            return (StoreTraceResponse) response;
        } catch (Exception e) {
            log.error("StoreTraceRequest handle error", e);
        }
        //TODO 返回特定的错误码
        return null;
    }

    @Override
    public StoreAppStateResponse handle(StoreAppStateRequest request) {
        MessageBody response;
        try {
            response = abstractIndexMap.get(StoreDataType.STORE_DATA_TYPE_APP_STATE).write(request);
            return (StoreAppStateResponse) response;
        } catch (Exception e) {
            log.error("StoreAppStateRequest handle error", e);
        }
        //TODO 返回特定的错误码
        return null;
    }

    @Override
    public SearchAppStateResponse handle(SearchAppStateRequest request) {
        return null;
    }

    @Override
    public SearchTraceResponse handle(SearchTraceRequest request) {
        return null;
    }

    @Override
    public SearchMetricResponse handle(SearchMetricRequest request) {
        return null;
    }

}
