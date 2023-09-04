package com.example.currencyconverter.controller;

import com.example.currencyconverter.model.Currencies;
import com.example.currencyconverter.service.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    final private CurrencyService currencyService;


    @RequestMapping("/currency")
    public @ResponseBody
    BigDecimal currency(@RequestParam BigDecimal amount,
                        @RequestParam Currencies fromCurrency,
                        @RequestParam Currencies toCurrency) {
        return currencyService.getCalculatedValue(amount, fromCurrency, toCurrency);
    }

}
