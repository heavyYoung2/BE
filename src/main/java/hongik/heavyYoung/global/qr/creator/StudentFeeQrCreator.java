package hongik.heavyYoung.global.qr.creator;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.studentFee.repository.StudentFeeRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import hongik.heavyYoung.global.qr.payload.StudentFeeQrPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentFeeQrCreator implements QrPayloadCreator {
    private final MemberRepository memberRepository;
    private final StudentFeeRepository studentFeeRepository;

    @Override
    public QrType getType() {
        return QrType.STUDENT_FEE;
    }

    @Override
    public QrPayload create(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        String studentId = member.getStudentId();
        StudentFeeStatus studentFeeStatus = refreshStudentFeeStatus(member);
        boolean feeStatus = studentFeeStatus.equals(StudentFeeStatus.PAID);

        return StudentFeeQrPayload.builder()
                .qrType(QrType.STUDENT_FEE)
                .memberId(memberId)
                .studentId(studentId)
                .feeStatus(feeStatus)
                .build();
    }

    private StudentFeeStatus refreshStudentFeeStatus(Member member) {
        if (member.getStudentFeeStatus() == StudentFeeStatus.YET) {
            member.updateStudentFeeStatus(
                    studentFeeRepository.existsByStudentId(member.getStudentId())
                            ? StudentFeeStatus.PAID
                            : StudentFeeStatus.NOT_PAID
            );
        }

        return member.getStudentFeeStatus();
    }
}
