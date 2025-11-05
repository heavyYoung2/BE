package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
class LockerAssignmentRepositoryTest {

    @Autowired
    private LockerAssignmentRepository lockerAssignmentRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 현재 학기 사물함 배정 내용 조회")
    void findByMember_IdAndIsCurrentSemesterTrue_success() {
        // given
        Locker locker1 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(1)
                .lockerStatus(LockerStatus.IN_USE)
                .build();

        Locker locker2 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(2)
                .lockerStatus(LockerStatus.IN_USE)
                .build();

        lockerRepository.save(locker1);
        lockerRepository.save(locker2);

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
        memberRepository.save(member2);

        LockerAssignment lockerAssignment1 = LockerAssignment.builder()
                .assignSemester("2025-2")
                .locker(locker1)
                .member(member1)
                .isCurrentSemester(true)
                .build();

        LockerAssignment lockerAssignment2 = LockerAssignment.builder()
                .assignSemester("2025-2")
                .locker(locker2)
                .member(member2)
                .isCurrentSemester(true)
                .build();

        lockerAssignmentRepository.save(lockerAssignment1);

        Long loginMemberId = member1.getId();

        // when
        Optional<LockerAssignment> result = lockerAssignmentRepository.findByMember_IdAndIsCurrentSemesterTrue(loginMemberId);

        // then
        LockerAssignment lockerAssignment = result.get();
        assertEquals(lockerAssignment.getLocker().getLockerSection(), "A");
        assertEquals(lockerAssignment.getLocker().getLockerNumber(), 1);
        assertEquals(lockerAssignment.getMember().getStudentId(), "C011117");
    }


}