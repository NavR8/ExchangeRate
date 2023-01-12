package com.exchangeCurrency.ExchangeRates.api;

import com.exchangeCurrency.ExchangeRates.Convert.CurrencyConverterD;
import com.exchangeCurrency.ExchangeRates.CurrencyEn.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class CurrencyApi {

    private final RestTemplate restTemplate;
    private static final String URL = "https://api.apilayer.com/currency_data/convert?";
    @Value("${api.key}")
    private String apiKey;

    public CurrencyConverterD getCurrencyConverter(Currency currencyTo, Currency currencyFrom, Double amount) {
        String url = URL + "to=" + currencyTo + "&from=" + currencyFrom + "&amount=" + amount + "&apikey=" + apiKey;

        ResponseEntity<CurrencyConverterD> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }
}
