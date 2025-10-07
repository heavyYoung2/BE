package hongik.heavyYoung.domain.locker.service.general.impl;

import hongik.heavyYoung.domain.locker.converter.LockerResponseConverter;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LockerQueryServiceImpl implements LockerQueryService {

    private final LockerRepository lockerRepository;
    private static final Long DUMMY_MEMBER_ID = 1L;

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
    public List<LockerResponse.LockerInfoDTO> findAllLockers(String lockerSection) {
        List<Object[]> lockerMember = lockerRepository.findAllWithCurrentSemesterAssign(lockerSection);

        return lockerMember.stream()
                .map(row -> {
                    Locker locker = (Locker) row[0];
                    Member member = (Member) row[1];
                    return LockerResponseConverter.toLockerInfoDTO(locker, member, DUMMY_MEMBER_ID);
                })
                .toList();
    }
}
