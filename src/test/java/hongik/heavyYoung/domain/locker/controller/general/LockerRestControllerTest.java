package hongik.heavyYoung.domain.locker.controller.general;

import hongik.heavyYoung.domain.locker.config.LockerRestControllerTestConfig;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LockerRestController.class)
@Import(LockerRestControllerTestConfig.class)
class LockerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LockerQueryService lockerQueryService;

    @BeforeEach
    void resetMocks() {
        reset(lockerQueryService);
    }

    @Test
    @DisplayName("A구역 사물함 전체 조회 성공")
    void getAllLockers_getSection_A_success() throws Exception {
        // given
        LockerResponse.LockerInfoDTO lockerInfoDTO1 = LockerResponse.LockerInfoDTO.builder()
                .lockerId(1L)
                .lockerNumber("A1")
                .studentId("C011117")
                .studentName("안제웅")
                .lockerStatus("MY")
                .build();

        LockerResponse.LockerInfoDTO lockerInfoDTO2 = LockerResponse.LockerInfoDTO.builder()
                .lockerId(2L)
                .lockerNumber("A2")
                .lockerStatus("AVAILABLE")
                .build();

        given(lockerQueryService.findAllLockers("A")).willReturn(List.of(lockerInfoDTO1, lockerInfoDTO2));

        // when
        ResultActions result = mockMvc.perform(get("/lockers")
                        .param("lockerSection", "A")
                        .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result[0].lockerId").value(1L))
                .andExpect(jsonPath("$.result[0].lockerNumber").value("A1"))
                .andExpect(jsonPath("$.result[0].studentId").value("C011117"))
                .andExpect(jsonPath("$.result[0].studentName").value("안제웅"))
                .andExpect(jsonPath("$.result[0].lockerStatus").value("MY"))
                .andExpect(jsonPath("$.result[1].lockerId").value(2L))
                .andExpect(jsonPath("$.result[1].lockerNumber").value("A2"))
                .andExpect(jsonPath("$.result[1].lockerStatus").value("AVAILABLE"));
    }

    @Test
    @DisplayName("사물함 전체 조회시 구역(lockerSection) 파라미터가 A~I가 아닌 경우")
    void getAllLockers_invalidLockerSection() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/lockers")
                .param("lockerSection", "a")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(false))
                .andExpect(jsonPath("$.code").value(ErrorStatus.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value("요청 값이 유효하지 않습니다."));
    }

}