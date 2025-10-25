package hongik.heavyYoung.domain;

import hongik.heavyYoung.global.apiPayload.ApiResponse;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempController {

    @GetMapping("/test1")
    public ApiResponse<TempResponse.TempTestDTO> test1() {
        return ApiResponse.onSuccess(TempResponse.TempTestDTO.builder().testString("테스트").build());
    }

    @GetMapping("/test2")
    public ApiResponse<TempResponse.TempTestDTO> test2() {
        throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
    }

    @GetMapping("/test3")
    public ApiResponse<TempResponse.TempTestDTO> test3() throws Exception {
        throw new Exception(ErrorStatus.INTERNAL_SERVER_ERROR.getMessage());
    }
}
