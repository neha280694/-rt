package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;



public class weatherforcast extends AppCompatActivity implements IWeatherCallbackListener {
    TextView tvCity;
    TextView tvCountry;
    Button btnGet5DaysWeather;
    ImageView ivWeatherIcon;
    RecyclerView rvWeatherData;
    AutoCompleteTextView etCityName;
    String OPEN_WEATHER_APP_ID = "b317aca2e83ad16e219ff2283ca837d5";
    String ACCU_WEATHER_APP_ID = "87ad516d1d4842838fcfebe843d933b1";
    LocationSearchModel mLocationSearchModel;
    Button btnGetCurrentWeather;
    TextView tvWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherforcast);
        tvCity=findViewById(R.id.tv_city);
        tvCountry =findViewById(R.id.tv_country);
        btnGet5DaysWeather=findViewById(R.id.btn_get_5_days_weather);
        ivWeatherIcon=findViewById(R.id.iv_weather_icon);
        rvWeatherData=findViewById(R.id.rv_weather_data);
        etCityName=findViewById(R.id.et_city_name);
        btnGetCurrentWeather=findViewById(R.id.btn_get_weather);
        tvWeather=findViewById(R.id.tv_info);
        etCityName.setThreshold(2);
        etCityName.setAdapter(new AutoCompleteAdapter(weatherforcast.this, ACCU_WEATHER_APP_ID));
        rvWeatherData.setLayoutManager(new LinearLayoutManager(this));
        btnGetCurrentWeather.setOnClickListener(view -> WeatherConditions.getOpenWeatherData(etCityName.getText().toString(), OPEN_WEATHER_APP_ID, weatherforcast.this));
        btnGet5DaysWeather.setOnClickListener(view -> WeatherConditions.getOpenWeatherData5Days(etCityName.getText().toString(), OPEN_WEATHER_APP_ID, weatherforcast.this));
        etCityName.setOnItemClickListener((adapterView, view, i, l) -> {
            mLocationSearchModel = (LocationSearchModel) adapterView.getAdapter().getItem(i);
            etCityName.setText(mLocationSearchModel.getLocalizedName());
            WeatherConditions.getAccuWeatherData(mLocationSearchModel.getKey(), ACCU_WEATHER_APP_ID, weatherforcast.this, true);
            WeatherConditions.getAccuWeatherData5Days(mLocationSearchModel.getKey(), ACCU_WEATHER_APP_ID, weatherforcast.this, true);

        });
    }
    @Override
    public void getWeatherData(Object weatherModel, Boolean success, String errorMsg) {
        if (success) {
            if (weatherModel instanceof OpenWeatherModel) {
                OpenWeatherModel openWeatherModel = (OpenWeatherModel) weatherModel;
                tvCountry.setText("Country -- " + openWeatherModel.getSys().getCountry());
                tvCity.setText("City -- " + openWeatherModel.getName());
                tvWeather.setText("Temperature -- " + String.valueOf(openWeatherModel.getMain().getTemp()));
                Glide.with(weatherforcast.this)
                        .load("http://openweathermap.org/img/w/" + openWeatherModel.getWeather().get(0).getIcon() + ".png")
                        .into(ivWeatherIcon);

            } else if (weatherModel instanceof OpenWeather5DayModel) {

                OpenWeather5DayModel weatherBean = (OpenWeather5DayModel) weatherModel;
                try {
                    rvWeatherData.setAdapter(new RecyclerAdapter(weatherforcast.this, weatherBean.getMinMaxTemp()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else if (weatherModel instanceof AccuWeatherModel) {
                AccuWeatherModel accuWeatherModel = (AccuWeatherModel) weatherModel;
                tvWeather.setText("Current Temperature - " + String.valueOf(accuWeatherModel.getTemperature().getMetric().getValue()));
                tvCity.setText("City - " + mLocationSearchModel.getLocalizedName());
                tvCountry.setText("Country - " + mLocationSearchModel.getCountry().getLocalizedName());

                Glide.with(weatherforcast.this)
                        .load("http://apidev.accuweather.com/developers/Media/Default/WeatherIcons/" + String.format("%02d", accuWeatherModel.getWeatherIcon()) + "-s" + ".png")
                        .into(ivWeatherIcon);

            } else if (weatherModel instanceof AccuWeather5DayModel) {

                AccuWeather5DayModel accuWeather5DayModel = (AccuWeather5DayModel) weatherModel;
                rvWeatherData.setAdapter(new RecyclerAdapterAccuWeather(weatherforcast.this, accuWeather5DayModel));

            }
        }
    }
}


