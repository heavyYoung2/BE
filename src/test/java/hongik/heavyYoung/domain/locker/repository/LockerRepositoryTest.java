package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberStatus;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.global.config.TestJpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private LockerAssignmentRepository lockerAssignmentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("A 구역 사물함 전체 조회 성공")
    void findAllWithCurrentSemesterAssign_findSection_A() {
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

        Locker locker3 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(3)
                .lockerStatus(LockerStatus.CANT_USE)
                .build();

        Locker locker4 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(4)
                .lockerStatus(LockerStatus.AVAILABLE)
                .build();

        Locker locker5 = Locker.builder()
                .lockerSection("B")
                .lockerNumber(1)
                .lockerStatus(LockerStatus.AVAILABLE)
                .build();

        lockerRepository.save(locker1);
        lockerRepository.save(locker2);
        lockerRepository.save(locker3);
        lockerRepository.save(locker4);
        lockerRepository.save(locker5);

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
        lockerAssignmentRepository.save(lockerAssignment2);


        // when
        List<Object[]> result = lockerRepository.findAllWithCurrentSemesterAssign("A");

        // then
        assertThat(result).hasSize(4);

        Locker resultLocker = (Locker) result.getFirst()[0];
        Member resultMember = (Member) result.getFirst()[1];

        assertEquals(resultLocker.getLockerSection(), "A");
        assertEquals(resultLocker.getLockerNumber(), 1);
        assertEquals(resultMember.getStudentId(), "C011117");
    }
}