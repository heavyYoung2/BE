package hongik.heavyYoung.domain.locker.service.admin.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import hongik.heavyYoung.domain.locker.dto.LockerRequest;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminLockerCommandServiceImpl implements AdminLockerCommandService {

    private final ApplicationRepository applicationRepository;
    private final LockerRepository lockerRepository;
    private final LockerAssignmentRepository lockerAssignmentRepository;
    private final MemberApplicationRepository memberApplicationRepository;

    @Override
    public void addLockerApplication(LockerRequest.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO) {

        int lockerApplicationCanCount = lockerRepository.countByLockerStatus(LockerStatus.AVAILABLE);

        Application lockerApplication = Application.builder()
                .applicationCanCount(lockerApplicationCanCount)
                .applicationStartAt(lockerApplicationAddRequestDTO.getApplicationStartAt())
                .applicationEndAt(lockerApplicationAddRequestDTO.getApplicationEndAt())
                .applicationSemester(lockerApplicationAddRequestDTO.getSemester())
                .applicationType(lockerApplicationAddRequestDTO.getApplicationType())
                .canAssign(lockerApplicationAddRequestDTO.getApplicationType() != ApplicationType.LOCKER_ADDITIONAL)
                .build();

        applicationRepository.save(lockerApplication);
    }

    @Override
    public void returnCurrentSemesterLockers() {
        lockerAssignmentRepository.updateAllByCurrentSemesterFalse();
        lockerRepository.updateAllInUseToAvailable();
    }

    @Override
    public void assignLockersByApplication(Long lockerApplicationId) {
        // 사물함 신청 정보 가져오기
        Application lockerApplication = applicationRepository.findById(lockerApplicationId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_FOUND));

        if(!lockerApplication.isCanAssign()) {
            throw new LockerException(ErrorStatus.CAN_NOT_ASSIGN);
        }

        // 사물함 신청 내역 가져오기
        List<MemberApplication> memberApplications = memberApplicationRepository.findAllByApplicationIdOrderByCreatedAtAsc(lockerApplicationId);
        if(memberApplications.isEmpty()) {
            return;
        }

        // AVAILABLE 사물함 가져오기
        List<Locker> availableLockers = lockerRepository.findAllAvailableForUpdate();
        if(availableLockers.isEmpty()) {
            throw new LockerException(ErrorStatus.NO_AVAILABLE_LOCKER);
        }


        // 사물함 배정
        String currentSemester = lockerApplication.getApplicationSemester();
        int lockerIdx = 0;

        for (MemberApplication ma : memberApplications) {
            if (lockerIdx >= availableLockers.size()) {
                break;
            }

            // AVAILABLE 사물함 가져오기
            Locker locker = availableLockers.get(lockerIdx++);

            // 사물함 상태 변경
            locker.updateLockerStatus(LockerStatus.IN_USE);

            // 배정 엔티티 생성
            LockerAssignment assignment = LockerAssignment.builder()
                    .member(ma.getMember())
                    .locker(locker)
                    .assignSemester(currentSemester)
                    .isCurrentSemester(true)
                    .build();

            // 저장
            lockerAssignmentRepository.save(assignment);
        }

        lockerApplication.updateCanAssignToFalse();
    }
}
