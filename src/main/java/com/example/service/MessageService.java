package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
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
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().trim().isEmpty()) {
            throw new RuntimeException("Message text cannot be empty");
        }
        if (message.getMessageText().length() > 254) {
            throw new RuntimeException("Message text cannot be longer than 254 characters");
        }
        Account account = accountRepository.findById(message.getPostedBy()).orElseThrow(() -> new RuntimeException("Account not found"));
        return messageRepository.save(message);
    }


    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message findById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    public int deleteMessage(Integer id) {
        messageRepository.deleteById(id);
        return 1; // return 1 if deleted successfully
    }

    public int updateMessage(Integer id, String messageText) {
        Message message = findById(id);
        if (message != null) {
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1; // return 1 if updated successfully
        } else {
            return 0; // return 0 if message not found
        }
    }

    public List<Message> getMessagesByAccount(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
