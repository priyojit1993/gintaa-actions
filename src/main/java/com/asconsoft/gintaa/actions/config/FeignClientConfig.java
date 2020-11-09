package com.asconsoft.gintaa.actions.config;

import feign.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Profile("!local")
@Configuration
@Slf4j
public class FeignClientConfig {
    @Bean
    public Client feignClient() {
        log.info("<<<<<<<<<<<<<<<<< excluding ribbon from feign config >>>>>>>>>>>>>>>>>>>");
        return new Client.Default(null, null);
    }
}
