package hongik.heavyYoung.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class MemberResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlacklistInfo {
        private boolean blacklisted;
        private LocalDate blacklistUntil;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentCouncilInfo{

        private List<StudentCouncilMemberInfo> studentCouncilMembers;

        @Getter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class StudentCouncilMemberInfo {
            private Long memberId;
            private String studentId;
            private String studentName;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentCouncilCandidateInfo {
        private Long memberId;
        private String studentId;
        private String studentName;
    }
}
