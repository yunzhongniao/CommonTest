package org.yunzhong.CommonTest.util.multithread;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.yunzhong.CommonTest.util.SpringUtil;
import org.yunzhong.CommonTest.util.multithread.ThreadPoolsInfo.ThreadPoolInfo;

public class ThreadPoolFactory {
    public static final String DEFAULT_POOL = "DEFAULT_POOL";

    private static final Map<String, ThreadPoolExecutor> poolMap = new ConcurrentHashMap<>();

    private static ThreadPoolConfig threadPoolConfig = null;

    public static ThreadPoolExecutor getPool() {
        return getPool(DEFAULT_POOL);
    }

    /**
     * @param poolName
     * @return
     */
    public static ThreadPoolExecutor getPool(String poolName) {
        if (poolMap.containsKey(poolName)) {
            return poolMap.get(poolName);
        } else {
            synchronized (poolMap) {
                if (!poolMap.containsKey(poolName)) {
                    if (threadPoolConfig == null) {
                        threadPoolConfig = SpringUtil.getBean(ThreadPoolConfig.class);
                    }
                    poolMap.put(poolName,
                            new ThreadPoolExecutor(threadPoolConfig.getCorePoolSize(),
                                    threadPoolConfig.getMaximumPoolSize(), threadPoolConfig.getKeepAliveTime(),
                                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(threadPoolConfig.getQueueSize()),
                                    new ThreadPoolThreadFactory(DEFAULT_POOL), new ThreadPoolExecutor.AbortPolicy()));
                }
            }
            return poolMap.get(poolName);
        }
    }

    /**
     * @return
     */
    public static ThreadPoolsInfo getInfo() {
        ThreadPoolsInfo info = new ThreadPoolsInfo();
        Iterator<Entry<String, ThreadPoolExecutor>> iterator = poolMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, ThreadPoolExecutor> next = iterator.next();
            String key = next.getKey();
            ThreadPoolExecutor value = next.getValue();
            ThreadPoolInfo poolInfo = new ThreadPoolInfo(value);
            info.getInfos().put(key, poolInfo);
            info.appendActiveCount(poolInfo.getActiveCount());
            info.appendWaitingSize(poolInfo.getWaitingSize());
        }
        return info;
    }
}
