package hongik.heavyYoung.domain.event.controller;

import hongik.heavyYoung.domain.event.config.EventRestControllerTestConfig;
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
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EventRestController.class)
@Import(EventRestControllerTestConfig.class)
class EventRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventQueryService eventQueryService;

    @Test
    @DisplayName("공지사항 조회 성공")
    void getEvents_success() throws Exception {
        // given
        EventResponse.EventInfoDTO eventInfoDTO = EventResponse.EventInfoDTO.builder()
                .eventId(1L)
                .title("간식행사")
                .eventCreatedAt(LocalDate.of(2025, 8, 31).atStartOfDay())
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();
        given(eventQueryService.findEvents(null, null)).willReturn(List.of(eventInfoDTO));

        // when
        ResultActions result = mockMvc.perform(get("/events")
                        .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result[0].eventId").value(1L))
                .andExpect(jsonPath("$.result[0].title").value("간식행사"))
                .andExpect(jsonPath("$.result[0].eventCreatedAt").value("2025-08-31 00:00:00"))
                .andExpect(jsonPath("$.result[0].eventStartDate").value("2025-09-01"))
                .andExpect(jsonPath("$.result[0].eventEndDate").value("2025-09-02"));
    }

    @Test
    @DisplayName("공지사항 조회시 시작일(from)보다 종료일(to)이 앞선 경우")
    void getEvents_invalidDateRange() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/events")
                        .param("from", "2025-09-05")
                        .param("to", "2025-09-01")
                        .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_DATE_RANGE.getCode()))
                .andExpect(jsonPath("$.message").value("시작일은 종료일보다 이후일 수 없습니다."));
    }

    @Test
    @DisplayName("공지사항 조회시 파라미터값이 잘못 들어온 경우 - 시작일(from), 종료일(to) 둘 중 한 가지 값만 들어온 경우")
    void getEvents_wrongParameter1() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/events")
                        .param("from", "2025-09-01")
                        .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()))
                .andExpect(jsonPath("$.message").value("잘못된 요청 파라미터입니다."));
    }

    @Test
    @DisplayName("공지사항 조회시 파라미터값이 잘못 들어온 경우 - 시작일(from), 종료일(to)의 날짜 형식(yyyy-MM-dd)이 맞지 않은 경우")
    void getEvents_wrongParameter2() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/events")
                        .param("from", "2025/09/01")
                        .param("to", "2025/09/02")
                        .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()))
                .andExpect(jsonPath("$.message").value("잘못된 요청 파라미터입니다."));
    }

    @Test
    @DisplayName("공지사항 상세 조회(사진포함) 성공")
    void getEventDetails() throws Exception {
        // given
        EventResponse.EventInfoDetailDTO eventInfoDetailDTO = EventResponse.EventInfoDetailDTO.builder()
                .eventId(1L)
                .title("간식행사")
                .content("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .eventCreatedAt(LocalDate.of(2025, 8, 31).atStartOfDay())
                .imageUrls(List.of("url1", "url2"))
                .build();

        given(eventQueryService.findEventDetails(1L)).willReturn(eventInfoDetailDTO);

        // when
        ResultActions result = mockMvc.perform(get("/events/{eventId}", 1L)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.result.eventId").value(1))
                .andExpect(jsonPath("$.result.title").value("간식행사"))
                .andExpect(jsonPath("$.result.content").value("간식행사 상세 일정"))
                .andExpect(jsonPath("$.result.eventCreatedAt").value("2025-08-31 00:00:00"))
                .andExpect(jsonPath("$.result.eventStartDate").value("2025-09-01"))
                .andExpect(jsonPath("$.result.eventEndDate").value("2025-09-02"))
                .andExpect(jsonPath("$.result.imageUrls", hasSize(2)))
                .andExpect(jsonPath("$.result.imageUrls[0]").value("url1"))
                .andExpect(jsonPath("$.result.imageUrls[1]").value("url2"));
    }

    @Test
    @DisplayName("공지사항 상세 조회(사진포함)시 PathVariable 형식 오류인 경우")
    void getEventDetails_WrongPathVariable() throws Exception {
        // given
        EventResponse.EventInfoDetailDTO eventInfoDetailDTO = EventResponse.EventInfoDetailDTO.builder()
                .eventId(1L)
                .title("간식행사")
                .content("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .eventCreatedAt(LocalDate.of(2025, 8, 31).atStartOfDay())
                .imageUrls(List.of("url1", "url2"))
                .build();

        given(eventQueryService.findEventDetails(1L)).willReturn(eventInfoDetailDTO);

        // when
        ResultActions result = mockMvc.perform(get("/events/{eventId}", "HI")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()));
    }
}