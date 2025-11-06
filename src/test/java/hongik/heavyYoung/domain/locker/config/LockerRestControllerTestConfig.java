package hongik.heavyYoung.domain.locker.config;

import hongik.heavyYoung.domain.locker.service.general.LockerQueryService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class LockerRestControllerTestConfig {

    @Bean
    public LockerQueryService lockerQueryService() {
        return Mockito.mock(LockerQueryService.class);
    }
}
