package br.unicamp.sindo.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan
@Configuration
public class CatalogCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogCoreApplication.class, args);
    }
}
