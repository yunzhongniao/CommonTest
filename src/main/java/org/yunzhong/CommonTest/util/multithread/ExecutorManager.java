package org.yunzhong.CommonTest.util.multithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yunzhong.CommonTest.util.JsonUtil;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author yunzhong
 *
 */
@Service
public class ExecutorManager {

    @Value("${yunzhong.config.corePoolSize:10}")
    private int corePoolSize;

    @Value("${yunzhong.config.maximumPoolSize:100}")
    private int maximumPoolSize;

    @Value("${yunzhong.config.queueSize:100}")
    private int queueSize;

    @Value("${yunzhong.config.keepAliveTime:1000}")
    private int keepAliveTime;

    /**
     * 用户自定义线程名，线程名需要有意义
     * 
     * @author yunzhong
     *
     */
    public static class ThreadFactoryN implements ThreadFactory {
        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            int number = atomicInteger.getAndUpdate(p -> (p + 1) % Integer.MAX_VALUE);
            return new Thread(r, ThreadFactoryN.class.getSimpleName() + number);
        }

    }

    /**
     * @author yunzhong
     *
     */
    public static class ThreadPoolInfo {
        private int corePoolSize;
        private int maximumPoolSize;
        private int activeCount;
        private int remainingCapacity;
        private int waitingSize;

        public ThreadPoolInfo(ThreadPoolExecutor threadPoolExecutor) {
            corePoolSize = threadPoolExecutor.getCorePoolSize();
            maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
            activeCount = threadPoolExecutor.getActiveCount();
            remainingCapacity = threadPoolExecutor.getQueue().remainingCapacity();
            waitingSize = threadPoolExecutor.getQueue().size();
        }

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaximumPoolSize() {
            return maximumPoolSize;
        }

        public void setMaximumPoolSize(int maximumPoolSize) {
            this.maximumPoolSize = maximumPoolSize;
        }

        public int getActiveCount() {
            return activeCount;
        }

        public void setActiveCount(int activeCount) {
            this.activeCount = activeCount;
        }

        public int getRemainingCapacity() {
            return remainingCapacity;
        }

        public void setRemainingCapacity(int remainingCapacity) {
            this.remainingCapacity = remainingCapacity;
        }

        public int getWaitingSize() {
            return waitingSize;
        }

        public void setWaitingSize(int waitingSize) {
            this.waitingSize = waitingSize;
        }

    }

    public ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void postConstruct() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime,
                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(this.queueSize),
                    new ThreadPoolExecutor.AbortPolicy());
        }
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return threadPoolExecutor.submit(callable);
    }

    public ThreadPoolInfo getInfo() {
        return new ThreadPoolInfo(threadPoolExecutor);
    }

    public void printInfo() {
        ThreadPoolInfo threadPoolInfo = new ThreadPoolInfo(threadPoolExecutor);
        try {
            System.out.println(JsonUtil.toJson(threadPoolInfo));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
