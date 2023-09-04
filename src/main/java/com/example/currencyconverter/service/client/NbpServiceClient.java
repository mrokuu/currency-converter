package com.example.currencyconverter.service.client;

import com.example.currencyconverter.service.client.model.Rates;
import com.example.currencyconverter.service.client.repository.NbpServiceClientRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NbpServiceClient {

    private static final String BASE_URL = "http://api.nbp.pl/api/exchangerates/tables/A/";

    private final WebClient webClient;
    private NbpServiceClientRepository nbpServiceClientRepository;

    public NbpServiceClient() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Rates getAllRates() {
        if (nbpServiceClientRepository != null) {
            return nbpServiceClientRepository.getAllRates();
        }
        final Rates first = getAllExchangeRates().stream().findFirst().orElseThrow(() -> new IllegalStateException("Not found"));
        this.nbpServiceClientRepository = new NbpServiceClientRepository(first);
        return first;
    }

    private List<Rates> getAllExchangeRates() {
        final Rates[] exchangeRatesResponse = getResponse();
        assert exchangeRatesResponse != null;
        return Arrays.stream(exchangeRatesResponse).collect(Collectors.toList());
    }

    private Rates[] getResponse() {
        return webClient.get()
                .retrieve()
                .bodyToMono(Rates[].class)
                .block();
    }
}

