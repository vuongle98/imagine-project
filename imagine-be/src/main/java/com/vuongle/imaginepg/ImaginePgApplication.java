package com.vuongle.imaginepg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
public class ImaginePgApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ImaginePgApplication.class, args);
    }

}
