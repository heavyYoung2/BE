package hongik.heavyYoung.domain.locker.converter;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.member.entity.Member;

public class LockerResponseConverter {
    // 사물함 기본 정보
    public static LockerResponse.LockerInfoDTO toLockerInfoDTO(Locker locker, Member member, Long memberId) {
        String lockerStatus = convertLockerStatus(locker, member, memberId);

        return LockerResponse.LockerInfoDTO.builder()
                .lockerId(locker.getId())
                .lockerNumber(locker.getLockerSection() + locker.getLockerNumber())
                .lockerStatus(lockerStatus)
                .studentId(member != null ? member.getStudentId() : null)
                .studentName(member != null ? member.getStudentName() : null)
                .build();
    }

    private static String convertLockerStatus(Locker locker, Member member, Long memberId) {
        if(member != null && member.getId().equals(memberId)) {
            return "MY";
        }
        return locker.getLockerStatus().name();
    }
}

