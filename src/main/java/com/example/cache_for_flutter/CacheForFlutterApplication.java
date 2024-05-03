package com.example.cache_for_flutter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CacheForFlutterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheForFlutterApplication.class, args);
    }
}
