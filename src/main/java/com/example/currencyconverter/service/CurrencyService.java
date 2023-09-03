package com.example.currencyconverter.service;

import com.example.currencyconverter.model.Currencies;
import org.springframework.stereotype.Service;

@Service
public interface CurrencyService {

    String getCalculatedValue(String amount, Currencies fromCurrency, Currencies toCurrency);
}
