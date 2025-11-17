package hongik.heavyYoung.domain.locker.service.general.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerRentalStatus;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberStatus;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LockerQueryServiceImplTest {

    @Mock
    private LockerRepository lockerRepository;

    @Mock
    private LockerAssignmentRepository lockerAssignmentRepository;

    @Mock
    private MemberApplicationRepository memberApplicationRepository;

    @InjectMocks
    private LockerQueryServiceImpl lockerQueryService;

    @Test
    @DisplayName("A 구역 사물함 전체 조회 성공")
    void findAllLockers_findSection_A() {
        // given
        List<Object[]> lockers = new ArrayList<>();

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

        Member member1 = Member.builder()
                .id(1L)
                .studentId("C011117")
                .studentName("안제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("ahnjewoong@gmail.com")
                .phoneNumber("010-1234-5678")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        Member member2 = Member.builder()
                .id(2L)
                .studentId("C011118")
                .studentName("박제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("parkjewoong@gmail.com")
                .phoneNumber("010-5678-9012")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        lockers.add(new Object[] {locker1, member1});
        lockers.add(new Object[] {locker2, member2});
        lockers.add(new Object[] {locker3, null});
        lockers.add(new Object[] {locker4, null});

        Long loginMemberId = member1.getId();

        given(lockerRepository.findAllWithCurrentSemesterAssign("A")).willReturn(lockers);

        // when
        List<LockerResponse.LockerInfoDTO> result = lockerQueryService.findAllLockers("A", loginMemberId);

        // then
        assertEquals(result.getFirst().getLockerNumber(), "A1");
        assertEquals(result.getFirst().getStudentId(), "C011117");
        assertEquals(result.getFirst().getStudentName(), "안제웅");

        assertEquals(result.get(1).getLockerNumber(), "A2");
        assertEquals(result.get(1).getStudentId(), "C011118");
        assertEquals(result.get(1).getStudentName(), "박제웅");

        assertEquals(result.get(2).getLockerNumber(), "A3");
        assertNull(result.get(2).getStudentId());

        assertEquals(result.get(3).getLockerNumber(), "A4");
        assertNull(result.get(3).getStudentId());


        verify(lockerRepository).findAllWithCurrentSemesterAssign("A");
    }

    @Test
    @DisplayName("나의 사물함 조회 성공 - 로그인 사용자가 사물함 배정된 경우")
    void findMyLocker_lockerAssigned_success() {
        // given
        Locker locker1 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(1)
                .lockerStatus(LockerStatus.IN_USE)
                .build();

        Member member1 = Member.builder()
                .id(1L)
                .studentId("C011117")
                .studentName("안제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("ahnjewoong@gmail.com")
                .phoneNumber("010-1234-5678")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        LockerAssignment lockerAssignment1 = LockerAssignment.builder()
                .assignSemester("2025-2")
                .locker(locker1)
                .member(member1)
                .isCurrentSemester(true)
                .build();

        Long loginMemberId = member1.getId();

        given(lockerAssignmentRepository.findByMember_IdAndIsCurrentSemesterTrue(loginMemberId)).willReturn(Optional.ofNullable(lockerAssignment1));

        // when
        LockerResponse.MyLockerInfoDTO result = lockerQueryService.findMyLocker(loginMemberId);

        // then
        assertEquals(result.getLockerId(), locker1.getId());
        assertEquals(result.getLockerNumber(), locker1.getLockerSection() + locker1.getLockerNumber());
        assertEquals(result.getLockerRentalStatus(), LockerRentalStatus.RENTING.name());
    }

    @Test
    @DisplayName("나의 사물함 조회 성공 - 로그인 사용자가 사물함 배정되진 않고, 신청중인 경우")
    void findMyLocker_lockerNotAssignedAndApplied_success() {
        // given
        Application lockerApplication1 = Application.builder()
                .id(1L)
                .applicationStartAt(LocalDateTime.of(2025, 10, 10, 12, 0))
                .applicationEndAt(LocalDateTime.of(2025, 10, 10, 17, 0))
                .applicationSemester("2025-02")
                .applicationType(ApplicationType.LOCKER_MAIN)
                .applicationCanCount(400)
                .applicationMemberCount(50)
                .canApply(true)
                .canAssign(true)
                .build();


        Member member1 = Member.builder()
                .id(1L)
                .studentId("C011117")
                .studentName("안제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("ahnjewoong@gmail.com")
                .phoneNumber("010-1234-5678")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        MemberApplication memberApplication1 = MemberApplication.builder()
                .id(1L)
                .application(lockerApplication1)
                .member(member1).build();

        Long loginMemberId = member1.getId();

        given(lockerAssignmentRepository.findByMember_IdAndIsCurrentSemesterTrue(loginMemberId)).willReturn(Optional.empty());
        given(memberApplicationRepository.existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(loginMemberId, ApplicationType.LOCKER_MAIN)).willReturn(true);

        // when
        LockerResponse.MyLockerInfoDTO result = lockerQueryService.findMyLocker(loginMemberId);

        // then
        assertNull(result.getLockerId());
        assertNull(result.getLockerNumber());
        assertEquals(result.getLockerRentalStatus(), LockerRentalStatus.RENTAL_REQUESTED.name());
    }

    @Test
    @DisplayName("나의 사물함 조회 성공 - 로그인 사용자가 사물함 배정되지 않고, 신청중이지도 않은 경우")
    void findMyLocker_lockerNotAssignedAndNotApplied_success() {
        // given
        Member member1 = Member.builder()
                .id(1L)
                .studentId("C011117")
                .studentName("안제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("ahnjewoong@gmail.com")
                .phoneNumber("010-1234-5678")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        Long loginMemberId = member1.getId();

        given(lockerAssignmentRepository.findByMember_IdAndIsCurrentSemesterTrue(loginMemberId)).willReturn(Optional.empty());
        given(memberApplicationRepository.existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(loginMemberId, ApplicationType.LOCKER_MAIN)).willReturn(false);

        // when
        LockerResponse.MyLockerInfoDTO result = lockerQueryService.findMyLocker(loginMemberId);

        // then
        assertNull(result.getLockerId());
        assertNull(result.getLockerNumber());
        assertEquals(result.getLockerRentalStatus(), LockerRentalStatus.NO_RENTAL.name());
    }


}