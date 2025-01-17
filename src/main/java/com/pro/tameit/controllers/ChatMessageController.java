package com.pro.tameit.controllers;

import com.pro.tameit.models.Message;
import com.pro.tameit.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            Message savedMessage = chatMessageService.saveMessage(message);
            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle other unexpected errors
        }
    }

    @GetMapping("/history/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            List<Message> messages = chatMessageService.getMessagesBetweenUsers(senderId, receiverId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle other unexpected errors
        }
    }
}
