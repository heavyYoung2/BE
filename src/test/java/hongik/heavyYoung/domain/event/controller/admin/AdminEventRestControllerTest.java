package hongik.heavyYoung.domain.event.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.domain.event.config.AdminEventRestControllerTestConfig;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminEventRestController.class)
@Import(AdminEventRestControllerTestConfig.class)
class AdminEventRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdminEventCommandService adminEventCommandService;

    @BeforeEach
    void resetMocks() {
        reset(adminEventCommandService);
    }

    @Test
    @DisplayName("공지사항 생성 성공")
    void addEvent() throws Exception {
        // given
        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        EventResponse.EventAddResponseDTO eventAddResponseDTO =
                EventResponse.EventAddResponseDTO.builder()
                        .eventId(1L)
                        .build();

        given(adminEventCommandService.createEvent(any(EventRequest.EventAddRequestDTO.class)))
                .willReturn(eventAddResponseDTO);

        // when
        ResultActions result = mockMvc.perform(post("/admin/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.eventId").value(1));
    }


    @Test
    @DisplayName("공지사항 생성 실패 - DTO Valid 검증 실패(제목 누락)")
    void addEvent_notValidException_noTitle() throws Exception {
        // given
        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        // when
        ResultActions result = mockMvc.perform(post("/admin/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$.result[0]").value("title: 제목은 필수 입력 값입니다."));

        verifyNoInteractions(adminEventCommandService);
    }

    @Test
    @DisplayName("공지사항 생성 실패 - JSON Parsing 검증 실패")
    void addEvent_httpMessageNotReadable() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(post("/admin/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
            {
              "title": "간식행사",
              "content": "간식행사 세부 일정",
              "eventStartDate": "2025-19-01",
              "eventEndDate": "2025-09-02"
            }
        """));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()));

        verifyNoInteractions(adminEventCommandService);
    }

    @Test
    @DisplayName("공지사항 생성 실패 - 시작일(startDate)보다 종료일(endDate)이 앞선 경우")
    void addEvent_invalidDateRange() throws Exception{
        // given
        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 2))
                .eventEndDate(LocalDate.of(2025, 9, 1))
                .build();

        // when
        ResultActions result = mockMvc.perform(post("/admin/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("isSuccess").value(false))
                .andExpect(jsonPath("code").value(ErrorStatus.INVALID_DATE_RANGE.getCode()));

        verifyNoInteractions(adminEventCommandService);
    }

    @Test
    @DisplayName("공지사항 생성 실패 - 시작일(startDate), 종료일(endDate)중 한 가지 값만 들어온 경우")
    void addEvent_onlyOneDate() throws Exception{
        // given
        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 2))
                .build();

        // when
        ResultActions result = mockMvc.perform(post("/admin/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("isSuccess").value(false))
                .andExpect(jsonPath("code").value(ErrorStatus.INVALID_PARAMETER.getCode()));

        verifyNoInteractions(adminEventCommandService);
    }

}