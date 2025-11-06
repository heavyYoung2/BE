package hongik.heavyYoung.domain.locker.service.admin.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.locker.dto.LockerRequest;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminLockerCommandServiceImpl implements AdminLockerCommandService {

    private final ApplicationRepository applicationRepository;
    private final LockerRepository lockerRepository;

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
}
