package hongik.heavyYoung.domain.locker.service.admin;

import hongik.heavyYoung.domain.locker.dto.LockerRequestDTO;

public interface AdminLockerCommandService {
    void addLockerApplication(LockerRequestDTO.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO);
    void returnCurrentSemesterLockers();
    void assignLockersByApplication(Long lockerApplicationId);
    void finishLockerApplication(Long lockerApplicationId);
    void changeLockerNotAvailable(Long lockerId);
    void changeLockerAvailable(Long lockerId);
    void changeLockerUsing(Long lockerId, String studentId);
}
