package com.vuongle.imaginepg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImaginePgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImaginePgApplication.class, args);
    }

}
