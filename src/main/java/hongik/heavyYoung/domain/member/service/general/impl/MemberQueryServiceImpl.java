package hongik.heavyYoung.domain.member.service.general.impl;

import hongik.heavyYoung.domain.locker.dto.LockerResponse;
import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import hongik.heavyYoung.domain.member.converter.MemberConverter;
import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.member.service.general.MemberQueryService;
import hongik.heavyYoung.domain.rental.dto.RentalResponseDTO;
import hongik.heavyYoung.domain.rental.service.general.RentalQueryService;
import hongik.heavyYoung.domain.studentFee.service.general.impl.StudentFeeStatusService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final LockerQueryService lockerQueryService;
    private final RentalQueryService rentalQueryService;
    private final StudentFeeStatusService studentFeeStatusService;

    private final MemberRepository memberRepository;

    /**
     * 사용자의 블랙리스트 여부와, 블랙리스트 기간을 반환하는 메서드
     * @return MemberResponseDTO.BlacklistInfo
     */
    @Override
    public MemberResponseDTO.BlacklistInfo getBlacklist(Long authMemberId) {

        Member member = memberRepository.findById(authMemberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        LocalDate blacklistUntil = member.getBlacklistUntil();
        boolean blacklisted = blacklistUntil != null && blacklistUntil.isAfter(LocalDate.now());

        return MemberConverter.toBlacklistInfo(blacklisted, blacklistUntil);
    }

    @Override
    public MemberResponseDTO.MyPageInfo findMyPage(Long authMemberId) {
        Member member = memberRepository.findById(authMemberId)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        LockerResponse.MyLockerInfoDTO locker = lockerQueryService.findMyLocker(authMemberId);
        List<RentalResponseDTO.RentalHistory> items = rentalQueryService.getRentalStatus(authMemberId).getItems();
        boolean isStudentFeePaid = studentFeeStatusService.isStudentFeePaid(member);
        MemberResponseDTO.BlacklistInfo blacklist = getBlacklist(authMemberId);

        return MemberConverter.toMyPageInfo(locker, items, isStudentFeePaid, blacklist);
    }
}
