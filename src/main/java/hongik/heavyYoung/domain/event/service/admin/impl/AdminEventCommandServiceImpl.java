package hongik.heavyYoung.domain.event.service.admin.impl;

import hongik.heavyYoung.domain.event.command.CreateEventCommand;
import hongik.heavyYoung.domain.event.command.UpdateEventCommand;
import hongik.heavyYoung.domain.event.converter.EventResponseConverter;
import hongik.heavyYoung.domain.event.dto.EventRequestDTO;
import hongik.heavyYoung.domain.event.dto.EventResponseDTO;
import hongik.heavyYoung.domain.event.entity.Event;
import hongik.heavyYoung.domain.event.entity.EventImage;
import hongik.heavyYoung.domain.event.repository.EventImageRepository;
import hongik.heavyYoung.domain.event.repository.EventRepository;
import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.EventException;
import hongik.heavyYoung.global.s3.S3Manager;
import hongik.heavyYoung.global.s3.S3UploadResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminEventCommandServiceImpl implements AdminEventCommandService {

    private final EventRepository eventRepository;
    private final S3Manager s3Manager;
    private final EventImageRepository eventImageRepository;

    /**
     * 새로운 공지사항(Event)을 생성합니다.
     *
     * @param eventAddRequestDTO 공지사항 생성에 필요한 제목, 내용, 행사 시작/종료일 정보를 담은 DTO
     * @return 생성된 공지사항의 PK를 담은 응답 DTO
     */
    @Override
    public EventResponseDTO.EventAddResponseDTO createEvent(EventRequestDTO.EventAddRequestDTO eventAddRequestDTO, List<MultipartFile> multipartFiles) {
        CreateEventCommand createEventCommand = CreateEventCommand.from(eventAddRequestDTO);

        Event event = Event.create(createEventCommand);

        eventRepository.save(event);

        saveEventImages(event, multipartFiles);

        return EventResponseConverter.toEventAddResponseDTO(event);
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

        deleteEventImages(event);

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
    public EventResponseDTO.EventPutResponseDTO updateEvent(Long eventId, EventRequestDTO.EventPutRequestDTO eventPutRequestDTO, List<MultipartFile> multipartFiles) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorStatus.EVENT_NOT_FOUND));
        event.update(UpdateEventCommand.from(eventPutRequestDTO));

        if (multipartFiles != null) {
            deleteEventImages(event);
            saveEventImages(event, multipartFiles);
        }

        return EventResponseConverter.toEventPutResponseDTO(event);
    }

    private void saveEventImages(Event event, List<MultipartFile> multipartFiles) {
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return;
        }

        int sortOrder = 1;

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile == null || multipartFile.isEmpty()) {
                continue;
            }

            S3UploadResult result = s3Manager.upload(multipartFile);
            String key = result.key();
            String url = result.url();

            EventImage eventImage = EventImage.builder()
                    .event(event)
                    .eventImageKey(key)
                    .eventImageUrl(url)
                    .sortOrder(sortOrder++)
                    .build();

            eventImageRepository.save(eventImage);
        }
    }

    private void deleteEventImages(Event event) {
        List<EventImage> eventImages = eventImageRepository.findAllByEvent(event);

        for (EventImage eventImage : eventImages) {
            s3Manager.delete(eventImage.getEventImageKey());
        }

        eventImageRepository.deleteAllByEvent(event);
    }
}
