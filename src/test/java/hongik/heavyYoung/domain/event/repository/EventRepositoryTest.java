package hongik.heavyYoung.domain.event.repository;

import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.config.TestJpaAuditingConfig;
import hongik.heavyYoung.global.exception.GeneralException;
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
    void findAllByOrderByCreatedAtDesc() {
        // given
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 10, 1))
                .eventEndDate(LocalDate.of(2025, 10, 2))
                .build();

        eventRepository.save(event1);
        eventRepository.save(event2);

        // when
        List<Event> result = eventRepository.findAllByOrderByCreatedAtDesc();

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventTitle(), "나눔행사");
        assertEquals(result.get(1).getEventTitle(),"간식행사");
    }

    @Test
    @DisplayName("공지사항 조회(기간별) 성공")
    void findAllByEventStartDateBetweenOrderByCreatedAtDesc() {
        // given
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        Event event2 = Event.builder()
                .eventTitle("나눔행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 10, 1))
                .eventEndDate(LocalDate.of(2025, 10, 2))
                .build();

        Event event3 = Event.builder()
                .eventTitle("운동행사")
                .eventContent("나눔행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 4))
                .eventEndDate(LocalDate.of(2025, 9, 5))
                .build();

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);

        // when
        List<Event> result = eventRepository.findAllByEventStartDateBetweenOrderByCreatedAtDesc(
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 9, 30)
        );

        // then
        assertThat(result).hasSize(2);
        assertEquals(result.getFirst().getEventTitle(), "운동행사");
        assertEquals(result.get(1).getEventTitle(), "간식행사");
    }

    @Test
    @DisplayName("공지사항 상세 조회(사진포함) 성공")
    void findByIdWithImages(){
        // given
        Event event1 = Event.builder()
                .eventTitle("간식행사")
                .eventContent("간식행사 상세 일정")
                .eventStartDate(LocalDate.of(2025, 9, 1))
                .eventEndDate(LocalDate.of(2025, 9, 2))
                .build();

        eventRepository.save(event1);

        EventImage eventImage1 = EventImage.builder()
                .event(event1)
                .eventImageUrl("url1")
                .sortOrder(1)
                .build();

        EventImage eventImage2 = EventImage.builder()
                .event(event1)
                .eventImageUrl("url2")
                .sortOrder(2)
                .build();

        event1.addEventImage(eventImage1);
        event1.addEventImage(eventImage2);

        // when
        Event result = eventRepository.findByIdWithImages(event1.getId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.EVENT_NOT_FOUND));

        // then
        assertEquals(result.getEventContent(), "간식행사 상세 일정");
        assertThat(result.getEventImages()).hasSize(2);
        assertThat(result.getEventImages().getFirst().getEventImageUrl())
                .isEqualTo("url1");
        assertThat(result.getEventImages()).extracting(EventImage::getSortOrder).containsExactly(1, 2);
    }
}