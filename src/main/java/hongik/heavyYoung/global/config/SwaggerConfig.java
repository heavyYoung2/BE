package hongik.heavyYoung.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger(OpenAPI) 설정 클래스.
 *
 * <p>Spring Boot 애플리케이션에서 Swagger UI를 통해
 * API 명세를 자동으로 생성하고 확인할 수 있도록
 * OpenAPI 객체를 구성합니다.</p>
 *
 * <ul>
 *   <li>문서 Info: 제목, 설명, 버전</li>
 *   <li>Server: 로컬 서버 URL</li>
 *   <li>SecurityRequirement: JWT 인증 요구사항</li>
 *   <li>SecurityScheme: Bearer JWT 스킴 정의</li>
 *   <li>Components: 보안 스킴을 등록</li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // 문서 정보
        Info info = new Info()
                .title("회비영 Server API")
                .description("회비영 API 명세서")
                .version("1.0.0");

        // 서버 정보
        Server server = new Server()
                .url("http://localhost:8080")
                .description("로컬");

        // 보안 요구사항
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("JWT_TOKEN");

        // 보안 스킴 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .name("JWT_TOKEN")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // 컴포넌트에 보안 스킴 등록
        Components components = new Components()
                .addSecuritySchemes("JWT_TOKEN", securityScheme);

        // OpenAPI 문서 객체
        return new OpenAPI()
                .info(info)
                .addServersItem(server)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
