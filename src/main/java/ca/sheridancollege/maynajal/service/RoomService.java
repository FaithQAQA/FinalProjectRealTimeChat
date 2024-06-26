package ca.sheridancollege.maynajal.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Session;
import ca.sheridancollege.maynajal.beans.Users;
import ca.sheridancollege.maynajal.repository.RoomRepository;
import ca.sheridancollege.maynajal.repository.SessionsRepository;
import ca.sheridancollege.maynajal.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class RoomService {

	
	
	
	
	 	@Autowired
	    private RoomRepository roomRepository;

	    @Autowired
	    private UserRepository usersRepository;

	    @Autowired
	    private SessionsRepository sessionRepository;

	    @Transactional
	    public Room addUserToRoom(Long roomId, Long userId1, Long userId2) {
	        
	        Users user1 = usersRepository.findById(userId1)
	                .orElseThrow(() -> new IllegalArgumentException("User 1 not found"));
	        
	        Users user2 = usersRepository.findById(userId2)
	                .orElseThrow(() -> new IllegalArgumentException("User 2 not found"));

	        System.out.println(user2+ "testing user2 if its being found in the database");
	        Room room = roomRepository.findById(roomId)
	                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

	        room.getUsers().add(user1);
	        room.getUsers().add(user2);

	        return roomRepository.save(room);
	    }

	
	
}
