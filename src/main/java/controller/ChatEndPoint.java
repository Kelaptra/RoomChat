package controller;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import messages.Message;
import messages.PublicMessage;
import service.DBManager;
import utility.MessageDecoder;
import utility.MessageEncoder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(
        value = "/chat",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
public class ChatEndPoint {
//                          username
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session){
        String myUsername = (String) session.getUserProperties().get("username");
        String myRoom_name = (String) session.getUserProperties().get("room_name");
        sessions.put(myUsername, session);
        Map<String, Session> fellowSessions = new ConcurrentHashMap<>();
        for(String username : DBManager.getDBManager().getUsernames(myRoom_name)){
            if(sessions.containsKey(username)){
                fellowSessions.put(username, sessions.get(username));
            }
        }
        DBManager.getDBManager().addUser(myUsername, myRoom_name);
        sendSmbdJoinedMessage(myUsername, fellowSessions);
        sendWelcomeMessage(myUsername, fellowSessions);
    }
    @OnMessage
    public void onMessage(Message message){
        message.sendMessage(sessions);
    }
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("WebSocket error for " + session.getUserProperties().get("username") + " " + throwable.getMessage());
    }
    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        String username = (String) session.getUserProperties().get("username");
        sendLeaveMessage(username, sessions);
        DBManager.getDBManager().removeUser(username);
    }

    public static void sendMessageByServer(String content, String username, Map<String, Session> sessions){
        Message message = PublicMessage.builder()
                .sender("Server")
                .content(content)
                .build();
        if(username != null){
            try {
                sessions.get(username).getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            sessions.forEach((key, value) -> {
                try {
                    value.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public static void sendSmbdJoinedMessage(String username, Map<String, Session> sessions){
        String content = username + " joined [" + sessions.size() + "]";
        sendMessageByServer(content, null, sessions);
    }
    public static void sendWelcomeMessage(String username, Map<String, Session> sessions) {
        String content = "Welcome" + username + "!";
        sendMessageByServer(content, username, sessions);
    }
    public static void sendLeaveMessage(String username, Map<String, Session> sessions){
        String content = username + " left the chat.";
        sendMessageByServer(content, null, sessions);
    }
}
