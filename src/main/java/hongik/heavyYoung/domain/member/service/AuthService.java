package hongik.heavyYoung.domain.member.service;

import hongik.heavyYoung.domain.member.converter.AuthConverter;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.authDTO.AuthResponseDTO;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.global.apiPayload.status.ErrorStatus;
import hongik.heavyYoung.global.exception.GeneralException;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;

    @Transactional
    public AuthResponseDTO.AuthSignUpResponseDTO signUp(AuthRequestDTO.AuthSignUpRequestDTO authRequestDTO) {
        // 학교 이메일인지 검증
//        if(authRequestDTO.getEmail() == null || isSchoolEmail(authRequestDTO.getEmail())) {
//            throw new GeneralException(ErrorStatus.INVALID_EMAIL);
//        }

        // 이미 회원인지 검증
        if(memberRepository.existsByEmail(authRequestDTO.getEmail())) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_EXIST);
        }
        // 비밀번호가 일치하는지 검증
        if(!(authRequestDTO.getPassword()).equals(authRequestDTO.getPasswordConfirm())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        // 멤버 추가
        Member member = AuthConverter.toMemberEntity(authRequestDTO);
        memberRepository.save(member);

        return  AuthConverter.toAuthSignUpResponseDTO(member);
    }

    private boolean isSchoolEmail(String email) {
        if(email == null) {
            return false;
        }
        String e = email.trim().toLowerCase(Locale.ROOT);
        if (!e.endsWith("@g.hongik.ac.kr")) return false;
        int at = e.lastIndexOf('@');
        return at > 0;
    }
}
