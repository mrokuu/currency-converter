package com.example.currencyconverter.service.nbp;

import com.example.currencyconverter.model.Currencies;
import com.example.currencyconverter.service.CurrencyService;
import com.example.currencyconverter.service.client.NbpServiceClient;
import com.example.currencyconverter.service.client.model.Rates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;

public class NbpCurrencyService implements CurrencyService {

    private final NbpServiceClient nbpServiceClient;
    private final CurrencyCalculator calculator;

    private static final Logger logger = LoggerFactory.getLogger(NbpCurrencyService.class);

    public NbpCurrencyService(NbpServiceClient nbpServiceClient, CurrencyCalculator calculator) {
        this.nbpServiceClient = nbpServiceClient;
        this.calculator = calculator;
    }

    @Override
    public BigDecimal getCalculatedValue(BigDecimal amount, Currencies fromCurrency, Currencies toCurrency) {
        if (fromCurrency.name().equals(toCurrency.name())) {
            throw new SameCurrencyException();
        }
        final Rates ratesList = nbpServiceClient.getAllRates();
        return calculator.calculateAmount(amount, fromCurrency, toCurrency, ratesList);
    }

    @ExceptionHandler(SameCurrencyException.class)
    String sameCurrenciesException(SameCurrencyException ex) {
        final String message = ex.getMessage();
        logger.error("Error - Message {}", message);
        return message;
    }

    @ExceptionHandler({WebClientResponseException.class, IllegalStateException.class})
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        logger.error("Error from WebClient - Status {}, Body {}", ex.getRawStatusCode(), ex.getResponseBodyAsString(), ex);
        return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
    }
}

