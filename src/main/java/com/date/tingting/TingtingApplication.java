package com.date.tingting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
@EnableSwagger2
public class TingtingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TingtingApplication.class, args);
    }

}
