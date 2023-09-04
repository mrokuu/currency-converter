package com.example.currencyconverter.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import com.example.currencyconverter.model.Currencies;
import com.example.currencyconverter.service.client.NbpServiceClient;
import com.example.currencyconverter.service.client.model.Rate;
import com.example.currencyconverter.service.client.model.Rates;
import com.example.currencyconverter.service.nbp.CurrencyCalculator;
import com.example.currencyconverter.service.nbp.NbpCurrencyService;
import com.example.currencyconverter.service.nbp.SameCurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class NbpRatesServiceTest {

    private final NbpServiceClient client = Mockito.mock(NbpServiceClient.class);
    private final CurrencyCalculator calculator = new CurrencyCalculator();
    NbpCurrencyService nbpCurrencyService = new NbpCurrencyService(client, calculator);

    @Test
    public void fromEURtoPLN() {
        // given
        final Currencies from = Currencies.EUR;
        final Currencies to = Currencies.PLN;
        final BigDecimal givenAmount = BigDecimal.valueOf(1.0);
        final BigDecimal expectedAmount = BigDecimal.valueOf(4.4832);
        when(client.getAllRates()).thenReturn(getMockedRates());
        // when
        final BigDecimal calculatedValue = nbpCurrencyService.getCalculatedValue(givenAmount, from, to);
        // then
        assertEquals(expectedAmount, calculatedValue);
    }

    @Test
    public void fromPLNtoEUR() {
        // given
        final Currencies from = Currencies.PLN;
        final Currencies to = Currencies.EUR;
        final BigDecimal givenAmount = BigDecimal.valueOf(1.0);
        final BigDecimal expectedAmount = BigDecimal.valueOf(0.2231);
        when(client.getAllRates()).thenReturn(getMockedRates());
        // when
        final BigDecimal calculatedValue = nbpCurrencyService.getCalculatedValue(givenAmount, from, to);
        // then
        assertEquals(expectedAmount, calculatedValue);
    }

    @Test
    public void fromEURtoEUR() {
        // given
        final Currencies from = Currencies.EUR;
        final Currencies to = Currencies.EUR;
        final BigDecimal givenAmount = BigDecimal.valueOf(1.0);
        when(client.getAllRates()).thenReturn(getMockedRates());
        // when
        Assertions.assertThrows(SameCurrencyException.class, () -> {
            nbpCurrencyService.getCalculatedValue(givenAmount, from, to);
        });
    }

    @Test
    public void fromCHFtoEUR() {
        // given
        final Currencies from = Currencies.CHF;
        final Currencies to = Currencies.EUR;
        final BigDecimal givenAmount = BigDecimal.valueOf(1.0);
        final BigDecimal expectedAmount = BigDecimal.valueOf(0.9269);
        when(client.getAllRates()).thenReturn(getMockedRates());
        // when
        final BigDecimal calculatedValue = nbpCurrencyService.getCalculatedValue(givenAmount, from, to);
        // then
        assertEquals(expectedAmount, calculatedValue);
    }

    @Test
    public void fromCHFtoEURWhenAmountEqual10() {
        // given
        final Currencies from = Currencies.CHF;
        final Currencies to = Currencies.EUR;
        final BigDecimal givenAmount = BigDecimal.valueOf(10.0);
        final BigDecimal expectedAmount = BigDecimal.valueOf(9.2690).setScale(4, RoundingMode.HALF_UP);
        when(client.getAllRates()).thenReturn(getMockedRates());
        // when
        final BigDecimal calculatedValue = nbpCurrencyService.getCalculatedValue(givenAmount, from, to);
        // then
        assertEquals(expectedAmount, calculatedValue);
    }

    private Rates getMockedRates() {
        final ArrayList<Rate> arrayList = new ArrayList<>();
        arrayList.add(new Rate("4.4832", "EUR"));
        arrayList.add(new Rate("4.1555", "CHF"));
        return new Rates(arrayList);
    }
}
