package hongik.heavyYoung.domain.event.repository;

import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    void deleteAllByEvent(Event event);

    List<EventImage> findAllByEvent(Event event);
}
