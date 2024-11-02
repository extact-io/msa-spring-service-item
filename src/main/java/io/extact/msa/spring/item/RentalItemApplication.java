package io.extact.msa.spring.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.extact.msa.spring.platform.fw.RmsFrameworkConfig;

@SpringBootApplication
@Import(RmsFrameworkConfig.class)
public class RentalItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentalItemApplication.class, args);
    }
}
