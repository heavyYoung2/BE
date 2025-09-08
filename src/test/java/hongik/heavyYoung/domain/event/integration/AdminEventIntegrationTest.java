package hongik.heavyYoung.domain.event.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminEventIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 생성 성공")
    void addEvent_Success() throws Exception{
        // given
        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        // when
        ResultActions result = mockMvc.perform(post("/admin/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.eventId").isNumber());

        // then
        Event savedEvent = eventRepository.findAll().getFirst();
        result.andExpect(jsonPath("$.result.eventId").value(savedEvent.getId()));

        assertEquals("간식행사", savedEvent.getEventTitle());
        assertEquals("간식행사 세부 일정", savedEvent.getEventContent());
        assertEquals(LocalDate.of(2025, 9, 1), savedEvent.getEventStartDate());
        assertEquals(LocalDate.of(2025, 9, 2), savedEvent.getEventEndDate());
    }
}
