package hongik.heavyYoung.domain.member.converter;

import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;

import java.time.LocalDate;

public class MemberConverter {

    public static MemberResponseDTO.BlacklistInfo toBlacklistInfo(
            boolean blacklisted,
            LocalDate blacklistUntil
    ) {
        return MemberResponseDTO.BlacklistInfo.builder()
                .blacklisted(blacklisted)
                .blacklistUntil(blacklistUntil)
                .build();
    }
}
