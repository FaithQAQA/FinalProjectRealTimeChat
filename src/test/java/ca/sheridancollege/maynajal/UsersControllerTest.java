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
        List<Users> usersList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(usersList);

        List<Users> result = usersController.getAllUsers();

        assertEquals(usersList, result);
    }

    @Test
    public void testGetUserBySessionId() {
        // Prepare test data
        Long sessionId = 1L;
        Session session = new Session();
        Users user = new Users();
        session.setUser(user);

        when(sessionsRepository.findById(sessionId)).thenReturn(Optional.of(session));

        ResponseEntity<Users> responseEntity = usersController.getUserBySessionId(sessionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testGetRoomsForCurrentUser() {
        Long sessionId = 1L;
        Users user = new Users();
        user.setId(1L);
        List<Room> rooms = new ArrayList<>();

        when(userRepository.findBySessions_Id(sessionId)).thenReturn(user);
        when(roomRepository.findByUsersContaining(user)).thenReturn(rooms);

        ResponseEntity<List<Room>> responseEntity = usersController.getRoomsForCurrentUser(sessionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rooms, responseEntity.getBody());
    }

 
    } 

