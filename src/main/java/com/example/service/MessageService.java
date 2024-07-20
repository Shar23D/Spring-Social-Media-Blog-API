package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.exception.NotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
            this.messageRepository = messageRepository;
            this.accountRepository = accountRepository;
    }

    @Transactional
    public Message createMesage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new BadRequestException("Message should not be blank and be less than 255 characters");
        }
        if(!accountRepository.existsById(message.getPostedBy())) {
            throw new BadRequestException("Account does not exist");
        }
        return messageRepository.save(message);
    }
    
    @Transactional(readOnly = true)
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Message getMessage(Integer messageId) {
        return messageRepository.findById(messageId).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    @Transactional
    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    @Transactional
    public Message updatMessage(Integer messageId, String messageText) {
        Message message = getMessage(messageId);
        if (messageText.isBlank() || messageText.length() > 255) {
            throw new BadRequestException("Invalid message length");
        }
        message.setMessageText(messageText);
        return messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<Message> getMessagesByPostedBy(Integer postedBy) {
        return messageRepository.findMessagesByPostedBy(postedBy);
    }
}
