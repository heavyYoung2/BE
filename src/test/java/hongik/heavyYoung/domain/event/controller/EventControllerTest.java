package hongik.heavyYoung.domain.event.controller;

import hongik.heavyYoung.domain.event.config.EventControllerTestConfig;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.EventQueryService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EventController.class)
@Import(EventControllerTestConfig.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventQueryService eventQueryService;

    @Test
    @DisplayName("공지사항 전체 조회 성공")
    void getEvents_success() throws Exception {
        // given
        EventResponse.EventInfoDTO eventInfoDTO = EventResponse.EventInfoDTO.builder()
                .eventId(1L)
                .title("간식행사")
                .eventCreatedAt(LocalDate.of(2025, 8, 31).atStartOfDay())
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();
        given(eventQueryService.getAllEvents(null, null)).willReturn(List.of(eventInfoDTO));

        // when & then
        mockMvc.perform(get("/events")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result[0].eventId").value(1L))
                .andExpect(jsonPath("$.result[0].title").value("간식행사"))
                .andExpect(jsonPath("$.result[0].eventCreatedAt").value("2025-08-31T00:00:00"))
                .andExpect(jsonPath("$.result[0].eventStartAt").value("2025-09-01"))
                .andExpect(jsonPath("$.result[0].eventEndAt").value("2025-09-02"));
    }

    @Test
    @DisplayName("시작일(from)보다 종료일(to)이 앞선 경우")
    void getEvents_invalidDateRange() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/events")
                        .param("from", "2025-09-05")
                        .param("to", "2025-09-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_DATE_RANGE.getCode()))
                .andExpect(jsonPath("$.message").value("시작일은 종료일보다 이후일 수 없습니다."));
    }

    @Test
    @DisplayName("파라미터값이 잘못 들어온 경우 - 시작일(from), 종료일(to) 둘 중 한 가지 값만 들어온 경우")
    void getEvents_wrongParameter1() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/events")
                        .param("from", "2025-09-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()))
                .andExpect(jsonPath("$.message").value("잘못된 요청 파라미터입니다."));
    }

    @Test
    @DisplayName("파라미터값이 잘못 들어온 경우 - 시작일(from), 종료일(to)의 날짜 형식(yyyy-MM-dd)이 맞지 않은 경우")
    void getEvents_wrongParameter2() throws Exception {
        // given

        // when & then
        mockMvc.perform(get("/events")
                        .param("from", "2025/09/01")
                        .param("to", "2025/09/02")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()))
                .andExpect(jsonPath("$.message").value("잘못된 요청 파라미터입니다."));
    }
}