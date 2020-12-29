package org.yunzhong.CommonTest.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yunzhong.CommonTest.util.multithread.ThreadPoolFactory;

public class WebsocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketHandler.class);

    private static ConcurrentHashMap<String, Map<String, WebsocketEndpoint>> websocketServers;

    private static ConcurrentHashMap<String, Future<String>> workerFutures;

    private static ConcurrentHashMap<String, WebsocketWorker> workers;

    static {
        websocketServers = new ConcurrentHashMap<String, Map<String, WebsocketEndpoint>>();
        workerFutures = new ConcurrentHashMap<String, Future<String>>();
        workers = new ConcurrentHashMap<String, WebsocketWorker>();
    }

    public static WebsocketEndpoint getWebSocketServer(String category, String user) {
        Map<String, WebsocketEndpoint> userWebsocketMap = websocketServers.get(category);
        if (userWebsocketMap != null) {

            return userWebsocketMap.get(user);
        } else {
            return null;

        }
    }

    public static void put(WebsocketEndpoint webSocketServer) {
        LOGGER.info("add websocket category:{}, user:{}", webSocketServer.getCategory(), webSocketServer.getUser());
        synchronized (websocketServers) {
            if (!websocketServers.contains(webSocketServer.getCategory())) {
                websocketServers.put(webSocketServer.getCategory(), new ConcurrentHashMap<String, WebsocketEndpoint>());
            }
            websocketServers.get(webSocketServer.getCategory()).put(webSocketServer.getUser(), webSocketServer);
            if (!workers.contains(webSocketServer.getCategory())) {
                WebsocketWorker webSocketStatsWorker = new WebsocketWorker(webSocketServer.getCategory());
                workers.put(webSocketServer.getCategory(), webSocketStatsWorker);

                workerFutures.put(webSocketServer.getCategory(),
                        ThreadPoolFactory.getPool().submit(webSocketStatsWorker));
            }
        }
    }

    public static void remove(String category, String user) {
        LOGGER.info("remove websocket category:{}, user:{}", category, user);
        synchronized (websocketServers) {
            if (websocketServers.contains(category)) {
                Map<String, WebsocketEndpoint> userWebSocketMap = websocketServers.get(category);
                userWebSocketMap.remove(user);
                if (userWebSocketMap.isEmpty()) {
                    WebsocketWorker webSocketStatsWorker = workers.remove(category);
                    if (webSocketStatsWorker != null) {
                        webSocketStatsWorker.setClose(true);
                    }
                    Future<String> future = workerFutures.remove(category);
                    if (future != null) {
                        future.cancel(true);
                    }
                }
            }
        }
    }

    public static void sendMessage(String category, String user, String message) throws IOException {
        WebsocketEndpoint webSocketServer = getWebSocketServer(category, user);
        if (webSocketServer != null) {
            webSocketServer.sendMessage(message);
        }
    }

    public static void sendObject(String category, String user, Object message) throws IOException {
        WebsocketEndpoint webSocketServer = getWebSocketServer(category, user);
        if (webSocketServer != null) {

            webSocketServer.sendobject(message);
        }
    }

    public static void sendObject(String category, Object message) throws IOException {
        Map<String, WebsocketEndpoint> userWebsocketMap = websocketServers.get(category);
        if (userWebsocketMap != null) {
            for (WebsocketEndpoint userWebSocket : userWebsocketMap.values()) {
                LOGGER.info("send message to category:{}, user:{", userWebSocket.getCategory(),
                        userWebSocket.getUser());
                try {
                    userWebSocket.sendobject(message);
                } catch (Exception e) {
                    LOGGER.error("Failed to send message to category:{}, user: {l", userWebSocket.getCategory(),
                            userWebSocket.getUser());
                }
            }
        }
    }
}
