package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class currencyconverter extends AppCompatActivity {
    private EditText txtinr;
    private TextView dollar,pound;
    RecyclerView recycleclist;
    SearchView searchView1;
    List<currencyDetail> currency = new ArrayList<>();
    Adaptercurrency adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencyconverter);
        txtinr=findViewById(R.id.txtinr);
        SearchView searchView1 = findViewById(R.id.searchView1);
        currencyDetail c1=new currencyDetail();
        c1.setCurrency("USD");
        currency.add(c1);
        currencyDetail c2=new currencyDetail();
        c2.setCurrency("POUND");
        currency.add(c2);
        currencyDetail c3=new currencyDetail();
        c3.setCurrency("YEN");
        currency.add(c3);
        currencyDetail c4=new currencyDetail();
        c4.setCurrency("EURO");
        currency.add(c4);
        recycleclist = findViewById(R.id.recycleclist);
        adapter = new Adaptercurrency(this, currency);
        //recycleclist = findViewById(R.id.recycleclist);
        //recycleclist.setAdapter(adapter);
        //recycleclist.setLayoutManager(new LinearLayoutManager(this));
        txtinr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recycleclist = findViewById(R.id.recycleclist);
                recycleclist.setAdapter(adapter);
                recycleclist.setLayoutManager(new LinearLayoutManager(currencyconverter.this));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When the text in txtinr changes, update the filtered list
                List<currencyDetail> filteredCurrencyList = filterCurrencyList(charSequence.toString());
                adapter.setFilteredList(filteredCurrencyList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //adapter = new Adaptercurrency(this, currency);
        //recycleclist.setAdapter(adapter);
        // recycleclist.setLayoutManager(new LinearLayoutManager(this));
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                List<currencyDetail> filteredCurrencyList = filterCurrencyList(newText);
                adapter.setFilteredList(filteredCurrencyList);
                return true;
            }
        });
        //searchView1=findViewById(R.id.searchView1);
    }


    private List<currencyDetail> filterCurrencyList(String query) {
        List<currencyDetail> filteredList = new ArrayList<>();
        for (currencyDetail currencyItem : currency) {
            String currencyName = currencyItem.getCurrency().toLowerCase();
            // Check if the currency name contains the user's query
            if (currencyName.contains(query.toLowerCase())) {
                // Perform currency conversion logic here and set the converted value in txtd
                double convertedValue = convertCurrency(
                        txtinr.getText().toString(), currencyItem.getCurrency());
                currencyItem.setConvertedValue(convertedValue);
                filteredList.add(currencyItem);
            }
        }
        return filteredList;

    }

    private double convertCurrency(String inputAmount, String targetCurrency) {
        // Implement your currency conversion logic here.
        // Convert the input amount to the target currency and return the result.
        // Example: 1 INR to USD conversion logic
        double inrValue = Double.parseDouble(inputAmount);

        // Implement the conversion logic for different currencies
        if (targetCurrency.equals("USD")) {
            // Conversion rate for USD from INR (replace with actual rate)
            double usdToInrRate = 0.013;
            return inrValue * usdToInrRate;
        } else if (targetCurrency.equals("POUND")) {
            // Conversion rate for Pound (GBP) from INR (replace with actual rate)
            double gbpToInrRate = 0.0095;
            return inrValue * gbpToInrRate;
        } else if (targetCurrency.equals("YEN")) {
            // Conversion rate for Yen (JPY) from INR (replace with actual rate)
            double jpyToInrRate = 1.42;
            return inrValue * jpyToInrRate;
        } else if (targetCurrency.equals("EURO")) {
            // Conversion rate for Euro (EUR) from INR (replace with actual rate)
            double eurToInrRate = 0.011;
            return inrValue * eurToInrRate;
        }

        // Return 0.0 if the target currency is not supported
        return 0.0;
    }
}