package hongik.heavyYoung.domain.locker.service.admin;

import hongik.heavyYoung.domain.locker.dto.LockerRequest;

public interface AdminLockerCommandService {
    void addLockerApplication(LockerRequest.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO);
    void returnCurrentSemesterLockers();
    void assignLockersByApplication(Long lockerApplicationId);
}
