package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.entity.*;
import com.example.exception.DuplicateUserException;
import com.example.service.*;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {


    private final MessageService messageService;
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService)
    {
        this.messageService=messageService;
        this.accountService=accountService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message inMessage)
    {
        try{
            return ResponseEntity.status(200).body(messageService.postMessage(inMessage));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId)
    {

        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Integer messageId)
    {
        int countDeleted=messageService.deleteMessage(messageId);
        if (countDeleted==0)
        {
            return ResponseEntity.status(200).body("");
        }
        return ResponseEntity.status(200).body("" +countDeleted);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessage(@RequestBody Message message, @PathVariable Integer messageId)
    {
        try{
        messageService.updateMessage(message,messageId);
        return ResponseEntity.status(200).body(""+1);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(400).body("");
        }
        
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAuthor(@PathVariable Integer accountId)
    {
        return ResponseEntity.status(200).body(messageService.getMessagesByAuthor(accountId));
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account inAccount)
    {
        try
        {
            return ResponseEntity.status(200).body(accountService.addAccount(inAccount));
        }
        catch(DuplicateUserException e)
        {
            return ResponseEntity.status(409).body(null);
        }
        catch(Exception e)
        {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account inAccount)
    {
        try{
            return ResponseEntity.status(200).body(accountService.loginAccount(inAccount));
        }
        catch(Exception e)
        {
            return ResponseEntity.status(401).body(null);
        }
        
    }

}
