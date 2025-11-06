package hongik.heavyYoung.domain.locker.service.general.strategy.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.general.strategy.LockerApplicationStrategy;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LockerAdditionalApplicationStrategy implements LockerApplicationStrategy {

    private final MemberApplicationRepository memberApplicationRepository;
    private final LockerRepository lockerRepository;
    private final LockerAssignmentRepository lockerAssignmentRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public boolean supports(Application lockerApplication) {
        return lockerApplication.getApplicationType() == ApplicationType.LOCKER_ADDITIONAL;
    }

    @Override
    public void apply(Member member, Application lockerApplication) {
        // 이미 신청한 이력이 있는지 확인
        boolean alreadyApplied =
                memberApplicationRepository.existsByMemberAndApplication(member, lockerApplication);

        if (alreadyApplied) {
            throw new LockerException(ErrorStatus.ALREADY_APPLIED);
        }

        // 신청 내역 저장
        MemberApplication memberApplication = MemberApplication
                .builder()
                .member(member)
                .application(lockerApplication)
                .build();

        memberApplicationRepository.save(memberApplication);

        // 배정할 AVAILABLE 사물함 가져오기
        Locker locker = lockerRepository.findFirstByLockerStatus(LockerStatus.AVAILABLE)
                .orElseThrow(() -> new LockerException(ErrorStatus.NO_AVAILABLE_LOCKER));

        // 사물함 배정
        LockerAssignment lockerAssignment = LockerAssignment.builder()
                .member(member)
                .locker(locker)
                .assignSemester(lockerApplication.getApplicationSemester())
                .build();
        lockerAssignmentRepository.save(lockerAssignment);

        // 배정한 사물함 상태 변경
        locker.updateLockerStatus(LockerStatus.IN_USE);

        // 해당 사물함 신청의 인원수 증가
        applicationRepository.incrementApplicationMemberCount(lockerApplication.getId());
    }
}
