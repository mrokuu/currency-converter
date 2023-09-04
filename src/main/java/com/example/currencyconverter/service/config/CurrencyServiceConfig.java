package com.example.currencyconverter.service.config;

import com.example.currencyconverter.service.nbp.NbpCurrencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyServiceConfig {
    @Bean
    public NbpCurrencyService nbpCurrencyService(){
        return null;
    }
}
