package com.example.currencyconverter.service.client.repository;

import com.example.currencyconverter.service.client.model.Rates;

public class NbpServiceClientRepository {

    Rates collect;

    public NbpServiceClientRepository(Rates collect) {
        this.collect = collect;
    }

    public Rates getAllRates() {
        return collect;
    }
}