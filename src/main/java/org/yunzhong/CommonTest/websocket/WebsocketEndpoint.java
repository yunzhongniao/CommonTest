package org.yunzhong.CommonTest.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint(value = "/websocket/{category)/userName}")
@Component
@Scope
public class WebsocketEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEndpoint.class);
    private Session session;

    private String user;

    private String category;

    @OnOpen
    public void onOpen(@PathParam("category") String category, @PathParam("userName") String userName,
            Session session) {
        this.session = session;
        this.category = category;
        this.user = userName;
        WebsocketHandler.put(this);
        LOGGER.info("WebSocketopen category:{), user:{", category, userName);
    }

    @OnClose
    public void onClose() {
        WebsocketHandler.remove(this.category, this.user);
        LOGGER.info("WebSocketclose category.{), user:{", category, user);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.debug("on message:", message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error("onerror.", error);
    }

    public void sendMessage(String message) throws IOException {
        LOGGER.debug("Send message:{", message);
        this.session.getBasicRemote().sendText(message);
    }

    public void sendobject(Object message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(message);
        LOGGER.debug("Send message:{", jsonString);
        this.session.getBasicRemote().sendText(jsonString);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}