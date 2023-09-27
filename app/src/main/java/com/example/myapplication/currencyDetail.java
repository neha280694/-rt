package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class currencyDetail {
    public String currency;
    private double convertedValue;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(double convertedValue) {
        this.convertedValue = convertedValue;
    }
}
