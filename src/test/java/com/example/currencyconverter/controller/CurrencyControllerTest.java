package com.example.currencyconverter.controller;


import com.example.currencyconverter.service.nbp.SameCurrencyException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RatesControllerTest {


    private MockMvc mockMvc;

    @Test
    public void shouldReturn_ClientOk_WhenRequestParametersAreGiven() throws Exception {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("amount", "1");
        params.add("fromCurrency", "EUR");
        params.add("toCurrency", "PLN");
        mockMvc.perform(get("/currency").params(params)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturn_ClientError_WhenRequestParametersNotGiven() throws Exception {
        mockMvc.perform(get("/currency")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn_ClientError_WhenCurriencesAreTheSame() throws Exception {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("amount", "10");
        params.add("fromCurrency", "EUR");
        params.add("toCurrency", "EUR");
        mockMvc.perform(get("/currency")
                        .params(params))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SameCurrencyException));
    }

    @Test
    public void shouldReturn_Client400Error_When_OneOfTheRequiredRequestParametersIsMissing() throws Exception {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.add("amount", "10");
        params.add("toCurrency", "EUR");
        mockMvc.perform(get("/currency").params(params)).andDo(print()).andExpect(status().isBadRequest());
    }
}