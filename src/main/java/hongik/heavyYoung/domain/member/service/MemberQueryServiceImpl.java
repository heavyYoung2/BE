package hongik.heavyYoung.domain.member.service;

import hongik.heavyYoung.domain.member.converter.MemberConverter;
import hongik.heavyYoung.domain.member.dto.MemberResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    /**
     * 사용자의 블랙리스트 여부와, 블랙리스트 기간을 반환하는 메서드
     * @return MemberResponseDTO.BlacklistInfo
     */
    @Override
    public MemberResponseDTO.BlacklistInfo getBlacklist() {

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        LocalDate blacklistUntil = member.getBlacklistUntil();
        boolean blacklisted = blacklistUntil != null && blacklistUntil.isAfter(LocalDate.now());

        return MemberConverter.toBlacklistInfo(blacklisted, blacklistUntil);
    }
}
