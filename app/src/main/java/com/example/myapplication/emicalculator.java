package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.slider.RangeSlider;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
        PieChart pieChart = findViewById(R.id.pieChart);
        // Inside your initialization code:
        pieChart.getDescription().setEnabled(false); // Disable chart description
        pieChart.setDrawEntryLabels(false); // Disable labels on chart slices
        pieChart.setDrawHoleEnabled(false); // Disable the central hole

// Create a Legend and customize its properties
        Legend legend = pieChart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setDrawInside(false);
        legend.setXEntrySpace(10f); // Adjust the horizontal space between legend and chart
        legend.setYEntrySpace(0f); // Adjust the vertical space between legend entries
        // Rest of your code remains the same
        // Create a TextWatcher for each TextView
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When the text in any TextView changes, update the PieChart
                updatePieChart(perc, txtloana, txty, pieChart);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

// Add the TextWatcher to each TextView
        perc.addTextChangedListener(textWatcher);
        txtloana.addTextChangedListener(textWatcher);
        txty.addTextChangedListener(textWatcher);

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
                updateEMI();

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
                updateEMI();

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
    private void updatePieChart(TextView perc, TextView txtloana, TextView txty, PieChart pieChart) {
        String interestRateText = perc.getText().toString();
        String loanAmountText = txtloana.getText().toString();
        String loanTenureText = txty.getText().toString();

        // Remove percentage symbols from the input strings
        interestRateText = interestRateText.replace("%", "");
        loanAmountText = loanAmountText.replace("%", "");

        // Extract numeric part of loanTenureText
        loanTenureText = loanTenureText.replaceAll("[^0-9.]+", ""); // Keep only digits and decimals

        try {
            // Parse the cleaned strings as floats
            float interestRate = Float.parseFloat(interestRateText);
            float loanAmount = Float.parseFloat(loanAmountText);
            float loanTenure = Float.parseFloat(loanTenureText);

            // Proceed with the calculations using the parsed float values
            double emi = calculateEMI(interestRate, loanAmount, loanTenure);

            // Create a PieEntry list with loan data
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry((float) emi, "Emi"));
            entries.add(new PieEntry(loanAmount, "Loan Amount"));
            entries.add(new PieEntry((interestRate / 100) * loanAmount, "Interest Paid"));
            int[] colors = {getColor(R.color.colorAccent1), getColor(R.color.colorAccent), getColor(R.color.teal_200)};
            // Create a PieDataSet
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);

            // Create a PieData object and set the dataSet
            PieData pieData = new PieData(dataSet);
            pieData.setValueTextColor(Color.WHITE);

            // Set data to the PieChart
            pieChart.setData(pieData);
            pieChart.invalidate();
        } catch (NumberFormatException e) {
            // Handle the exception if the parsing fails
            e.printStackTrace();
        }
    }






}