package hongik.heavyYoung.global.jwt;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt.auth")
public class JwtAuthProperties {
    private String secret;
    private long accessExpSeconds;
    private long refreshExpSeconds;
}
