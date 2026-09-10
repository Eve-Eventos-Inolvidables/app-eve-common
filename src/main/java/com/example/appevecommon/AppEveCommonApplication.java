package com.example.appevecommon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
// By moment, ignore jpa, no db yet
@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class})
public class AppEveCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppEveCommonApplication.class, args);
    }

    @Bean
    public CommandLineRunner ejecutarAlArranque(TestEnvVars testvars) {
        return args -> {
            testvars.printSecret();
        };
    }
}
