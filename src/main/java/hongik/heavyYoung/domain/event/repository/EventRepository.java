package hongik.heavyYoung.domain.event.repository;

import hongik.heavyYoung.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByUpdatedAtDesc();
    List<Event> findAllByEventStartDateBetweenOrderByUpdatedAtDesc(LocalDate from, LocalDate to);
}
