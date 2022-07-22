package com.example.blockchainauthority;

import com.example.blockchainauthority.contract.BlockchainService;
import com.example.blockchainauthority.contract.EthereumConnection;
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
    public CommandLineRunner commandLineRunner(ApplicationContext context, Controller controller, BlockchainService service) {
        final String csr = "-----BEGIN CERTIFICATE REQUEST-----\n" +
                "MIIC3jCCAcYCAQAwgZgxCzAJBgNVBAYTAkJSMRcwFQYDVQQIDA5TYW50YSBDYXRh\n" +
                "cmluYTEWMBQGA1UEBwwNRmxvcmlhbm9wb2xpczEcMBoGA1UECgwTRGVmYXVsdCBD\n" +
                "b21wYW55IEx0ZDEWMBQGA1UEAwwNSm9hbyBkYSBTaWx2YTEiMCAGCSqGSIb3DQEJ\n" +
                "ARYTam9hb3NpbHZhQGVtYWlsLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC\n" +
                "AQoCggEBAPCqQYNCLh2uDR0x/JVSTxQ77W7w4fB+ya2bm/C+SkCQHv/EfCIXONbF\n" +
                "JAMASI3U4EeVri1UpssiOJMUb2VKtazIfGA8ukHhgtJkA4kAfedQiPynmk682SiE\n" +
                "+Pb+VS9az/JRyNZv9CbyX6fyxG/IzlD77ZeIFo8DYNqUveNUQkBTnu8Y/xl5xmd5\n" +
                "g4EIWMOc5sNhqUU12LFP7i5jyat9hhgCfecXGZ/vygLV4TxmACN6fqzPrVN+UGIX\n" +
                "SCywNZxVz8IQll5uu0OXyOxn4DkFDpYQ7DBJywOXZ+GP5V4zm7X8NzlzAI/UVW90\n" +
                "q9qgPzDVt2uIhqU0554/UGHO+DC3gr8CAwEAAaAAMA0GCSqGSIb3DQEBCwUAA4IB\n" +
                "AQDe5bN2ljUDCGXiKomrxGKC+szTXfwMP0z+g1lSe9NAEjiS1RUnBS10CXrNR1Mq\n" +
                "rZ/tQafYzjYvAczMlhNu2GKHytkMQXO/vW+cTMYwlGOIzliwEckwaMactAPxQ4WP\n" +
                "aVdrlAuZE1kl8986/x2wwXChjvtNxnMDtItLfflZd6W3c+be8qr7xAJuwBigSibA\n" +
                "kjvYnnwax7+KXmQ2jPuNU9rADKIIfLqRnCJsqxEAMiTQZ08HuK8IY58nDI3Go68O\n" +
                "C1G/pdkoW9xZA598LPxflKo+qK9vjUlaPO3g8LN7AyqzIjwpgt6w+YlVcNfgdHtp\n" +
                "6os4yvn6vO5rUPl4L2VU15Zk\n" +
                "-----END CERTIFICATE REQUEST-----";

        return args -> {
            logger.info("ISSUING PRE CERTIFICATE");
            controller.issueCertificate(csr);

            service.init();
            service.deployPersonRegistryContract();
            service.deployLogContract();
            service.deployCrlContract();

            System.exit(0);
        };
    }
}
