package hongik.heavyYoung.domain.event.repository;

import hongik.heavyYoung.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByCreatedAtDesc();
    List<Event> findAllByEventStartDateBetweenOrderByCreatedAtDesc(LocalDate from, LocalDate to);
    @Query("SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.eventImages WHERE e.id = :id")
    Optional<Event> findByIdWithImages(@Param("id") Long id);
}
