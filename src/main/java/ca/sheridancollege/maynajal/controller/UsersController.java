package ca.sheridancollege.maynajal.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Session;
import ca.sheridancollege.maynajal.beans.Users;
import ca.sheridancollege.maynajal.repository.RoomRepository;
import ca.sheridancollege.maynajal.repository.SessionsRepository;
import ca.sheridancollege.maynajal.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class UsersController {

	@Autowired
    private UserRepository usersRepository;
	@Autowired
	private SessionsRepository	sessionRepository;

    @Autowired
    private RoomRepository roomRepository;
	
	 	@GetMapping(value = {"", "/"})
	    public List<Users> getAllUsers() {
	        return usersRepository.findAll();
	    }
	 	
	 	
	 	
	
	 	@GetMapping("/user/{sessionId}")
	 	public ResponseEntity<Users> getUserBySessionId(@PathVariable Long sessionId) {
	 	    Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
	 	    if (sessionOptional.isPresent()) {
	 	        Session session = sessionOptional.get();
	 	        Users user = session.getUser(); 
	 	        return ResponseEntity.ok(user);
	 	    } else {
	 	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	 	    }
	 	}

	 	  @GetMapping("/api/user/rooms/test")
	 	    public ResponseEntity<List<Room>> getRoomsForCurrentUser(@RequestParam Long sessionId) {
	 	        Users user = usersRepository.findBySessions_Id(sessionId);

	 	        if (user != null) {
	 	            List<Room> userRooms = roomRepository.findByUsersContaining(user);
	 	            return ResponseEntity.ok(userRooms);
	 	        } else {
	 	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	 	        }
	 	    }
	  
	 	@PutMapping("/hello/{username}")
	 	public ResponseEntity<String> loginUser(@PathVariable String username) {
	 	    List<Users> allUsers = usersRepository.findAll();
	 	    for (Users user : allUsers) {
	 	        if (user.getUsername().equals(username.trim())) {
	 	            user.setLoggedIn(true);
	 	            usersRepository.save(user);

	 	            Session session = new Session();
	 	            session.setUser(user); 
	 	            session.setExpirationTime(LocalDateTime.now().plusHours(1));
	 	            sessionRepository.save(session);

	 	            String sessionId = session.getId().toString(); 
	 	            return ResponseEntity.ok().body("{\"message\": \"User " + username + " logged in successfully\", \"sessionId\": \"" + sessionId + "\"}");
	 	        }
	 	    }
	 	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"User " + username + " not found\"}");
	 	}


	 	@GetMapping("/validateSession/{sessionId}")
	 	public ResponseEntity<String> validateSession(@PathVariable Long sessionId) {
	 	    Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
	 	    if (sessionOptional.isPresent() && sessionOptional.get().getExpirationTime().isAfter(LocalDateTime.now())) {
	 	        return ResponseEntity.ok("Session valid");
	 	    } else {
	 	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired");
	 	    }
	 	}

	 	 @GetMapping("/getRecipientId")
	     public Long getRecipientId(@RequestParam String recipientUsername) {
	         List<Users> users = usersRepository.findAll();
	         for (Users user : users) {
	             if (user.getUsername().equals(recipientUsername)) {
	                 return user.getId();
	             }
	         }
	         return null; 
	     }
	   
	    @PostMapping(consumes = "application/json")
	    public ResponseEntity<Object> postUser(@RequestBody Users student) {
	        try {
	            Users savedStudent = usersRepository.save(student);
	            return ResponseEntity.ok(savedStudent);
	        } catch (DataIntegrityViolationException e) {
	            String errorMessage = "Username With " +student.getUsername() + " Exist please pick another name";
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	        }
	    }
	 
	    @GetMapping("/{usernamePart}")
	    public List<Users> searchUsersByUsernameContaining( @PathVariable("usernamePart") String usernamePart) 
	    {
			System.out.println(usernamePart);
			System.out.println(usersRepository.findByUsernameContaining(usernamePart));
	        return usersRepository.findByUsernameContaining(usernamePart);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users userDetails) {
	        Users user = usersRepository.findById(id).orElse(null);
	        if (user != null) {
	            user.setUsername(userDetails.getUsername());
	            user.setPassword(userDetails.getPassword());
	            Users updatedUser = usersRepository.save(user);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	        Users user = usersRepository.findById(id).orElse(null);
	        if (user != null) {
	            usersRepository.deleteById(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
}
