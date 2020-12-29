package org.yunzhong.CommonTest.websocket;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketWorker implements Callable<String> {
    private static final Logger LOG = LoggerFactory.getLogger(WebsocketWorker.class);

    private String category;

    private boolean close = false;

    public WebsocketWorker(String category) {
        this.category = category;
    }

    @Override
    public String call() throws Exception {
        while (!close) {
            WebsocketHandler.sendObject(category, "new message");
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                LOG.error("", e);
            }
        }
        return "close";
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

}
