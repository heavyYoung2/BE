package hongik.heavyYoung.domain.member.service.general;

import hongik.heavyYoung.domain.member.dto.AuthRequestDTO;
import hongik.heavyYoung.domain.member.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO.SignUpResponseDTO signUp(AuthRequestDTO.AuthSignUpRequestDTO authRequestDTO);
    AuthResponseDTO.LoginResponseDTO login(AuthRequestDTO.AuthLoginRequestDTO req);
    void logout(Long memberId);
    AuthResponseDTO.SendCodeResponseDTO issueSchoolEmailCode(AuthRequestDTO.SendCodeRequestDTO dto);
    AuthResponseDTO.VerifyCodeResponseDTO verifySchoolEmailCode(AuthRequestDTO.VerifyCodeRequestDTO dto);
    AuthResponseDTO.TempPasswordResponseDTO issueTemporaryPassword(String email);
}
