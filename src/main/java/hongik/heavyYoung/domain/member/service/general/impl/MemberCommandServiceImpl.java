package hongik.heavyYoung.domain.member.service.general.impl;

import hongik.heavyYoung.domain.member.converter.AuthConverter;
import hongik.heavyYoung.domain.member.dto.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.AuthResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.member.service.general.MemberCommandService;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.customException.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO.ChangePasswordResponseDTO changePassword(Long authMemberId, AuthRequestDTO.ChangePasswordRequestDTO dto) {
        Member member = memberRepository.findById(authMemberId).orElseThrow(() -> new AuthException(ErrorStatus.MEMBER_NOT_FOUND));


        if(!passwordEncoder.matches(dto.getOriginPassword(), member.getPassword())) {
            throw new AuthException(ErrorStatus.INVALID_PASSWORD);
        }

        if(!(dto.getNewPassword().equals(dto.getNewPasswordConfirm()))) {
            throw new AuthException(ErrorStatus.PASSWORD_CONFIRM_NOT_MATCH);
        }

        if(dto.getOriginPassword().equals(dto.getNewPassword())) {
            throw new AuthException(ErrorStatus.PASSWORD_ALREADY_USED);
        }

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));

        return AuthConverter.toChangePasswordResponseDTO(member);
    }
}
