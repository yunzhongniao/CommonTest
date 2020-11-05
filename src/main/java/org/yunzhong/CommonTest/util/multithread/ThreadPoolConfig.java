package org.yunzhong.CommonTest.util.multithread;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yunzhong
 *
 */
@Component
public class ThreadPoolConfig {

    @Value("${yunzhong.config.corePoolSize:10}")
    private int corePoolSize;

    @Value("${yunzhong.config.maximumPoolSize:100}")
    private int maximumPoolSize;

    @Value("${yunzhong.config.queueSize:100}")
    private int queueSize;

    @Value("${yunzhong.config.keepAliveTime:1000}")
    private int keepAliveTime;

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

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

}
