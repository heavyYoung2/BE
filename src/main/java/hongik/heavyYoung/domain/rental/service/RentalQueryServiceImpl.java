package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.rental.converter.RentalConverter;
import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import hongik.heavyYoung.domain.rental.enums.RentalStatus;
import hongik.heavyYoung.domain.rental.repository.ItemRentalHistoryRepository;
import hongik.heavyYoung.domain.studentFee.service.StudentFeeStatusService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import hongik.heavyYoung.global.qr.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RentalQueryServiceImpl implements RentalQueryService {

    private final MemberRepository memberRepository;
    private final QrManager qrManager;
    private final StudentFeeStatusService studentFeeStatusService;
    private final ItemRentalHistoryRepository itemRentalHistoryRepository;


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

    /**
     * 대여 현황 조회
     * @return RentalResponseDTO.MemberRentalInfo
     */
    @Override
    public RentalResponseDTO.MemberRentalInfo getRentalStatus(Long memberId) {

        // 멤버 정보 받아오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        // 반납하지 않은 대여 내역 리스트
        List<ItemRentalHistory> itemRentalHistories = itemRentalHistoryRepository.findCurrentWithItemAndCategory(member);

        // 대여 정보들
        List<RentalResponseDTO.RentalHistory> rentalItemInfos = new ArrayList<>();
        for (ItemRentalHistory itemRentalHistory : itemRentalHistories) {
            RentalStatus rentalStatus = resolveStatus(itemRentalHistory, LocalDateTime.now());
            RentalResponseDTO.RentalHistory rentalHistory = RentalConverter.toRentalHistory(itemRentalHistory, rentalStatus);
            rentalItemInfos.add(rentalHistory);
        }

        // TODO: 블랙리스트 예상 기간 계산 로직
        LocalDate expectedBlacklistUntil = null;

        return RentalConverter.toRentalInfo(expectedBlacklistUntil, rentalItemInfos);
    }

    @Override
    public RentalResponseDTO.AllRentalHistories getRentalHistory() {

        // 멤버 정보 받아오기
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        // 대여 정보 가져오기
        List<ItemRentalHistory> itemRentalHistoryList = itemRentalHistoryRepository.findAllByMemberOrderByCreatedAtDesc(member);

        // 대여 정보들
        List<RentalResponseDTO.RentalHistory> rentalHistories = new ArrayList<>();
        for (ItemRentalHistory itemRentalHistory : itemRentalHistoryList) {
            RentalStatus rentalStatus = resolveStatus(itemRentalHistory, LocalDateTime.now());
            RentalResponseDTO.RentalHistory rentalHistory = RentalConverter.toRentalHistory(itemRentalHistory, rentalStatus);
            rentalHistories.add(rentalHistory);
        }

        return RentalConverter.toAllRentalHistories(rentalHistories);
    }

    // 대여 상태 계산
    private RentalStatus resolveStatus(ItemRentalHistory itemRentalHistory, LocalDateTime now) {
        if (itemRentalHistory.getReturnedAt() != null) {
            return RentalStatus.RETURNED;
        } else if (itemRentalHistory.getExpectedReturnAt().isBefore(now)) {
            return RentalStatus.OVERDUE;
        } else {
            return RentalStatus.RENTING;
        }
    }
}
