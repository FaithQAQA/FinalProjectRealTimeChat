package ca.sheridancollege.maynajal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	  	 


	 
	
	  	
	  	  @PostMapping("/create")
	      public ResponseEntity<Message> createMessage(@RequestBody CreateMessageRequest request) {
	          try {
	              Users sender = usersRepository.findById(request.getSenderId())
	                      .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

	              Users recipient = usersRepository.findById(request.getRecipientId())
	                      .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

	              Room room = roomRepository.findById(request.getRoomId())
	                      .orElseThrow(() -> new IllegalArgumentException("Room not found"));

	              Message message = new Message();
	              message.setContent(request.getContent());
	              message.setSender(sender);
	              message.setRecipient(recipient);
	              message.setRoom(room);
	              message.setSenderUsername(sender.getUsername());
	              message.setRecipientUsername(recipient.getUsername());

	              Message savedMessage = messageRepository.save(message);

	          


	              
	              return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
	          } catch (Exception e) {
	        
	              return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	          }
	          
	          
	      
	  	}

	  
}
