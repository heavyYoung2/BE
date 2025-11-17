package hongik.heavyYoung.domain.locker.repository;

import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LockerAssignmentRepository extends JpaRepository<LockerAssignment, Long> {
    Optional<LockerAssignment> findByMember_IdAndIsCurrentSemesterTrue(Long memberId);

    @Modifying
    @Query("UPDATE LockerAssignment la SET la.isCurrentSemester = false WHERE la.isCurrentSemester = true")
    void updateAllByCurrentSemesterFalse();


    @Query("""
    select la
    from LockerAssignment la
    join fetch la.member m
    join fetch la.locker l
    where la.assignSemester = :semester
    order by l.lockerSection asc, l.lockerNumber asc, m.studentId asc
""")
    List<LockerAssignment> findAllBySemesterWithMemberAndLocker(@Param("semester") String semester);

    Optional<LockerAssignment> findByLocker_IdAndIsCurrentSemesterTrue(Long lockerId);

    @Query("""
    select la.assignSemester
    from LockerAssignment  la
    order by la.assignSemester desc
    limit 1
""")
    Optional<String> findCurrentSemester();
}
