package com.webRTCService.webRTCService;

import com.webRTCService.webRTCService.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase (com.webRTCService.webRTCService.repositories.EmloyeeRepository repository){
        return args -> {
            log.info("Preloading: " + repository.save(new Employee("Bilbo Beggins", "burglar")));
            log.info("Preloading: " + repository.save(new Employee("Froddo Beggins", "thief ")));
        };
    }

}
