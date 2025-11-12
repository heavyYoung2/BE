package hongik.heavyYoung.domain.rental.service;

import hongik.heavyYoung.domain.item.entity.Item;
import hongik.heavyYoung.domain.item.entity.ItemCategory;
import hongik.heavyYoung.domain.item.repository.ItemCategoryRepository;
import hongik.heavyYoung.domain.item.repository.ItemRepository;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.rental.dto.RentalRequestDTO;
import hongik.heavyYoung.domain.rental.entity.ItemRentalHistory;
import hongik.heavyYoung.domain.rental.repository.ItemRentalHistoryRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import hongik.heavyYoung.global.exception.customException.ItemCategoryException;
import hongik.heavyYoung.global.exception.customException.ItemRentalHistoryException;
import hongik.heavyYoung.global.exception.customException.MemberException;
import hongik.heavyYoung.global.exception.customException.RentalException;
import hongik.heavyYoung.global.qr.QrManager;
import hongik.heavyYoung.global.qr.QrType;
import hongik.heavyYoung.global.qr.payload.RentalPayload;
import hongik.heavyYoung.global.qr.payload.ReturnPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RentalAdminCommandServiceImpl implements RentalAdminCommandService {

    private final QrManager qrManager;
    private final ItemRentalHistoryRepository itemRentalHistoryRepository;
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public void rentalByQr(RentalRequestDTO.QrToken rentalQrToken) {

        String qrToken = rentalQrToken.getQrToken();
        RentalPayload qrPayload = (RentalPayload) qrManager.decodeQrToken(QrType.RENTAL, qrToken);

        // 블랙리스트인지 확인
        if (qrPayload.isBlacklisted()) {
            throw new RentalException(ErrorStatus.MEMBER_IS_BLACKLIST);
        }

        // 회비 납부 여부 확인
        if (!qrPayload.isFeePaid()) {
            throw new RentalException(ErrorStatus.MEMBER_NOT_PAID);
        }

        // 대여중인 물품 리스트 가져오기
        List<ItemRentalHistory> rentingList = itemRentalHistoryRepository.findAllByMemberIdAndReturnedAtIsNull(qrPayload.getMemberId());

        // 현재 대여 중 연체 내역, 동일 종류 물품 대여 내역 확인
        for (ItemRentalHistory itemRentalHistory : rentingList) {
            if (itemRentalHistory.getExpectedReturnAt().isBefore(LocalDateTime.now())) {
                throw new RentalException(ErrorStatus.MEMBER_HAS_OVERDUE_ITEM);
            } else if (itemRentalHistory.getItem().getItemCategory().getId().equals(qrPayload.getItemCategoryId())) {
                throw new RentalException(ErrorStatus.MEMBER_ALREADY_RENTED_SAME_CATEGORY);
            }
        }

        // 해당 itemCategoryId를 통해, item에서 대여 가능한 상품, 오름차순으로 돌며 하나 빌려주기
        Item item = itemRepository.findFirstByItemCategoryIdAndIsRentedFalseOrderByIdAsc(qrPayload.getItemCategoryId())
                        .orElseThrow(() -> new RentalException(ErrorStatus.ITEM_QUANTITY_NON_POSITIVE));
        item.updateIsRented(true);

        // 해당 itemCategory의 수량 변경
        ItemCategory itemCategory = itemCategoryRepository.findById(qrPayload.getItemCategoryId())
                .orElseThrow(() -> new ItemCategoryException(ErrorStatus.ITEM_CATEGORY_NOT_FOUND));
        itemCategory.decreaseQuantity();

        // 멤버 받아오기
        Member member = memberRepository.findById(qrPayload.getMemberId())
                        .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        // itemRentalHistory 생성 및 저장
        ItemRentalHistory itemRentalHistory = ItemRentalHistory.builder()
                .item(item)
                .member(member)
                .rentalStartAt(LocalDateTime.now())
                .expectedReturnAt(LocalDateTime.now().plusDays(1))
                .build();
        itemRentalHistoryRepository.save(itemRentalHistory);
    }

    @Override
    public void returnByQr(RentalRequestDTO.QrToken request) {

        String qrToken = request.getQrToken();
        ReturnPayload qrPayload = (ReturnPayload) qrManager.decodeQrToken(QrType.RETURN_ITEM, qrToken);

        Member member = memberRepository.findById(qrPayload.getMemberId())
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        ItemRentalHistory itemRentalHistory = itemRentalHistoryRepository.findById(qrPayload.getRentalHistoryId())
                .orElseThrow(() -> new ItemRentalHistoryException(ErrorStatus.ITEM_RENTAL_HISTORY_NOT_FOUND));

        // 이미 반납한 물품 예외 처리
        if (itemRentalHistory.getReturnedAt() != null) {
            throw new RentalException(ErrorStatus.ALREADY_RETURN);
        }

        // itemRentalHistory에 returnedAt 기록
        itemRentalHistory.updateReturnedAt(LocalDateTime.now());

        // item 상태 변경
        itemRentalHistory.getItem().updateIsRented(false);

        // itemCategory 수량 + 1
        itemRentalHistory.getItem().getItemCategory().increaseQuantity();

        // if (expectedReturnAt.before(returnedAt)) 이면 블랙리스트 체크
        if (itemRentalHistory.getExpectedReturnAt().isBefore(LocalDateTime.now())) {
            member.updateBlacklistUntil(LocalDate.now().plusDays(1));
        }
    }
}
