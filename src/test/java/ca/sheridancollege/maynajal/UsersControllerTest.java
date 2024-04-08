package ca.sheridancollege.maynajal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.beans.Session;
import ca.sheridancollege.maynajal.beans.Users;
import ca.sheridancollege.maynajal.controller.UsersController;
import ca.sheridancollege.maynajal.repository.RoomRepository;
import ca.sheridancollege.maynajal.repository.SessionsRepository;
import ca.sheridancollege.maynajal.repository.UserRepository;

@SpringBootTest
public class UsersControllerTest 
{

     @InjectMocks
    UsersController usersController;

    @Mock
    UserRepository userRepository;

    @Mock
    SessionsRepository sessionsRepository;

    @Mock
    RoomRepository roomRepository;

    @Test
    public void testGetAllUsers() {
             // Create a list of users for testing
        List<Users> usersList = new ArrayList<>();
        // Add some sample users to the list

        // Mock the userRepository.findAll() method to return the list of users
        when(userRepository.findAll()).thenReturn(usersList);

        // Call the getUsers() method
        List<Users> result = usersController.getAllUsers();

        // Assert that the returned list is equal to the list we provided
        assertEquals(usersList, result);
    }

    @Test
    public void testGetUserBySessionId() {
        // Prepare test data
        Long sessionId = 1L;
        Session session = new Session();
        Users user = new Users();
        session.setUser(user);

        // Mock the sessionRepository.findById() method
        when(sessionsRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Call the getUserBySessionId() method
        ResponseEntity<Users> responseEntity = usersController.getUserBySessionId(sessionId);

        // Assert that the response is OK and contains the correct user
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetRoomsForCurrentUser() {
        // Prepare test data
        Long sessionId = 1L;
        Users user = new Users();
        user.setId(1L);
        List<Room> rooms = new ArrayList<>();
        // Add some sample rooms to the list

        // Mock the userRepository.findBySessions_Id() method
        when(userRepository.findBySessions_Id(sessionId)).thenReturn(user);
        // Mock the roomRepository.findByUsersContaining() method
        when(roomRepository.findByUsersContaining(user)).thenReturn(rooms);

        // Call the getRoomsForCurrentUser() method
        ResponseEntity<List<Room>> responseEntity = usersController.getRoomsForCurrentUser(sessionId);

        // Assert that the response is OK and contains the correct rooms
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rooms, responseEntity.getBody());
    }

    @Test
    public void testLoginUser() {
        // Prepare test data
        String username = "testUser";
        List<Users> allUsers = new ArrayList<>();
        Users user = new Users();
        user.setUsername(username);
        allUsers.add(user);

        // Mock the userRepository.findAll() method
        when(userRepository.findAll()).thenReturn(allUsers);

        // Mock the Session object with a non-null ID
        Session session = new Session();
        session.setId(1L); // Set a non-null ID
        when(sessionsRepository.save(any(Session.class))).thenReturn(session);

        // Call the loginUser() method
        ResponseEntity<String> responseEntity = usersController.loginUser(username);

        // Assert that the user is logged in and a session is created
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User " + username + " logged in successfully", responseEntity.getBody());
    }
    } 

