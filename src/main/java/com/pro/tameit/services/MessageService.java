package com.pro.tameit.services;

import com.pro.tameit.models.Message;

import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);
    List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId);
    List<Message> getChatHistoryForUser(Long userId);
}
