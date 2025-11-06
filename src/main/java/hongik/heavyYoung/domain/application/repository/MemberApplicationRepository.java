package hongik.heavyYoung.domain.application.repository;

import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberApplicationRepository extends JpaRepository<MemberApplication, Long> {
    boolean existsByMember_IdAndApplication_CanAssignTrueAndApplication_ApplicationType(
            Long memberId,
            ApplicationType applicationType
    );
}
