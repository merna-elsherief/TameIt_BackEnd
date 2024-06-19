package com.pro.tameit.controllers;

import com.pro.tameit.models.Message;
import com.pro.tameit.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {
    @Autowired
    private MessageService chatMessageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = chatMessageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/history/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable Long senderId, @PathVariable Long receiverId) {
        List<Message> messages = chatMessageService.getMessagesBetweenUsers(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }
}
