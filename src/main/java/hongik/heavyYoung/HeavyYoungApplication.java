package hongik.heavyYoung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HeavyYoungApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeavyYoungApplication.class, args);
    }

}
