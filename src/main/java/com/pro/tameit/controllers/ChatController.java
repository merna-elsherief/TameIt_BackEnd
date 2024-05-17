package com.pro.tameit.controllers;


import com.pro.tameit.models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller("/sw")
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        if (chatMessage.getRecipient() != null && !chatMessage.getRecipient().isEmpty()) {
            // Private chat
            messagingTemplate.convertAndSendToUser(chatMessage.getRecipient(), "/queue/private", chatMessage);
        }
    }
    @MessageMapping("/chat.addPrivateUser")
    public void addPrivateUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session for private chat
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSendToUser(chatMessage.getRecipient(), "/queue/private", chatMessage);
    }
}

