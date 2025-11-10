package hongik.heavyYoung.domain.locker.service.admin;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;

import java.util.List;

public interface AdminLockerQueryService {
    List<LockerResponse.LockerApplicationInfoDTO> findAllLockerApplication();
    LockerResponse.LockerApplicationDetailInfoDTO findLockerApplicationDetail(Long lockerApplicationId);
}
