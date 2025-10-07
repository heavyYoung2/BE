package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.Locker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
}
