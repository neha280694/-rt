package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;

import java.text.DecimalFormat;
import java.util.List;

public class emicalculator extends AppCompatActivity {
    private TextView perc,txttotalamount,txtloana,txty;
    private RangeSlider rangeperc,rangeloan,rangeyear;
    private DecimalFormat decimalFormat;
    private Button btn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emicalculator);
        perc=findViewById(R.id.perc);
        rangeperc=findViewById(R.id.rangeperc);
        txttotalamount=findViewById(R.id.txttotalamount);
        txtloana=findViewById(R.id.txtloana);
        txty=findViewById(R.id.txty);
        rangeloan=findViewById(R.id.rangeloan);
        rangeyear=findViewById(R.id.rangeyear);
        btn1=findViewById(R.id.btn1);
        decimalFormat = new DecimalFormat("#,##0.00");

        rangeperc.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Check if there are any values (thumbs) in the list
                if (!slider.getValues().isEmpty()) {
                    int intValue = Math.round(slider.getValues().get(0));
                    String text = "" + intValue + "%"; // Append "%" to the value
                    perc.setText(text);
                } else {
                    perc.setText("No values selected");
                }
               // updateEMI();

            }

        });
        rangeloan.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Check if there are any values (thumbs) in the list
                if (!slider.getValues().isEmpty()) {
                    int intValue = Math.round(slider.getValues().get(0));
                    String text = "" + intValue ; // Append "%" to the value
                    txtloana.setText(text);
                } else {
                    txtloana.setText("No values selected");
                }
                //updateEMI();

            }
        });
        rangeyear.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Check if there are any values (thumbs) in the list
                if (!slider.getValues().isEmpty()) {
                    int intValue = Math.round(slider.getValues().get(0));
                    String text = "" +intValue + "year"; // Append "%" to the value
                    txty.setText(text);
                } else {
                    txty.setText("No values selected");
                }
                //updateEMI();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEMI();

            }
        });
    }




    private void updateEMI() {
        // Retrieve the values from RangeSliders
        float interestRate = rangeperc.getValues().get(0);
        float loanAmount = rangeloan.getValues().get(0);
        float loanTenure = rangeyear.getValues().get(0);

        // Calculate EMI (You need to implement the EMI calculation logic here)
        double emi = calculateEMI(interestRate, loanAmount, loanTenure);

        // Format the EMI with up to two decimal places, removing trailing zeros
        String formattedEMI = decimalFormat.format(emi);

        // Display the updated EMI result in the emiResult TextView
        txttotalamount.setText("" + formattedEMI); // You can customize the currency symbol as needed
    }

    // Implement the EMI calculation logic here
    private double calculateEMI(float interestRate, float loanAmount, float loanTenure) {
        // Your EMI calculation formula goes here
        // For example, you can use the following formula (replace with your actual formula):
        // EMI = (P * r * (1 + r)^n) / ((1 + r)^n - 1)
        // where P = Principal amount (loan amount)
        //       r = Monthly interest rate (annual rate / 12 / 100)
        //       n = Total number of monthly payments (loan tenure * 12)
        //       EMI = Equated Monthly Installment

        // Calculate monthly interest rate
        float monthlyInterestRate = (interestRate / 12) / 100;

        // Calculate total number of monthly payments
        float totalPayments = loanTenure * 12;

        // Calculate EMI using the formula
        double emi = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments))
                / (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);

        return emi;
    }






}