package hongik.heavyYoung.domain.event.entity;

import hongik.heavyYoung.domain.event.command.CreateEventCommand;
import hongik.heavyYoung.domain.event.command.UpdateEventCommand;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_title", nullable = false, length = 100)
    private String eventTitle;

    @Lob
    @Column(name = "event_content", nullable = false, columnDefinition = "TEXT")
    private String eventContent;

    @Column(name = "event_start_date")
    private LocalDate eventStartDate;

    @Column(name = "event_end_date")
    private LocalDate eventEndDate;

    @Builder.Default
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<EventImage> eventImages = new ArrayList<>();

    /** 연관관계 편의 메서드 */
    public void addEventImage(EventImage image) {
        if (image == null) return;
        this.eventImages.add(image);
        image.setEvent(this);
    }

    /** 생성 관련 메서드 */
    public static Event create(CreateEventCommand createEventCommand) {
        return Event.builder().
                eventTitle(createEventCommand.title())
                .eventContent(createEventCommand.content())
                .eventStartDate(createEventCommand.startDate())
                .eventEndDate(createEventCommand.endDate())
                .build();
    }

    /** 수정 관련 메서드 */
    public void update(UpdateEventCommand updateEventCommand) {
        this.eventTitle = updateEventCommand.title();
        this.eventContent = updateEventCommand.content();
        this.eventStartDate = updateEventCommand.startDate();
        this.eventEndDate = updateEventCommand.endDate();
    }
}