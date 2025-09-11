package hongik.heavyYoung.domain.event.service.admin.impl;

import hongik.heavyYoung.domain.event.converter.EventRequestConverter;
import hongik.heavyYoung.domain.event.converter.EventResponseConverter;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminEventCommandServiceImpl implements AdminEventCommandService {

    private final EventRepository eventRepository;

    /**
     * 새로운 공지사항(Event)을 생성합니다.
     *
     * @param eventAddRequestDTO 공지사항 생성에 필요한 제목, 내용, 행사 시작/종료일 정보를 담은 DTO
     * @return 생성된 공지사항의 PK를 담은 응답 DTO
     */
    @Override
    public EventResponse.EventAddResponseDTO createEvent(EventRequest.EventAddRequestDTO eventAddRequestDTO) {
        Event event = EventRequestConverter.toNewEvent(eventAddRequestDTO);
        Event savedEvent = eventRepository.save(event);
        // TODO 사진 업로드 방식 결정 후, 공지사항(Event)에 사진 추가 로직 필요
        return EventResponseConverter.toEventAddResponseDTO(savedEvent);
    }
}
