package is.hi.hbv501g.sundbok.controller;

import is.hi.hbv501g.sundbok.model.CheckIn;
import is.hi.hbv501g.sundbok.service.CheckInService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckInController.class)
@Import(CheckInControllerWebTest.TestConfig.class)
public class CheckInControllerWebTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CheckInService checkInService() {
            return Mockito.mock(CheckInService.class);
        }
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CheckInService checkInService; // injected mock from TestConfig


    @Test
    void getAllCheckIns_returnsOk() throws Exception {
        Mockito.when(checkInService.getAllCheckIns()).thenReturn(List.of());
        mvc.perform(get("/api/checkins").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getCheckInById_found() throws Exception {
        CheckIn ci = new CheckIn();
        ci.setId(1L);
        ci.setVisitedAt(LocalDateTime.now());
        Mockito.when(checkInService.getCheckInById(1L)).thenReturn(Optional.of(ci));

        mvc.perform(get("/api/checkins/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getCheckInById_notFound() throws Exception {
        Mockito.when(checkInService.getCheckInById(999L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/checkins/999").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void createCheckIn_returnsCreated() throws Exception {
        CheckIn created = new CheckIn();
        created.setId(5L);
        created.setVisitedAt(LocalDateTime.now());
        Mockito.when(checkInService.checkIn(1L, 1L)).thenReturn(created);

        mvc.perform(post("/api/checkins")
                .param("userId", "1")
                .param("facilityId", "1"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void createCheckIn_badRequest_whenServiceThrows() throws Exception {
        Mockito.when(checkInService.checkIn(anyLong(), anyLong())).thenThrow(new RuntimeException("User not found"));
        mvc.perform(post("/api/checkins")
                .param("userId", "999")
                .param("facilityId", "1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getUserCheckIns_returnsOk() throws Exception {
        Mockito.when(checkInService.getUserCheckIns(1L)).thenReturn(List.of());
        mvc.perform(get("/api/checkins/user/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getFacilityCheckIns_returnsOk() throws Exception {
        Mockito.when(checkInService.getFacilityCheckIns(1L)).thenReturn(List.of());
        mvc.perform(get("/api/checkins/facility/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void deleteCheckIn_returnsNoContent() throws Exception {
        Mockito.doNothing().when(checkInService).deleteCheckIn(1L);
        mvc.perform(delete("/api/checkins/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void visited_returnsBoolean() throws Exception {
        Mockito.when(checkInService.hasUserVisited(1L, 1L)).thenReturn(true);
        mvc.perform(get("/api/checkins/visited")
                .param("userId", "1")
                .param("facilityId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }
}