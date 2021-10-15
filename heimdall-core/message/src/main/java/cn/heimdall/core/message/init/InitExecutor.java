package cn.heimdall.core.message.init;

import cn.heimdall.core.utils.spi.ServiceLoaderUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

public class InitExecutor {
    private static AtomicBoolean initialized = new AtomicBoolean(false);

    private final static Logger log = LogManager.getLogger(InitExecutor.class);

    private InitExecutor() {}

    public static void doInit() {
        if (!initialized.compareAndSet(false, true)) {
            return;
        }
        try {
            ServiceLoader<InitFunc> loader = ServiceLoaderUtil.getServiceLoader(InitFunc.class);
            List<OrderWrapper> initList = new ArrayList<OrderWrapper>();
            for (InitFunc initFunc : loader) {
                log.info("InitExecutor Found init func: " + initFunc.getClass().getCanonicalName());
                insertSorted(initList, initFunc);
            }
            for (OrderWrapper ow : initList) {
                ow.func.init();
                log.info("InitExecutor Executing: " + ow.func.getClass().getCanonicalName() + " : " + ow.order);
            }
        } catch (Exception ex) {
            log.warn("InitExecutor: Initialization failed", ex);
            ex.printStackTrace();
        } catch (Error error) {
            log.warn("InitExecutor: Initialization failed with fatal error", error);
            error.printStackTrace();
        }
    }

    private static void insertSorted(List<OrderWrapper> list, InitFunc func) {
        int order = resolveOrder(func);
        int i = 0;
        for (; i < list.size(); i++) {
            if (list.get(i).getOrder() > order) {
                break;
            }
        }
        list.add(i, new OrderWrapper(order, func));
    }

    private static int resolveOrder(InitFunc func) {
        if (!func.getClass().isAnnotationPresent(InitOrder.class)) {
            return InitOrder.LOWEST_PRECEDENCE;
        } else {
            return func.getClass().getAnnotation(InitOrder.class).value();
        }
    }

    private static class OrderWrapper {
        private final int order;
        private final InitFunc func;

        OrderWrapper(int order, InitFunc func) {
            this.order = order;
            this.func = func;
        }

        int getOrder() {
            return order;
        }

        InitFunc getFunc() {
            return func;
        }
    }
}
