package hongik.heavyYoung.domain.locker.service.admin;

import hongik.heavyYoung.domain.locker.dto.LockerRequest;

public interface AdminLockerCommandService {
    void addLockerApplication(LockerRequest.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO);
    void returnCurrentSemesterLockers();
    void assignLockersByApplication(Long lockerApplicationId);
    void finishLockerApplication(Long lockerApplicationId);
    void changeLockerNotAvailable(Long lockerId);
    void changeLockerAvailable(Long lockerId);
    void changeLockerUsing(Long lockerId, String studentId);
}
