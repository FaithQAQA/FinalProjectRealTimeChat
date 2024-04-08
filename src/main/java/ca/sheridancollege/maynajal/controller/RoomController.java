package ca.sheridancollege.maynajal.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Session;
import ca.sheridancollege.maynajal.beans.Users;
import ca.sheridancollege.maynajal.repository.RoomRepository;
import ca.sheridancollege.maynajal.repository.SessionsRepository;
import ca.sheridancollege.maynajal.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {

	  	@Autowired
	    private RoomRepository roomRepository;

	  	@Autowired
	    private RoomService roomService;
	  	
	  	@Autowired
	  	private UsersController userController;
	  	
	  	 @Autowired
	     private SessionsRepository sessionRepository;
	  	
		@GetMapping("/all")
	    public List<Room> getAllRooms() {
	        return roomRepository.findAll();
	    }
	 	
	

		 @GetMapping("/refresh")
		    public List<Room> refreshRooms(@RequestHeader("Authorization") String sessionId) {
		        // Convert the session ID from String to Long
			 System.out.println(sessionId+ " String version");
		        Long sessionIdLong = Long.parseLong(sessionId);

				 System.out.println(sessionIdLong+ " Long  version");

		        // Retrieve the user associated with the session ID
		        Optional<Session> sessionOptional = sessionRepository.findById(sessionIdLong);
		        if (sessionOptional.isPresent()) {
		            Users loggedInUser = sessionOptional.get().getUser();

		            // Get all rooms
		            List<Room> allRooms = roomRepository.findAll();

		            // Filter rooms based on whether the logged-in user is a part of each room
		            return allRooms.stream()
		                    .filter(room -> room.getUsers().contains(loggedInUser))
		                    .collect(Collectors.toList());
		        } else {
		            throw new IllegalArgumentException("Invalid session ID");
		        }


		 }
	  	
	    @PostMapping
	    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
	        Room savedRoom = roomRepository.save(room);
	        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
	    }
	    
	 
	    @PostMapping("/{roomId}/users/{userId1}/{userId2}")
	    public ResponseEntity<Room> addUserToRoom(@PathVariable Long roomId, @PathVariable Long userId1, @PathVariable Long userId2) {
	        System.out.println(roomId);
	        System.out.println(userId1);
	        System.out.println(userId2);

	        Room updatedRoom = roomService.addUserToRoom(roomId, userId1, userId2);
	        System.out.println(updatedRoom.getUsers());
	        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
	    }

	    
}
