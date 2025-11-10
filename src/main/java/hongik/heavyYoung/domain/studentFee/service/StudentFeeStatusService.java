package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.domain.studentFee.entity.StudentFee;
import hongik.heavyYoung.domain.studentFee.repository.StudentFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentFeeStatusService {
    private final StudentFeeRepository studentFeeRepository;

    public boolean isStudentFeePaid(Member member) {
        if (member.getStudentFeeStatus() == StudentFeeStatus.YET) {
            StudentFee studentFee = studentFeeRepository.findByStudentId(member.getStudentId());

            member.updateStudentFeeStatus(studentFee.isFeePaid()
                    ? StudentFeeStatus.PAID
                    : StudentFeeStatus.NOT_PAID
            );
        }

        return member.getStudentFeeStatus().equals(StudentFeeStatus.PAID);
    }
}
