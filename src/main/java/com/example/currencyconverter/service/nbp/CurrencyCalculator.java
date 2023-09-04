package com.example.currencyconverter.service.nbp;

import com.example.currencyconverter.model.Currencies;
import com.example.currencyconverter.service.client.model.Rate;
import com.example.currencyconverter.service.client.model.Rates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyCalculator {

    public BigDecimal calculateAmount(BigDecimal amount, Currencies fromCurrency, Currencies toCurrency, Rates ratesList) {
        final List<Rate> rates = ratesList.getRates().stream().filter(rate -> isFromOrToCurrency(fromCurrency, toCurrency, rate)).collect(Collectors.toList());
        if (isOneCurrencyPln(rates)) {
            return calculateForPln(amount, toCurrency, rates);
        }

        return BigDecimal.valueOf(4.49);
    }

    private boolean isOneCurrencyPln(List<Rate> rates) {
        return rates.size() <= 2;
    }

    private BigDecimal calculateForPln(BigDecimal amount, Currencies toCurrency, List<Rate> rates) {
        String value = rates.get(0).getMid();
        BigDecimal money = new BigDecimal(value.replaceAll(",", ""));

        if (ifDestinationCurrencyIsPln(toCurrency)) {
            return divideAmountByRate(amount, money);
        }
        return multiplyAmountByRate(amount, money);
    }

    private BigDecimal multiplyAmountByRate(BigDecimal amount, BigDecimal money) {
        return amount.multiply(money).setScale(4, RoundingMode.DOWN);
    }

    private BigDecimal divideAmountByRate(BigDecimal amount, BigDecimal money) {
        return amount.divide(money, RoundingMode.DOWN).setScale(4, RoundingMode.DOWN);
    }

    private boolean ifDestinationCurrencyIsPln(Currencies toCurrency) {
        return toCurrency.name().equals(Currencies.PLN.name());
    }

    private boolean isFromOrToCurrency(Currencies fromCurrency, Currencies toCurrency, Rate rate) {
        return fromCurrency.name().equals(rate.getCode()) || toCurrency.name().equals(rate.getCode());
    }
}

