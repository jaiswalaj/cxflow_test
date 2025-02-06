package com.example.demo;

import com.example.demo.service.VaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private VaultService vaultService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 3) {
            System.out.println("Usage: java -jar vaultdemo.jar <operation> <path> <key> <value>");
            System.out.println("<operation> can be 'store' or 'retrieve'.");
            return;
        }

        String operation = args[0];
        String path = args[1];
        String key = args[2];
        String value = (args.length > 3) ? args[3] : null;

        if ("store".equalsIgnoreCase(operation) && value != null) {
            vaultService.storeSecret(path, key, value);
        } else if ("retrieve".equalsIgnoreCase(operation)) {
            String secret = vaultService.getSecret(path, key);
            System.out.println("Secret Retrieved: " + secret);
        } else {
            System.out.println("Invalid Operation. Use 'store' or 'retrieve'.");
        }
    }
}
