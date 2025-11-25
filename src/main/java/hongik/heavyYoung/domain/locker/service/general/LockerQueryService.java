package hongik.heavyYoung.domain.locker.service.general;

import hongik.heavyYoung.domain.locker.dto.LockerResponseDTO;

import java.util.List;

public interface LockerQueryService {
    List<LockerResponseDTO.LockerInfoDTO> findAllLockers(String lockerSection, Long memberId);
    LockerResponseDTO.MyLockerInfoDTO findMyLocker(Long memberId);
}
