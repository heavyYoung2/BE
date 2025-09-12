package hongik.heavyYoung.domain.event.integration.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private Long savedEventId;

    @BeforeEach
    void setUp(){
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        EventImage eventImage1 = EventImage.builder()
                .eventImageUrl("url1")
                .sortOrder(1)
                .build();

        EventImage eventImage2 = EventImage.builder()
                .eventImageUrl("url2")
                .sortOrder(2)
                .build();

        event1.addEventImage(eventImage1);
        event1.addEventImage(eventImage2);

        event1 = eventRepository.save(event1);
        savedEventId = event1.getId();

        Event event2 = Event.builder()
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 10, 1))
                .eventEndDate(LocalDate.of(2025, 10, 2))
                .build();

        Event event3 = Event.builder()
                .eventTitle("운동행사")
                .eventContent("운동행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 4))
                .eventEndDate(LocalDate.of(2025, 9, 5))
                .build();

        eventRepository.save(event2);
        eventRepository.save(event3);
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 생성 성공")
    void addEvent_Success() throws Exception{
        // given
        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("특별행사")
                .content("특별행사 세부 일정")
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
        String responseBody = result.andReturn().getResponse().getContentAsString();
        Long savedEventId = objectMapper.readTree(responseBody)
                .path("result")
                .path("eventId")
                .asLong();

        Event savedEvent = eventRepository.findById(savedEventId).orElseThrow();

        assertEquals("특별행사", savedEvent.getEventTitle());
        assertEquals("특별행사 세부 일정", savedEvent.getEventContent());
        assertEquals(LocalDate.of(2025, 9, 1), savedEvent.getEventStartDate());
        assertEquals(LocalDate.of(2025, 9, 2), savedEvent.getEventEndDate());
    }

    @Test
    @DisplayName("공지사항 삭제 성공")
    void deleteEvent_success() throws Exception {
        // given
        Long eventId = savedEventId;

        // when
        ResultActions result = mockMvc.perform(delete("/admin/event/{eventId}", eventId));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result").doesNotExist());

        // then
        assertThat(eventRepository.findById(eventId)).isEmpty();
    }
}
