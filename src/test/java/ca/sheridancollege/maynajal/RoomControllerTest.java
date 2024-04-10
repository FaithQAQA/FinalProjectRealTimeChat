package ca.sheridancollege.maynajal;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import ca.sheridancollege.maynajal.beans.Room;
import ca.sheridancollege.maynajal.repository.RoomRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {
    

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllRooms() throws Exception {
        Room room1 = new Room();
        room1.setName("Room 1");
        Room room2 = new Room();
        room2.setName("Room 2");
        List<Room> rooms = Arrays.asList(room1, room2);
        roomRepository.saveAll(rooms);

        mockMvc.perform(get("/api/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Room 1"))
                .andExpect(jsonPath("$[1].name").value("Room 2"));
    }

    @Test
    public void testCreateRoom() throws Exception {
        Room room = new Room();
        room.setName("New Room");

        mockMvc.perform(post("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"New Room\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Room"));
    }
}
