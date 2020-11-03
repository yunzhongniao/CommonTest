package org.yunzhong.CommonTest.util.multithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.yunzhong.CommonTest.util.JsonUtil;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author yunzhong
 *
 */
public class ExecutorManagerDemo {

    /**
     * 用户自定义线程名，线程名需要有意义
     * 
     * @author yunzhong
     *
     */
    public static class ThreadFactoryDemo implements ThreadFactory {
        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, ThreadFactoryDemo.class.getSimpleName() + atomicInteger.incrementAndGet());
        }

    }

    /**
     * @author yunzhong
     *
     */
    public static class ThreadPoolInfoDemo {
        private int corePoolSize;
        private int maximumPoolSize;
        private int activeCount;
        private int remainingCapacity;
        private int waitingSize;

        public ThreadPoolInfoDemo(ThreadPoolExecutor threadPoolExecutor) {
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

    /**
     * 
     * <pre>
     * corePoolSize : 核心线程数线程数定义了最小可以同时运行的线程数量。 
     * maximumPoolSize :当队列中存放的任务达到队列容量的时候，当前可以同时运行的线程数量变为最大线程数。
     * keepAliveTime,unit: 存活时间
     * workQueue:当新任务来的时候会先判断当前运行的线程数量是否达到核心线程数，如果达到的话，新任务就会被存放在队列中。
     * handler: workQueue饱和策略.
     * </pre>
     * 
     * <pre>
     * 饱和策略（如果线程数量达到最大线程数量并且队列也已经被放满）：
     * ThreadPoolExecutor.AbortPolicy：抛出 RejectedExecutionException来拒绝新任务的处理。
     * ThreadPoolExecutor.CallerRunsPolicy：调用当前线程运行任务。如果当前程序已关闭，则会丢弃该任务。
     * ThreadPoolExecutor.DiscardPolicy： 不处理新任务，直接丢弃掉。
     * ThreadPoolExecutor.DiscardOldestPolicy： 丢弃最早的未处理的任务请求。
     * </pre>
     */
    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 1000L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 线程数初始为0，上限是Integer.MAX_VALUE。不推荐使用此线程池
     */
    public static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool(new ThreadFactoryDemo());

    /**
     * 线程数最小和最大都是10
     */
    public static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10, new ThreadFactoryDemo());

    /**
     * 线程数最小是10,最大为Integer.MAX_VALUE。不推荐使用此线程池
     */
    public static final ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10,
            new ThreadFactoryDemo());

    /**
     * 线程数最小和最大都是1
     */
    public static final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(new ThreadFactoryDemo());

    public static <T> Future<T> execute(Callable<T> callable) {
        return threadPoolExecutor.submit(callable);
    }

    public static ThreadPoolInfoDemo getInfo() {
        return new ThreadPoolInfoDemo(threadPoolExecutor);
    }

    public static void printInfo() {
        ThreadPoolInfoDemo threadPoolInfo = new ThreadPoolInfoDemo(threadPoolExecutor);
        try {
            System.out.println(JsonUtil.toJson(threadPoolInfo));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
