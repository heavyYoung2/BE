package hongik.heavyYoung.domain.application.repository;

import hongik.heavyYoung.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
