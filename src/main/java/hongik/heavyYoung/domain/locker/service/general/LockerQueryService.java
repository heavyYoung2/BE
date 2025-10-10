package hongik.heavyYoung.domain.locker.service.general;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;

import java.util.List;

public interface LockerQueryService {
    List<LockerResponse.LockerInfoDTO> findAllLockers(String lockerSection);
    LockerResponse.MyLockerInfoDTO findMyLocker();
}
