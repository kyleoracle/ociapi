package com.oracle.sehub.appdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");
        System.setProperty("server.servlet.context-path", "/");
        SpringApplication.run(Application.class, args);
    }
}
