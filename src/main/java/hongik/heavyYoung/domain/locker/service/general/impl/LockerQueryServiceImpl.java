package hongik.heavyYoung.domain.locker.service.general.impl;

import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.locker.converter.LockerResponseConverter;
import hongik.heavyYoung.domain.locker.dto.LockerResponseDTO;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerRentalStatus;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LockerQueryServiceImpl implements LockerQueryService {

    private final LockerRepository lockerRepository;
    private final LockerAssignmentRepository lockerAssignmentRepository;
    private final MemberApplicationRepository memberApplicationRepository;

    /**
     * 섹션 별 전체 사물함 정보를 조회합니다.
     * A,B,C,D,E,F 섹션에 맞게 사물함의 정보를 조회할 수 있습니다.
     *
     * @param lockerSection 사물함 섹션 번호
     * @return 섹션 별 전체 사물함 정보 리스트
     */
    @Override
    public List<LockerResponseDTO.LockerInfoDTO> findAllLockers(String lockerSection, Long memberId) {
        List<Object[]> lockerMember = lockerRepository.findAllWithCurrentSemesterAssign(lockerSection);

        return lockerMember.stream()
                .map(row -> {
                    Locker locker = (Locker) row[0];
                    Member member = (Member) row[1];

                    String lockerStatus = (member != null && member.getId().equals(memberId))
                            ? LockerStatus.MY.name()
                            : locker.getLockerStatus().name();

                    return LockerResponseConverter.toLockerInfoDTO(locker, member, lockerStatus);
                })
                .toList();
    }

    /**
     * 로그인된 사용자의 사물함 정보를 조회합니다.
     * 1. 사용자가 사물함을 대여중인 상황에는 사물함 PK, 사물함 번호, 사물함 대여 상태(RENTING)로 응답합니다.
     * 2. 사용자가 사물함 대여중이지는 않지만, 사물함 대여를 신청중인 상황에는 사물함 대여 상태(RENTAL_REQUESTED)로 응답합니다.
     * 3. 사용자가 사물함 대여중이지 않고, 사물함 대여를 신청하지 않은 상황에는 사물함 대여 상태(NO_RENTAL)로 응답합니다.
     *
     * @return 나의 사물함 정보
     */
    @Override
    public LockerResponseDTO.MyLockerInfoDTO findMyLocker(Long memberId) {
        Optional<LockerAssignment> lockerAssignment = lockerAssignmentRepository.findByMember_IdAndIsCurrentSemesterTrue(memberId);

        if (lockerAssignment.isPresent()) {
            Locker locker = lockerAssignment.get().getLocker();
            return LockerResponseConverter.toMyLockerInfoDTO(locker, LockerRentalStatus.RENTING);
        }

        boolean lockerRequested = memberApplicationRepository.existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(memberId, ApplicationType.LOCKER_MAIN);

        if (lockerRequested) {
            return LockerResponseConverter.toMyLockerInfoDTO(LockerRentalStatus.RENTAL_REQUESTED);
        } else {
            return LockerResponseConverter.toMyLockerInfoDTO(LockerRentalStatus.NO_RENTAL);
        }
    }
}
