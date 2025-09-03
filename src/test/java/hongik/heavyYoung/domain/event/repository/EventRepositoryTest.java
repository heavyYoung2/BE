package hongik.heavyYoung.domain.event.repository;

import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.global.config.TestJpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("공지사항 조회(전체) 성공")
    void findAll(){
        // given
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 10, 1))
                .eventEndAt(LocalDate.of(2025, 10, 2))
                .build();

        eventRepository.save(event1);
        eventRepository.save(event2);

        // when
        List<Event> result = eventRepository.findAllByOrderByUpdatedAtDesc();

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventTitle(), "나눔행사");
        assertEquals(result.get(1).getEventTitle(),"간식행사");
    }

    @Test
    @DisplayName("공지사항 조회(기간별) 성공")
    void findAllByEventStartAtBetweenOrderByUpdatedAt() {
        // given
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 1))
                .eventEndAt(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 10, 1))
                .eventEndAt(LocalDate.of(2025, 10, 2))
                .build();

        Event event3 = Event.builder()
                .eventTitle("운동행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartAt(LocalDate.of(2025, 9, 4))
                .eventEndAt(LocalDate.of(2025, 9, 5))
                .build();

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);

        // when
        List<Event> result = eventRepository.findAllByEventStartAtBetweenOrderByUpdatedAtDesc(
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 9, 30)
        );

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventTitle(), "운동행사");
        assertEquals(result.get(1).getEventTitle(), "간식행사");
    }
}