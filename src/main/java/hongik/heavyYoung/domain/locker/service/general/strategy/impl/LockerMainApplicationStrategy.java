package hongik.heavyYoung.domain.locker.service.general.strategy.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import hongik.heavyYoung.domain.locker.service.general.strategy.LockerApplicationStrategy;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LockerMainApplicationStrategy implements LockerApplicationStrategy {

    private final MemberApplicationRepository memberApplicationRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public boolean supports(Application lockerApplication) {
        return lockerApplication.getApplicationType() == ApplicationType.LOCKER_MAIN;
    }

    @Override
    public void apply(Member member, Application lockerApplication) {
        // 해당 학기에 이미 신청한 이력이 있는지 확인
        boolean alreadyApplied = memberApplicationRepository.existsByMemberAndApplication_ApplicationSemesterAndApplication_ApplicationTypeIn(member, lockerApplication.getApplicationSemester(), ApplicationType.LOCKER);

        if (alreadyApplied) {
            throw new LockerException(ErrorStatus.ALREADY_APPLIED);
        }

        // 신청 내역 저장
        MemberApplication memberApplication = MemberApplication.builder()
                .application(lockerApplication)
                .member(member)
                .applicationSemester(lockerApplication.getApplicationSemester())
                .build();
        memberApplicationRepository.save(memberApplication);

        // 해당 사물함 신청의 인원수 증가
        applicationRepository.incrementApplicationMemberCount(lockerApplication.getId());
    }
}
