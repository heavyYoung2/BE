package hongik.heavyYoung.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MemberResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlacklistInfo {
        private boolean blacklisted;
        private LocalDate blacklistUntil;
    }
}
