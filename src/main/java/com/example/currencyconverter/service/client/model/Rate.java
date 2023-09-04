package com.example.currencyconverter.service.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rate {

    @JsonProperty("mid")
    private String mid;

    @JsonProperty("code")
    private String code;
}
