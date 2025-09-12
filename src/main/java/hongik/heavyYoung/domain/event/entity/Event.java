package hongik.heavyYoung.domain.event.entity;

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
    @Column(name = "event_content", nullable = false)
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

    public void removeEventImage(EventImage image) {
        if (image == null) return;
        this.eventImages.remove(image);
        image.removeEvent();
    }

    /** 수정 관련 메서드 */
    public void updateByDTO(EventRequest.EventPutRequestDTO eventPutRequestDTO) {
        this.changeEventTitle(eventPutRequestDTO.getTitle());
        this.changeEventContent(eventPutRequestDTO.getContent());
        this.changeEventStartDate(eventPutRequestDTO.getEventStartDate());
        this.changeEventEndDate(eventPutRequestDTO.getEventEndDate());
    }

    public void changeEventTitle(String title) {
        this.eventTitle = title;
    }

    public void changeEventContent(String content) {
        this.eventContent = content;
    }

    public void changeEventStartDate(LocalDate eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public void changeEventEndDate(LocalDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
}