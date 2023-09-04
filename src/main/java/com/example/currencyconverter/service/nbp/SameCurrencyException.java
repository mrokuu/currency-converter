package com.example.currencyconverter.service.nbp;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameCurrencyException extends RuntimeException {

    private static final long serialVersionUID = 1236996191743114729L;

    SameCurrencyException() {
        super();
    }
}
