package hongik.heavyYoung.domain.event.service.admin.impl;

import hongik.heavyYoung.domain.event.converter.EventRequestConverter;
import hongik.heavyYoung.domain.event.converter.EventResponseConverter;
import hongik.heavyYoung.domain.event.dto.EventRequest;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 기존에 존재하는 공지사항(Event)을 삭제합니다.
     * 존재하지 않는 공지사항일 경우, EventException(EVENT_NOT_FOUND)가 발생합니다.
     *
     * @param eventId 삭제할 공지사항의 PK
     */
    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorStatus.EVENT_NOT_FOUND));
        eventRepository.delete(event);
    }

    /**
     * 기존에 존재하는 공지사항(Event)의 내용을 수정합니다.
     * 존재하지 않는 공지사항일 경우, EventException(EVENT_NOT_FOUND)가 발생합니다.
     *
     * @param eventId 수정할 공지사항의 PK
     * @param eventPutRequestDTO 공지사항 수정에 필요한 제목, 내용, 행사 시작/종료일 정보를 담은 DTO
     * @return 수정된 공지사항의 PK를 담은 DTO
     */
    @Override
    public EventResponse.EventPutResponseDTO updateEvent(Long eventId, EventRequest.EventPutRequestDTO eventPutRequestDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorStatus.EVENT_NOT_FOUND));
        event.updateByDTO(eventPutRequestDTO);
        return EventResponseConverter.toEventPutResponseDTO(event);
    }
}
