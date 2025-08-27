package hongik.heavyYoung.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TempResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TempTestDTO {
        String testString;
    }
}
