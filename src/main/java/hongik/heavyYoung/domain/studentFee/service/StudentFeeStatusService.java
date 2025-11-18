package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.domain.studentFee.entity.StudentFee;
import hongik.heavyYoung.domain.studentFee.repository.StudentFeeRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentFeeStatusService {
    private final StudentFeeRepository studentFeeRepository;

    public boolean isStudentFeePaid(Member member) {
        if (member.getStudentFeeStatus() != StudentFeeStatus.PAID) {
            StudentFee studentFee = studentFeeRepository.findByStudentId(member.getStudentId())
                    .orElseThrow(() -> new MemberException(ErrorStatus.STUDENT_FEE_NOT_FOUND)
            );

            member.updateStudentFeeStatus(studentFee.isFeePaid()
                    ? StudentFeeStatus.PAID
                    : StudentFeeStatus.NOT_PAID
            );
        }

        return member.getStudentFeeStatus().equals(StudentFeeStatus.PAID);
    }
}
