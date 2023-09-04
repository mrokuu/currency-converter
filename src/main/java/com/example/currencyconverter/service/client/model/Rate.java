package com.example.currencyconverter.service.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {

    @JsonProperty("mid")
    private String mid;

    @JsonProperty("code")
    private String code;
}
