package com.example.currencyconverter.service.config;

import com.example.currencyconverter.service.CurrencyService;
import com.example.currencyconverter.service.client.NbpServiceClient;
import com.example.currencyconverter.service.nbp.CurrencyCalculator;
import com.example.currencyconverter.service.nbp.NbpCurrencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyServiceConfig {

    @Bean
    protected CurrencyService nbpCurrencyService() {
        return new NbpCurrencyService(new NbpServiceClient(), new CurrencyCalculator());
    }
}
