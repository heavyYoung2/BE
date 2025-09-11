package hongik.heavyYoung.domain.event.config;

import hongik.heavyYoung.domain.event.service.admin.AdminEventCommandService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AdminEventRestControllerTestConfig {

    @Bean
    public AdminEventCommandService adminEventCommandService() { return Mockito.mock(AdminEventCommandService.class); }
}
