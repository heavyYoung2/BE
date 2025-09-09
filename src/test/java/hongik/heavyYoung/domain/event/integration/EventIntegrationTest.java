package hongik.heavyYoung.domain.event.integration;

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

import static org.hamcrest.Matchers.hasSize;
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
                .event(event1)
                .eventImageUrl("url1")
                .sortOrder(1)
                .build();

        EventImage eventImage2 = EventImage.builder()
                .event(event1)
                .eventImageUrl("url2")
                .sortOrder(2)
                .build();

        event1.addEventImage(eventImage1);
        event1.addEventImage(eventImage2);

        event1 = eventRepository.save(event1); // 저장 후 id 세팅됨
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
    @DisplayName("공지사항 조회(전체) 통합테스트")
    void getEvents_All_API() throws Exception{
        // given

        // when
        ResultActions result = mockMvc.perform(get("/events")
                    .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.length()").value(3))
                .andExpect(jsonPath("$.result[0].title").value("운동행사"))
                .andExpect(jsonPath("$.result[0].eventStartDate").value("2025-09-04"))
                .andExpect(jsonPath("$.result[0].eventEndDate").value("2025-09-05"))
                .andExpect(jsonPath("$.result[1].title").value("나눔행사"))
                .andExpect(jsonPath("$.result[1].eventStartDate").value("2025-10-01"))
                .andExpect(jsonPath("$.result[1].eventEndDate").value("2025-10-02"))
                .andExpect(jsonPath("$.result[2].title").value("간식행사"))
                .andExpect(jsonPath("$.result[2].eventStartDate").value("2025-09-01"))
                .andExpect(jsonPath("$.result[2].eventEndDate").value("2025-09-02"));

    }

    @Test
    @DisplayName("공지사항 조회(기간별) 통합테스트")
    void getEvents_Period_API() throws Exception{
        // given

        // when
        ResultActions result = mockMvc.perform(get("/events")
                .param("from", "2025-09-01")
                .param("to", "2025-09-30")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.length()").value(2))
                .andExpect(jsonPath("$.result[0].title").value("운동행사"))
                .andExpect(jsonPath("$.result[0].eventStartDate").value("2025-09-04"))
                .andExpect(jsonPath("$.result[0].eventEndDate").value("2025-09-05"))
                .andExpect(jsonPath("$.result[1].title").value("간식행사"))
                .andExpect(jsonPath("$.result[1].eventStartDate").value("2025-09-01"))
                .andExpect(jsonPath("$.result[1].eventEndDate").value("2025-09-02"));
    }

    @Test
    @DisplayName("공지사항 상세 조회(사진포함) 테스트")
    void getEventDetails() throws Exception{
        // given

        // when
        ResultActions result = mockMvc.perform(get("/events/{eventId}", savedEventId)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.title").value("간식행사"))
                .andExpect(jsonPath("$.result.content").value("간식행사 상세 일정"))
                .andExpect(jsonPath("$.result.eventStartDate").value("2025-09-01"))
                .andExpect(jsonPath("$.result.eventEndDate").value("2025-09-02"))
                .andExpect(jsonPath("$.result.imageUrls", hasSize(2)))
                .andExpect(jsonPath("$.result.imageUrls[0]").value("url1"))
                .andExpect(jsonPath("$.result.imageUrls[1]").value("url2"));
    }

}
