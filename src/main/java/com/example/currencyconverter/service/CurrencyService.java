package com.example.currencyconverter.service;

import com.example.currencyconverter.model.Currencies;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CurrencyService {

    BigDecimal getCalculatedValue(BigDecimal amount, Currencies fromCurrency, Currencies toCurrency);
}
