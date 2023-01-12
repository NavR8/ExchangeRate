package com.exchangeCurrency.ExchangeRates.view;

import com.exchangeCurrency.ExchangeRates.Convert.CurrencyConverterD;
import com.exchangeCurrency.ExchangeRates.CurrencyEn.Currency;
import com.exchangeCurrency.ExchangeRates.api.CurrencyApi;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route("")
public class PageView extends VerticalLayout {

    private VerticalLayout mainLayout;
    private NumberField amountForExchange;
    private Button convertButton;
    private final CurrencyApi currencyApi;
    private Select<Currency> currencyFrom;
    private Select<Currency> currencyTo;
    private NumberField result;
    public PageView(CurrencyApi currencyApi){
        this.currencyApi = currencyApi;

        mainLayout();
        setHeader();
        setCurrency();

        convertButton.addClickListener(buttonClickEvent -> {
            if(amountForExchange.getValue() != null){
                getConversionResult(amountForExchange,currencyTo.getValue(),currencyFrom.getValue());
            }
            else{
                Notification notification = Notification.show("Please enter an amount", 1500,Notification.Position.TOP_CENTER);
            }
        });
    }

    private void getConversionResult(NumberField amountForExchange, Currency to, Currency from){
        CurrencyConverterD currencyConverter = currencyApi.getCurrencyConverter(to,from,amountForExchange.getValue());

        result.setValue(currencyConverter.getResult());
    }

    private void mainLayout(){
        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        add(mainLayout);
    }

    private void setHeader(){
        HorizontalLayout headerL = new HorizontalLayout();
        headerL.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        headerL.add(new H2("Exchange Rates"));
        mainLayout.add(headerL);
    }

    private void setCurrency(){
        HorizontalLayout horizontalL = new HorizontalLayout();
        horizontalL.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        horizontalL.setSpacing(true);
        horizontalL.setMargin(true);

        currencyFrom = new Select<>();
        currencyFrom.setLabel("Your Currency");
        currencyFrom.setWidth("35%");
        currencyFrom.setItems(Currency.values());
//        currencyFrom.setValue(Currency.USD);

        //to change
        amountForExchange = new NumberField();
        amountForExchange.setStep(0.01);
        amountForExchange.setWidth("60%");

        horizontalL.add(currencyFrom,amountForExchange);

        HorizontalLayout horizontalL2 = new HorizontalLayout();
        horizontalL2.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        currencyTo = new Select<>();
        currencyTo.setLabel("Exchange Currency");
        currencyTo.setWidth("35%");
        currencyTo.setItems(Currency.values());
//        currencyTo.setValue(Currency.EUR);

        //swap currency button
        Button swapButton = new Button(new Icon(VaadinIcon.REFRESH));
        swapButton.addClickListener(change ->{
            Currency currency = currencyFrom.getValue();
            currencyFrom.setValue(currencyTo.getValue());
            currencyTo.setValue(currency);
        });

        //Result-conversion
        result = new NumberField();
        result.setWidth("60%");
        result.setStep(0.0001);

        horizontalL2.add(currencyTo, result);

        //Button for conversion
        convertButton = new Button("Convert!");
        convertButton.setWidth("15%");
        convertButton.setHeight("20%");

        convertButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        convertButton.getStyle().set("margin-top","55px");
        mainLayout.add(horizontalL,swapButton,horizontalL2, convertButton);

    }

}
