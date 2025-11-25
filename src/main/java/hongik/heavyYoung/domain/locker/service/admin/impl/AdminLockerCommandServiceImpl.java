package hongik.heavyYoung.domain.locker.service.admin.impl;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.application.entity.MemberApplication;
import hongik.heavyYoung.domain.application.enums.ApplicationType;
import hongik.heavyYoung.domain.application.repository.ApplicationRepository;
import hongik.heavyYoung.domain.application.repository.MemberApplicationRepository;
import hongik.heavyYoung.domain.locker.converter.LockerConverter;
import hongik.heavyYoung.domain.locker.dto.LockerRequestDTO;
import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.locker.service.admin.AdminLockerCommandService;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
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
public class AdminLockerCommandServiceImpl implements AdminLockerCommandService {

    private final ApplicationRepository applicationRepository;
    private final LockerRepository lockerRepository;
    private final LockerAssignmentRepository lockerAssignmentRepository;
    private final MemberApplicationRepository memberApplicationRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addLockerApplication(LockerRequestDTO.LockerApplicationAddRequestDTO lockerApplicationAddRequestDTO) {
        // 현재 진행중인 사물함 신청이 있는 경우 생성 불가
        if (applicationRepository.findActiveLockerApplications(LocalDateTime.now(), ApplicationType.LOCKER).isPresent()) {
            throw new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_ENDED);
        }

        int lockerApplicationCanCount = lockerRepository.countByLockerStatus(LockerStatus.AVAILABLE);

        Application lockerApplication = LockerConverter.toLockerApplication(lockerApplicationAddRequestDTO, lockerApplicationCanCount);

        applicationRepository.save(lockerApplication);
    }

    @Override
    public void returnCurrentSemesterLockers() {
        lockerAssignmentRepository.updateAllByCurrentSemesterFalse();
        lockerRepository.updateAllInUseToAvailable();
    }

    @Override
    public void finishLockerApplication(Long lockerApplicationId) {
        Application lockerApplication = applicationRepository.findById(lockerApplicationId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_FOUND));
        lockerApplication.updateCanApplyToFalse();
    }

    @Override
    public void changeLockerNotAvailable(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_NOT_FOUND));

        LockerStatus currentLockerStatus = locker.getLockerStatus();

        switch (currentLockerStatus) {
            case AVAILABLE -> {
            }
            case IN_USE -> {
                LockerAssignment lockerAssignment = lockerAssignmentRepository
                        .findByLocker_IdAndIsCurrentSemesterTrue(lockerId)
                        .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_ASSIGNMENT_NOT_FOUND));

                lockerAssignment.updateIsCurrentSemesterToFalse();
            }
            case CANT_USE -> throw new LockerException(ErrorStatus.LOCKER_ALREADY_CANT_USE);
        }

        locker.updateLockerStatus(LockerStatus.CANT_USE);
    }

    @Override
    public void changeLockerAvailable(Long lockerId) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_NOT_FOUND));

        LockerStatus currentLockerStatus = locker.getLockerStatus();

        switch (currentLockerStatus) {
            case AVAILABLE -> throw new LockerException(ErrorStatus.LOCKER_ALREADY_AVAILABLE);
            case IN_USE -> {
                LockerAssignment lockerAssignment = lockerAssignmentRepository
                        .findByLocker_IdAndIsCurrentSemesterTrue(lockerId)
                        .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_ASSIGNMENT_NOT_FOUND));

                lockerAssignment.updateIsCurrentSemesterToFalse();
            }
            case CANT_USE -> {
            }
        }

        locker.updateLockerStatus(LockerStatus.AVAILABLE);
    }

    @Override
    public void changeLockerUsing(Long lockerId, String studentId) {
        Locker locker = lockerRepository.findById(lockerId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_NOT_FOUND));

        LockerStatus currentLockerStatus = locker.getLockerStatus();

        switch (currentLockerStatus) {
            case AVAILABLE -> {
                Member member = memberRepository.findByStudentId(studentId)
                        .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

                String currentSemester = lockerAssignmentRepository.findCurrentSemester()
                        .orElseThrow(() -> new LockerException(ErrorStatus.CURRENT_SEMESTER_NOT_FOUND));

                LockerAssignment lockerAssignment = LockerConverter.toLockerAssignmentForLockerAdditional(member, locker, currentSemester);
                lockerAssignmentRepository.save(lockerAssignment);

                locker.updateLockerStatus(LockerStatus.IN_USE);
            }
            case IN_USE, CANT_USE -> throw new LockerException(ErrorStatus.LOCKER_SHOULD_BE_AVAILABLE);
        }

    }


    @Override
    public void assignLockersByApplication(Long lockerApplicationId) {
        // 사물함 신청 정보 가져오기
        Application lockerApplication = applicationRepository.findById(lockerApplicationId)
                .orElseThrow(() -> new LockerException(ErrorStatus.LOCKER_APPLICATION_NOT_FOUND));

        if(lockerApplication.isCanApply()) {
            throw new LockerException(ErrorStatus.LOCKER_APPLICATION_SHOULD_BE_ENDED);
        }

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
            LockerAssignment assignment = LockerConverter.toLockerAssignmentForLockerMain(ma, locker, currentSemester);

            // 저장
            lockerAssignmentRepository.save(assignment);
        }

        lockerApplication.updateCanAssignToFalse();
    }
}
