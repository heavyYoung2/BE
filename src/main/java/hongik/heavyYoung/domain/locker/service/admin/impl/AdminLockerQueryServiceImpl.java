package hongik.heavyYoung.domain.locker.service.admin.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.locker.converter.LockerResponseConverter;
import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminLockerQueryServiceImpl implements AdminLockerQueryService {

    private final ApplicationRepository applicationRepository;

    @Override
    public List<LockerResponse.LockerApplicationInfoDTO> findAllLockerApplication() {
        List<Application> lockerApplications = applicationRepository.findAllByApplicationTypeIn(ApplicationType.LOCKER);
        return LockerResponseConverter.toLockerApplicationInfoDTOList(lockerApplications);
    }
}
