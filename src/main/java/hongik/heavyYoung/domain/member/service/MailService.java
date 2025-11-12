package hongik.heavyYoung.domain.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @Value("{app.mail.from}")
    private String from;

    public void sendVerificationCode(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject("[회비영] 학교 이메일 인증번호");
            helper.setText(buildHtml(code), true); // HTML 본문
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 전송 중 오류가 발생했습니다.", e);
        }
    }

    private String buildHtml(String code) {
        return """
            <div style="font-family: Pretendard, Apple SD Gothic Neo, sans-serif; line-height:1.6">
              <h2>회비영 이메일 인증</h2>
              <p>아래 인증번호를 입력해 주세요.</p>
              <div style="font-size:24px;font-weight:700;letter-spacing:6px;margin:16px 0">
                %s
              </div>
              <p style="color:#666">이 메일은 발신 전용입니다.</p>
            </div>
            """.formatted(code);
    }


}
