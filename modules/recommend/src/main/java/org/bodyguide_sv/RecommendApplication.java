package org.bodyguide_sv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class RecommendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoupangApplication.class, args);
        log.info("==================== BODYGUIDE Recommend Service Started! ====================");
    }
}