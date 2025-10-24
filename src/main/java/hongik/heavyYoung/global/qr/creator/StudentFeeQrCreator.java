package hongik.heavyYoung.global.qr.creator;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.QrPayload;
import hongik.heavyYoung.global.qr.payload.StudentFeeQrPayload;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentFeeQrCreator implements QrPayloadCreator {
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    public QrType getType() {
        return QrType.STUDENT_FEE;
    }

    @Override
    public QrPayload create(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        String studentId = member.getStudentId();
        boolean feePaid = member.getStudentFeeStatus() == StudentFeeStatus.PAID;

        return StudentFeeQrPayload.builder()
                .qrType(QrType.STUDENT_FEE)
                .memberId(memberId)
                .studentId(studentId)
                .feePaid(feePaid)
                .build();
    }

    @Override
    public QrPayload createFromClaims(Claims claims) {
        return objectMapper.convertValue(claims, StudentFeeQrPayload.class);
    }
}
