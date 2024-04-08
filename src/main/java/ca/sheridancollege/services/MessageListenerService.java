package ca.sheridancollege.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.sheridancollege.maynajal.beans.Message;
import ca.sheridancollege.maynajal.repository.MessageRepository;

@Service
public class MessageListenerService 

{

	@Autowired
    private MessageRepository messageRepository;

    @KafkaListener(topics = "chat_topic", groupId = "chat_group")
    public void listen(String messageJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(messageJson, Message.class);
        Message savedMessage = messageRepository.save(message);
        savedMessage.getRecipient();
    }
	
}
