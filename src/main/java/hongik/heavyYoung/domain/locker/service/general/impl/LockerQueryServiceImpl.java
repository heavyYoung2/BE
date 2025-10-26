package hongik.heavyYoung.domain.locker.service.general.impl;

import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.locker.converter.LockerResponseConverter;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerRentalStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

    // TODO 로그인 구현 시 하드 코딩된 로그인 멤버 아이디 제거

    /**
     * 섹션 별 전체 사물함 정보를 조회합니다.
     * A,B,C,D,E,F 섹션에 맞게 사물함의 정보를 조회할 수 있습니다.
     * 처음 가져올 때 DB 에서 정보를 가져오고, 이후 Redis 활용 캐시에서 정보를 가져옵니다.
     *
     * @param lockerSection 사물함 섹션 번호
     * @return 섹션 별 전체 사물함 정보 리스트
     */
    @Override
    @Cacheable(value = "lockers", key = "#lockerSection")
    public List<LockerResponse.LockerInfoDTO> findAllLockers(String lockerSection, Long memberId) {
        List<Object[]> lockerMember = lockerRepository.findAllWithCurrentSemesterAssign(lockerSection);

        return lockerMember.stream()
                .map(row -> {
                    Locker locker = (Locker) row[0];
                    Member member = (Member) row[1];

                    return LockerResponseConverter.toLockerInfoDTO(locker, member, memberId);
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
    public LockerResponse.MyLockerInfoDTO findMyLocker(Long memberId) {
        // TODO 로그인 구현 시 하드 코딩된 로그인 멤버 아이디로 대체
        Optional<LockerAssignment> lockerAssignment = lockerAssignmentRepository.findByMember_IdAndIsCurrentSemesterTrue(memberId);

        if (lockerAssignment.isPresent()) {
            Locker locker = lockerAssignment.get().getLocker();
            return LockerResponseConverter.toMyLockerInfoDTO(locker, LockerRentalStatus.RENTING);
        }

        // TODO 로그인 구현 시 하드 코딩된 로그인 멤버 아이디로 대체
        boolean lockerRequested = memberApplicationRepository.existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(memberId, ApplicationType.LOCKER_MAIN);

        if (lockerRequested) {
            return LockerResponseConverter.toMyLockerInfoDTO(null, LockerRentalStatus.RENTAL_REQUESTED);
        } else {
            return LockerResponseConverter.toMyLockerInfoDTO(null, LockerRentalStatus.NO_RENTAL);
        }
    }
}
