package hongik.heavyYoung.domain.locker.service.general.impl;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LockerQueryServiceImplTest {

    @Mock
    private LockerRepository lockerRepository;

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
                .lockerStatus(LockerStatus.BROKEN)
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

        given(lockerRepository.findAllWithCurrentSemesterAssign("A")).willReturn(lockers);

        // when
        List<LockerResponse.LockerInfoDTO> result = lockerQueryService.findAllLockers("A");

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

}