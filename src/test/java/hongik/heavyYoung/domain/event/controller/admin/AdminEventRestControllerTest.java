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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        Long eventId = 1L;

        EventRequest.EventAddRequestDTO request = EventRequest.EventAddRequestDTO.builder()
                .title("간식행사")
                .content("간식행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        EventResponse.EventAddResponseDTO eventAddResponseDTO =
                EventResponse.EventAddResponseDTO.builder()
                        .eventId(eventId)
                        .build();

        given(adminEventCommandService.createEvent(any(EventRequest.EventAddRequestDTO.class)))
                .willReturn(eventAddResponseDTO);

        // when
        ResultActions result = mockMvc.perform(post("/admin/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.eventId").value(eventId));

        verify(adminEventCommandService).createEvent(any(EventRequest.EventAddRequestDTO.class));
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
        ResultActions result = mockMvc.perform(post("/admin/events")
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
        ResultActions result = mockMvc.perform(post("/admin/events")
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
    @DisplayName("공지사항 삭제 성공")
    void deleteEvent_success() throws Exception {
        // given
        Long eventId = 1L;
        doNothing().when(adminEventCommandService).deleteEvent(eventId);

        // when
        ResultActions result = mockMvc.perform(delete("/admin/events/{eventId}", eventId));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result").doesNotExist());

        verify(adminEventCommandService).deleteEvent(eventId);
    }

    @Test
    @DisplayName("공지사항 삭제 실패 - eventId 형식 오류")
    void deleteEvent_fail_wrongEventId() throws Exception {
        // given
        String eventId = "HI";

        // when
        ResultActions result = mockMvc.perform(delete("/admin/events/{eventId}", eventId));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess"). value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.INVALID_PARAMETER.getCode()));

        verifyNoInteractions(adminEventCommandService);
    }

    @Test
    @DisplayName("공지사항 수정 성공")
    void updateEvent_success() throws Exception {
        // given
        Long eventId = 1L;

        EventRequest.EventPutRequestDTO eventPutRequestDTO = EventRequest.EventPutRequestDTO.builder()
                .title("수정된행사")
                .content("수정된행사 세부 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        EventResponse.EventPutResponseDTO eventPutResponseDTO =
                EventResponse.EventPutResponseDTO.builder()
                        .eventId(eventId)
                        .build();

        given(adminEventCommandService.updateEvent(eq(eventId),any(EventRequest.EventPutRequestDTO.class))).willReturn(eventPutResponseDTO);

        // when
        ResultActions result = mockMvc.perform(put("/admin/events/{eventId}", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventPutRequestDTO)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.eventId").value(eventId));

        verify(adminEventCommandService).updateEvent(eq(eventId),any(EventRequest.EventPutRequestDTO.class));
    }
}