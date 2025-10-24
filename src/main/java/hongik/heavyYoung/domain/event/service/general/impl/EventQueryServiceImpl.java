package hongik.heavyYoung.domain.event.service.general.impl;

import hongik.heavyYoung.domain.event.converter.EventResponseConverter;
import hongik.heavyYoung.domain.event.dto.EventResponse;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.general.EventQueryService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventQueryServiceImpl implements EventQueryService {

    private final EventRepository eventRepository;

    /**
     * 공지사항(Event) 전체 또는 특정 기간의 목록을 조회합니다.
     * 시작일과 종료일이 모두 전달되면 해당 기간 내의 이벤트만 조회합니다.
     * 둘 다 전달되지 않으면 전체 이벤트를 조회합니다.
     *
     * @param from 조회 시작일 (yyyy-MM-dd), null 허용
     * @param to   조회 종료일 (yyyy-MM-dd), null 허용
     * @return 조회된 공지사항(Event) 정보 리스트
     */
    @Override
    public List<EventResponse.EventInfoDTO> findEvents(LocalDate from, LocalDate to) {
        List<Event> allEvents;

        // 시작일, 종료일이 전달된 경우 (기간별 조회)
        if (from != null &&  to!=null) {
            allEvents = eventRepository.findAllByEventStartDateBetweenOrderByCreatedAtDesc(from, to);
        } else {
            // 시작일, 종료일 둘 다 전달되지 않은 경우 (전체 조회)
            allEvents = eventRepository.findAllByOrderByCreatedAtDesc();
        }

        return EventResponseConverter.toEventInfoDTOList(allEvents);
    }

    /**
     * 주어진 id에 해당하는 공지사항(Event)의 상세 정보를 조회합니다.
     * 존재하지 않는 공지사항일 경우, EventException(EVENT_NOT_FOUND)가 발생합니다.
     *
     * @param id 조회할 공지사항의 PK
     * @return 공지사항 상세 정보(제목, 내용, 시작일, 종료일, 생성일자)와 공지사항에 게시된 이미지 목록을 포함한 DTO
     * @throws EventException 해당 ID의 공지사항이 존재하지 않을 경우 발생
     */
    @Override
    public EventResponse.EventInfoDetailDTO findEventDetails(Long id) {
        Event event = eventRepository.findByIdWithImages(id)
                .orElseThrow(() -> new EventException(ErrorStatus.EVENT_NOT_FOUND));

        return EventResponseConverter.toEventInfoDetailDTO(event);
    }
}
