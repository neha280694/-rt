package com.example.myapplication;

public interface IWeatherCallbackListener<T> {
    <Y> void getWeatherData(Y weatherModel, Boolean success, String errorMsg);
}
