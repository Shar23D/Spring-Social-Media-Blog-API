package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/api")

public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;
    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Account registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }
    
    @PostMapping("/login")
    public Account loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@RequestBody Message message) {
        return messageService.createMesage(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessage(@PathVariable Long messageId) {
        return messageService.getMessage(messageId);
    }
    
    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
    }

    @PatchMapping("/messages/{messageId}")
    public Message updatMessage(@PathVariable Long messageId, @RequestBody String messageText) {
        return messageService.updatMessage(messageId, messageText);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccountId(@PathVariable Long accountId) {
        return messageService.getMessagesByAccountId(accountId);
    }



}
