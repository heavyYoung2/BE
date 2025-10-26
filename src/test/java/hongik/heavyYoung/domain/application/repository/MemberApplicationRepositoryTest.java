package hongik.heavyYoung.domain.application.repository;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberStatus;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.config.TestJpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
class MemberApplicationRepositoryTest {

    @Autowired
    private MemberApplicationRepository memberApplicationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    @DisplayName("사용자가 현재 학기에 배정 받지 못한 사물함 신청 여부 확인 성공")
    void existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType_success() {
        // given
        Application lockerApplication1 = Application.builder()
                .applicationStartAt(LocalDateTime.of(2025, 10, 10, 12, 0))
                .applicationEndAt(LocalDateTime.of(2025, 10, 10, 17, 0))
                .applicationSemester("2025-02")
                .applicationType(ApplicationType.LOCKER_MAIN)
                .applicationCanCount(400)
                .applicationMemberCount(50)
                .canApply(true)
                .canAssign(true)
                .build();

        applicationRepository.save(lockerApplication1);

        Member member1 = Member.builder()
                .studentId("C011117")
                .studentName("안제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("ahnjewoong@gmail.com")
                .phoneNumber("010-1234-5678")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        Member member2 = Member.builder()
                .studentId("C011118")
                .studentName("박제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("parkjewoong@gmail.com")
                .phoneNumber("010-5678-9012")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        memberRepository.save(member1);

        MemberApplication memberApplication1 = MemberApplication.builder()
                .application(lockerApplication1)
                .member(member1).build();

        memberApplicationRepository.save(memberApplication1);

        Long loginMemberId1 = member1.getId();
        Long loginMemberId2 = member2.getId();

        // when
        boolean result1 = memberApplicationRepository.existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(loginMemberId1, ApplicationType.LOCKER_MAIN);
        boolean result2 = memberApplicationRepository.existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(loginMemberId2, ApplicationType.LOCKER_MAIN);

        // then
        assertTrue(result1);
        assertFalse(result2);
    }

}