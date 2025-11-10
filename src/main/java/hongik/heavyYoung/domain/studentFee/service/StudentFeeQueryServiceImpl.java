package hongik.heavyYoung.domain.studentFee.service;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.exception.customException.MemberException;
import hongik.heavyYoung.global.qr.QrConverter;
import hongik.heavyYoung.global.qr.QrManager;
import hongik.heavyYoung.global.qr.QrTokenResponse;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional//(readOnly = true)
@RequiredArgsConstructor
public class StudentFeeQueryServiceImpl implements StudentFeeQueryService {

    private final MemberRepository memberRepository;
    private final QrManager qrManager;
    private final StudentFeeStatusService studentFeeStatusService;

    /**
     * 사용자의 학생회비 납부 여부가 포함된 QrToken 생성한다. </br>
     * 1. 회원 정보를 조회한다</br>
     * 2. 회원의 납부 상태가 미확정(YET)인 경우, DB를 조회해 상태를 갱신</br>
     * 3. qrManger를 통해, STUDENT_FEE 타입이 담긴 토큰을 생성</br>
     * 4. 토큰과 납부 여부를 응답 DTO로 변환해 반환</br>
     *
     *
     * @return {@link QrTokenResponse}
     * @throws MemberException 회원이 존재하지 않는 경우
     */
    @Override
    public QrTokenResponse generateStudentFeeQrToken() {

        // 멤버 정보 받아오기
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        // 회비 납부 여부 갱신
        boolean feePaid = studentFeeStatusService.isStudentFeePaid(member);

        // qrPayload에 들어갈 내용 생성
        Map<String, Object> context = Map.of(
                "memberId", member.getId(),
                "studentId", member.getStudentId(),
                "feePaid", feePaid
        );

        // qrToken 생성
        String qrToken = qrManager.generateQrToken(QrType.STUDENT_FEE, context);

        // 응답 반환
        return QrConverter.toQrTokenResponse(qrToken, feePaid);
    }

}
