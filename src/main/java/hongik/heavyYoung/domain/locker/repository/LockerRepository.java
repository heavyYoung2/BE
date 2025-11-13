package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LockerRepository extends JpaRepository<Locker, Long> {
    @Query("""
    SELECT l, m
    FROM Locker l
    LEFT JOIN LockerAssignment la ON la.locker = l AND la.isCurrentSemester = true
    LEFT JOIN la.member m
    WHERE l.lockerSection = :lockerSection
    ORDER BY l.lockerNumber ASC
""")
    List<Object[]> findAllWithCurrentSemesterAssign(@Param("lockerSection") String lockerSection);

    int countByLockerStatus(LockerStatus lockerStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Locker> findFirstByLockerStatus(LockerStatus lockerStatus);

    @Modifying
    @Query("UPDATE Locker l SET l.lockerStatus = 'AVAILABLE' WHERE l.lockerStatus = 'IN_USE'")
    void updateAllInUseToAvailable();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Locker l where l.lockerStatus = 'AVAILABLE' order by l.lockerSection asc, l.lockerNumber asc")
    List<Locker> findAllAvailableForUpdate();
}
