package com.exchangeCurrency.ExchangeRates.Convert;

import com.exchangeCurrency.ExchangeRates.CurrencyEn.Currency;
import lombok.Data;

@Data
public class ConverterD {
    private Currency from;
    private Currency to;
    private Long amount;
}
