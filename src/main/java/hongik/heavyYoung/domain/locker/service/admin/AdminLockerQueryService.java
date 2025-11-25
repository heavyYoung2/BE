package hongik.heavyYoung.domain.locker.service.admin;

import hongik.heavyYoung.domain.locker.dto.LockerResponseDTO;

import java.util.List;

public interface AdminLockerQueryService {
    List<LockerResponseDTO.LockerApplicationInfoDTO> findAllLockerApplication();
    LockerResponseDTO.LockerApplicationDetailInfoDTO findLockerApplicationDetail(Long lockerApplicationId);
    List<LockerResponseDTO.LockerAssignmentInfoDTO> findLockerAssignments(String semester);
    List<String> findLockerAssignSemesters();
}
