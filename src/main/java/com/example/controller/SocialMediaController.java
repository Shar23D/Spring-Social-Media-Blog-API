package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;
    

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty() ||
                account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().build();
        }
        Account existingAccount = accountService.findByUsername(account.getUsername());
        if (existingAccount != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account existingAccount = accountService.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(existingAccount);
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable Integer messageId) {
        Message message = messageService.findById(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Integer messageId) {
        int rowsUpdated = messageService.deleteMessage(messageId);
        if (rowsUpdated == 1) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.ok("");
        }
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() ||
                message.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().build();
        }
        int rowsUpdated = messageService.updateMessage(messageId, message.getMessageText());
        if (rowsUpdated == 1) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccount(accountId);
        return ResponseEntity.ok(messages);
    }
}