package hongik.heavyYoung.domain.event.integration;

import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp(){
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 10, 1))
                .eventEndAt(LocalDate.of(2025, 10, 2))
                .build();

        eventRepository.save(event1);
        eventRepository.save(event2);
    }

    @Test
    @DisplayName("공지사항 조회(전체) 통합테스트")
    void getEvents_All_API() throws Exception{
        mockMvc.perform(get("/events")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.length()").value(2))
                .andExpect(jsonPath("$.result[0].title").value("간식행사"))
                .andExpect(jsonPath("$.result[0].eventStartAt").value("2025-09-01"))
                .andExpect(jsonPath("$.result[0].eventEndAt").value("2025-09-02"))
                .andExpect(jsonPath("$.result[1].title").value("나눔행사"))
                .andExpect(jsonPath("$.result[1].eventStartAt").value("2025-10-01"))
                .andExpect(jsonPath("$.result[1].eventEndAt").value("2025-10-02"));

    }

    @Test
    @DisplayName("공지사항 조회(기간별) 통합테스트")
    void getEvents_Period_API() throws Exception{
        mockMvc.perform(get("/events")
                        .param("from", "2025-09-01")
                        .param("to", "2025-09-30")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.length()").value(1))
                .andExpect(jsonPath("$.result[0].title").value("간식행사"))
                .andExpect(jsonPath("$.result[0].eventStartAt").value("2025-09-01"))
                .andExpect(jsonPath("$.result[0].eventEndAt").value("2025-09-02"));
    }

}
