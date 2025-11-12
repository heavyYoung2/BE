package hongik.heavyYoung.domain.application.repository;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByApplicationTypeInOrderByCreatedAtDesc(Collection<ApplicationType> types);

    @Query("""
  SELECT a FROM Application a
  WHERE a.applicationType IN :types
    AND a.applicationStartAt <= :now
    AND a.applicationEndAt >= :now
     AND a.applicationMemberCount < a.applicationCanCount
""")
    Optional<Application> findActiveLockerApplications(@Param("now") LocalDateTime now, @Param("types") Set<ApplicationType> types);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Application a SET a.applicationMemberCount = a.applicationMemberCount + 1 WHERE a.id = :id")
    void incrementApplicationMemberCount(@Param("id") Long id);

    Optional<Application> findFirstByApplicationTypeInOrderByApplicationSemesterDesc(Set<ApplicationType> types);

    @Query("""
        SELECT a.applicationSemester
        FROM Application a
        WHERE a.applicationType IN :types
        ORDER BY a.applicationSemester DESC
    """)
    List<String> findAllSemestersByApplicationTypeInOrderBySemesterDesc(@Param("types") Set<ApplicationType> types);
}
