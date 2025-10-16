package is.hi.hbv501g.sundbok.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import is.hi.hbv501g.sundbok.service.UserService;
import is.hi.hbv501g.sundbok.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerWebTest.TestConfig.class)
public class UserControllerWebTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService; // now injected mock

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllUsers_returnsOk() throws Exception {
        User u1 = new User("Alice", "pass", "alice@example.com", false);
        User u2 = new User("Bob", "pass", "bob@example.com", false);
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(u1, u2));

        mvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUserById_found() throws Exception {
        User u = new User("Alice", "pass", "alice@example.com", false);
        u.setId(1L);
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(u));

        mvc.perform(get("/api/users/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alice"))
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getUserById_notFound() throws Exception {
        Mockito.when(userService.getUserById(999L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/users/999").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void searchByName_found() throws Exception {
        User u = new User("Alice", "pass", "alice@example.com", false);
        Mockito.when(userService.getUserByName("Alice")).thenReturn(Optional.of(u));

        mvc.perform(get("/api/users/search").param("name", "Alice").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void createUser_returnsCreated() throws Exception {
        User input = new User("Charlie", "charpass", "charlie@example.com", false);
        User saved = new User("Charlie", "charpass", "charlie@example.com", false);
        saved.setId(10L);
        Mockito.when(userService.createUser(any(User.class))).thenReturn(saved);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(10))
            .andExpect(jsonPath("$.name").value("Charlie"));
    }

    @Test
    void updateUser_returnsOk() throws Exception {
        User update = new User("AliceUpdated", "newpass", "alice@example.com", false);
        User updated = new User("AliceUpdated", "newpass", "alice@example.com", false);
        updated.setId(1L);
        Mockito.when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updated);

        mvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("AliceUpdated"))
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteUser_returnsNoContent() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void getUserCount_and_exists() throws Exception {
        Mockito.when(userService.getUserCount()).thenReturn(6L);
        Mockito.when(userService.userExists(1L)).thenReturn(true);

        mvc.perform(get("/api/users/count"))
            .andExpect(status().isOk())
            .andExpect(content().string("6"));

        mvc.perform(get("/api/users/exists/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}