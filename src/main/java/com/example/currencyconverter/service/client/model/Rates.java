package com.example.currencyconverter.service.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Rates {

    @JsonProperty("rates")
    private List<Rate> rates;
}
