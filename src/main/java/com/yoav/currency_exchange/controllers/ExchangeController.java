package com.yoav.currency_exchange.controllers;

import com.yoav.currency_exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/money")
@RequiredArgsConstructor
public class ExchangeController {
    private final ExchangeService exchangeService;

    @GetMapping("/convert/from/{fromCurrency}/to/{toCurrency}/amount/{amount}")
    public ResponseEntity<?> getValue(@PathVariable String fromCurrency,@PathVariable String toCurrency,@PathVariable double amount){
        return new ResponseEntity<>(exchangeService.readJason(fromCurrency,toCurrency,amount), HttpStatus.ACCEPTED);
    }

}
