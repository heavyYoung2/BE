package hongik.heavyYoung.domain.event.entity;

import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(name = "event_start_at")
    private LocalDate eventStartAt;

    @Column(name = "event_end_at")
    private LocalDate eventEndAt;
}