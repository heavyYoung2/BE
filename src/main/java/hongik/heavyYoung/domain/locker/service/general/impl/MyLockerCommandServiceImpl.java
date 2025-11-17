package hongik.heavyYoung.domain.locker.service.general.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.locker.service.general.MyLockerCommandService;
import hongik.heavyYoung.domain.locker.service.general.strategy.LockerApplicationStrategy;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.studentFee.service.StudentFeeStatusService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.LockerException;
import hongik.heavyYoung.global.exception.customException.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyLockerCommandServiceImpl implements MyLockerCommandService {

    private final MemberRepository memberRepository;
    private final ApplicationRepository applicationRepository;
    private final List<LockerApplicationStrategy> lockerApplicationStrategies;

    private final StudentFeeStatusService studentFeeStatusService;

    @Override
    public void applyLocker(Long memberId) {
        // 신청하려는 학생 찾기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new LockerException(ErrorStatus.MEMBER_NOT_FOUND));

        // 학생회비 미납 시 사물함 신청 불가
        boolean studentFeePaid = studentFeeStatusService.isStudentFeePaid(member);
        if (!studentFeePaid) {
            throw new MemberException(ErrorStatus.MEMBER_NOT_PAID);
        }

        // 신청 가능한 사물함 신청 찾기
        Application activeLockerApplication = applicationRepository.findActiveLockerApplications(LocalDateTime.now(), ApplicationType.LOCKER)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_OPENED));

        // 사물함 신청 전략 탐색(본 신청, 추가신청)
        LockerApplicationStrategy lockerApplicationStrategy = lockerApplicationStrategies.stream()
                .filter(s -> s.supports(activeLockerApplication))
                .findFirst()
                .orElseThrow(() -> new LockerException(ErrorStatus.NO_LOCKER_STRATEGY));

        // 전략에 맞는 사물함 신청
        lockerApplicationStrategy.apply(member, activeLockerApplication);
    }
}
