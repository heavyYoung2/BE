package hongik.heavyYoung.domain.event.entity;

import hongik.heavyYoung.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "event_image")
public class EventImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "event_image_key)", nullable = false)
    private String eventImageKey;

    @Lob
    @Column(name = "event_image_url", nullable = false)
    private String eventImageUrl;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    protected void setEvent(Event event) {
        this.event = event;
    }

    protected void removeEvent() {
        this.event = null;
    }
}