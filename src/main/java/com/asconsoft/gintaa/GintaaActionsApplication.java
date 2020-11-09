package com.asconsoft.gintaa;

import com.asconsoft.gintaa.cache.EnableGintaaCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableGintaaCache
@EnableFeignClients
public class GintaaActionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GintaaActionsApplication.class, args);
    }


}
