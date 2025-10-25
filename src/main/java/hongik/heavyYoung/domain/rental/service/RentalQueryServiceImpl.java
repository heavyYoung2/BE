package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.studentFee.service.StudentFeeStatusService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import hongik.heavyYoung.global.qr.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RentalQueryServiceImpl implements RentalQueryService {

    private final MemberRepository memberRepository;
    private final QrManager qrManager;
    private final StudentFeeStatusService studentFeeStatusService;


    @Override
    public QrTokenResponse generateRentalQrToken(Long itemCategoryId) {

        // 멤버 정보 받아오기
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        // 회비 납부 여부 갱신
        boolean feePaid = studentFeeStatusService.isStudentFeePaid(member);

        // 블랙리스트 확인
        LocalDate blacklistUntil = member.getBlacklistUntil();
        boolean blacklisted = blacklistUntil != null && !blacklistUntil.isBefore(LocalDate.now());

        Map<String, Object> context = Map.of(
            "memberId", member.getId(),
            "studentId", member.getStudentId(),
            "studentName", member.getStudentName(),
            "feePaid", feePaid,
            "blacklisted", blacklisted,
            "itemCategoryId", itemCategoryId
    );

        // qrToken 생성
        String qrToken = qrManager.generateQrToken(QrType.RENTAL, context);

        // 응답 반환
        return QrConverter.toQrTokenResponse(qrToken, feePaid);
    }

    @Override
    public QrTokenResponse generateReturnRentalQrToken(Long rentalHistoryId) {

        // 멤버 정보 받아오기
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        // 반납 아이디
        Map<String, Object> context = Map.of(
                "memberId", member.getId(),
                "studentId", member.getStudentId(),
                "studentName", member.getStudentName(),
                "rentalHistoryId", rentalHistoryId
        );

        // qr 토큰 생성
        String qrToken = qrManager.generateQrToken(QrType.RETURN_ITEM, context);

        return QrConverter.toQrTokenResponse(qrToken, true);
    }
}
