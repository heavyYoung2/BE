package hongik.heavyYoung.domain.locker.service.admin.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import hongik.heavyYoung.domain.locker.converter.LockerResponseConverter;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerQueryService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminLockerQueryServiceImpl implements AdminLockerQueryService {

    private final ApplicationRepository applicationRepository;
    private final MemberApplicationRepository memberApplicationRepository;
    private final LockerAssignmentRepository lockerAssignmentRepository;

    @Override
    public List<LockerResponse.LockerApplicationInfoDTO> findAllLockerApplication() {
        List<Application> lockerApplications = applicationRepository.findAllByApplicationTypeInOrderByCreatedAtDesc(ApplicationType.LOCKER);
        return LockerResponseConverter.toLockerApplicationInfoDTOList(lockerApplications);
    }

    @Override
    public LockerResponse.LockerApplicationDetailInfoDTO findLockerApplicationDetail(Long lockerApplicationId) {
        Application lockerApplication = applicationRepository.findById(lockerApplicationId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_FOUND));

        List<MemberApplication> memberApplications = memberApplicationRepository.findAllByApplicationIdOrderByCreatedAtAsc(lockerApplicationId);

        return LockerResponseConverter.toLockerApplicationDetailInfoDTO(lockerApplication, memberApplications);
    }

    @Override
    public List<String> findLockerAssignSemesters() {
        return applicationRepository.findAllSemestersByApplicationTypeInOrderBySemesterDesc(ApplicationType.LOCKER);
    }

    @Override
    public List<LockerResponse.LockerAssignmentInfoDTO> findLockerAssignments(String semesterOrNull) {
        String semester = resolveSemesterOrLatest(semesterOrNull);
        List<LockerAssignment> lockerAssignments = lockerAssignmentRepository.findAllBySemesterWithMemberAndLocker(semester);
        return LockerResponseConverter.toLockerAssignmentInfoDTOList(lockerAssignments);
    }

    /**
    * semester 값이 null 일 경우 가장 최신 학기를 가져오는 로직
    * */
    private String resolveSemesterOrLatest(String semesterOrNull) {
        if (semesterOrNull != null && !semesterOrNull.isBlank()) {
            return semesterOrNull;
        }

        return applicationRepository.findFirstByApplicationTypeInOrderByApplicationSemesterDesc(ApplicationType.LOCKER)
                .map(Application::getApplicationSemester)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_FOUND));
    }

}
