package hongik.heavyYoung.domain.event.config;

import hongik.heavyYoung.domain.event.service.EventQueryService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EventRestControllerTestConfig {

    @Bean
    public EventQueryService eventQueryService() {
        return Mockito.mock(EventQueryService.class);
    }
}