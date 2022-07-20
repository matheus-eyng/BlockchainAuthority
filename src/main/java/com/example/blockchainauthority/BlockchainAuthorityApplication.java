package com.example.blockchainauthority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlockchainAuthorityApplication {

    private static Logger logger = LoggerFactory.getLogger(BlockchainAuthorityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BlockchainAuthorityApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            logger.info("INITIALIZING AUTHORITY");
            CertificationAuthority ca = new CertificationAuthority();
        };
    }
}
