package com.example.currencyconverter.service.nbp;

import com.example.currencyconverter.model.Currencies;
import com.example.currencyconverter.service.client.model.Rate;
import com.example.currencyconverter.service.client.model.Rates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyCalculator {

    public static final String REGEX = ",";
    public static final String REPLACEMENT = "";
    public static final int TWO_CURRENCIES = 2;
    public static final int BIG_DECIMAL_SCALE = 4;

    public BigDecimal calculateAmount(BigDecimal amount, Currencies fromCurrency, Currencies toCurrency, Rates ratesList) {
        final List<Rate> rates = getRates(fromCurrency, toCurrency, ratesList);

        if (isOneCurrencyPln(rates)) {
            return getAmountForPln(amount, toCurrency, rates);
        }
        return getAmountForForeignCurrencies(fromCurrency, toCurrency, rates).orElseThrow(IllegalStateException::new);
    }

    private List<Rate> getRates(Currencies fromCurrency, Currencies toCurrency, Rates ratesList) {
        return ratesList.getRates()
                .stream()
                .filter(rate -> isFromOrToCurrency(fromCurrency, toCurrency, rate))
                .collect(Collectors.toList());
    }

    private Optional<BigDecimal> getAmountForForeignCurrencies(Currencies fromCurrency, Currencies toCurrency, List<Rate> rates) {
        final Optional<Rate> fromRate = rates.stream().filter(rate -> rate.getCode().equals(fromCurrency.name())).findFirst();
        final Optional<Rate> toRate = rates.stream().filter(rate -> rate.getCode().equals(toCurrency.name())).findFirst();

        if (fromRate.isPresent() && toRate.isPresent()) {
            final BigDecimal parsedFromRate = parseToBigDecimal(fromRate.get().getMid());
            final BigDecimal parsedToRate = parseToBigDecimal(toRate.get().getMid());
            return Optional.of(parsedFromRate.divide(parsedToRate, RoundingMode.HALF_UP));
        }
        return Optional.empty();
    }

    private String getFirstRate(List<Rate> rates) {
        return rates.get(0).getMid();
    }

    private BigDecimal parseToBigDecimal(String value) {
        return new BigDecimal(value.replaceAll(REGEX, REPLACEMENT)).setScale(4, RoundingMode.HALF_UP);
    }

    private boolean isOneCurrencyPln(List<Rate> rates) {
        return rates.size() < TWO_CURRENCIES;
    }

    private BigDecimal getAmountForPln(BigDecimal amount, Currencies toCurrency, List<Rate> rates) {
        String value = getFirstRate(rates);
        BigDecimal money = parseToBigDecimal(value);
        if (ifDestinationCurrencyIsNotPln(toCurrency)) {
            return divideAmountByRate(amount, money);
        }
        return multiplyAmountByRate(amount, money);
    }

    private BigDecimal multiplyAmountByRate(BigDecimal amount, BigDecimal money) {
        return amount.multiply(money).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal divideAmountByRate(BigDecimal amount, BigDecimal money) {
        return amount.setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP).divide(money, RoundingMode.HALF_UP).setScale(BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    private boolean ifDestinationCurrencyIsNotPln(Currencies toCurrency) {
        return !toCurrency.name().equals("PLN");
    }

    private boolean isFromOrToCurrency(Currencies fromCurrency, Currencies toCurrency, Rate rate) {
        return fromCurrency.name().equals(rate.getCode()) || toCurrency.name().equals(rate.getCode());
    }
}