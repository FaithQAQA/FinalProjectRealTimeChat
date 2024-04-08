package ca.sheridancollege.maynajal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.sheridancollege.maynajal.beans.CreateMessageRequest;
import ca.sheridancollege.maynajal.beans.Message;
import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Users;
import ca.sheridancollege.maynajal.repository.MessageRepository;
import ca.sheridancollege.maynajal.repository.RoomRepository;
import ca.sheridancollege.maynajal.repository.UserRepository;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})

public class MessagesController 
{

	  	@Autowired
	    private MessageRepository messageRepository;
	  	
	  	@Autowired
	  	private RoomRepository roomRepository;
	  	
	  	 @Autowired
	     private UserRepository usersRepository;
	  	 
	  	@Autowired
	  	private KafkaTemplate<String, String> kafkaTemplate;

	 
	
	  	
	  	  @PostMapping("/create")
	      public ResponseEntity<Message> createMessage(@RequestBody CreateMessageRequest request) {
	          try {
	              // Retrieve sender and recipient from repository
	              Users sender = usersRepository.findById(request.getSenderId())
	                      .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

	              Users recipient = usersRepository.findById(request.getRecipientId())
	                      .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

	              // Retrieve room from repository based on the provided room ID
	              Room room = roomRepository.findById(request.getRoomId())
	                      .orElseThrow(() -> new IllegalArgumentException("Room not found"));

	              // Create a new message with provided content, sender, recipient, and room
	              Message message = new Message();
	              message.setContent(request.getContent());
	              message.setSender(sender);
	              message.setRecipient(recipient);
	              message.setRoom(room);
	              message.setSenderUsername(sender.getUsername());
	              message.setRecipientUsername(recipient.getUsername());

	              Message savedMessage = messageRepository.save(message);

	              ObjectMapper objectMapper = new ObjectMapper();
	              String messageJson;
	              try {
	                  messageJson = objectMapper.writeValueAsString(savedMessage);
	              } catch (JsonProcessingException e) {
	                  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	              }

	              // Send the message to a Kafka topic
	              kafkaTemplate.send("chat_topic", messageJson);
	              sendMessage("chat_topic",messageJson);

	              
	              return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
	          } catch (Exception e) {
	              // Log the exception and return a more informative error message
	              //log.error("Error creating message", e);
	              return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	          }
	          
	          
	      
	  	}

	  	public void sendMessage(String topic, String message) {
	  	    kafkaTemplate.send(topic, message);
	  	}
}
