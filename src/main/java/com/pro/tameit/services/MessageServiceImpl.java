package com.pro.tameit.services;

import com.pro.tameit.models.Message;
import com.pro.tameit.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository chatMessageRepository;

    @Override
    public Message saveMessage(Message message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        return chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public List<Message> getChatHistoryForUser(Long userId) {
        return chatMessageRepository.findByReceiverId(userId);
    }
}

