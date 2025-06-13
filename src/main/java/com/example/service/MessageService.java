package com.example.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;



@Service
public class MessageService {
    private final MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository=messageRepository;
    }
    public Message getMessageById(Integer id)
    {
        Optional<Message> messageBox=messageRepository.findById(id);
        if(messageBox.isEmpty())
        {
            return null;
        }
        return messageBox.get();
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    public Integer deleteMessage(Integer messageId) {
        
        
        if(getMessageById(messageId)==null)
        {
            return null;
        }
        messageRepository.deleteById(messageId);
        return 1;

    }
    public void updateMessage(Message message, Integer messageId) throws Exception {
        if(getMessageById(messageId)==null|| !validMessage(message))
        {
            throw new Exception("message Id not found");
        }
        message.setMessageId(messageId);
        messageRepository.save(message);
    }
    public List<Message> getMessagesByAuthor(Integer accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }

    private boolean validMessage(Message inMessage)
    {
        String messageText=inMessage.getMessageText();
        return messageText.trim()!="" && messageText.length()<256;
    }
    public Message postMessage(Message inMessage) throws Exception
    {
        if (inMessage.getMessageId()!=null || !validMessage(inMessage))
        {
            throw new Exception("invalid message attempted to be posted");
        }
        return messageRepository.save(inMessage);
    }
}
