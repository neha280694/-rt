package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.slider.RangeSlider;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class calculatorsip extends AppCompatActivity {
    private TextView txttotalvalue,txtinvested,txtreturnrate,txtyear;
    private RangeSlider rangeinvested,rangereturn,rangeperiod;
    private DecimalFormat decimalFormat;
    private String returnRate = "0%";
    private String investedAmount = "0";
    private String investmentPeriod = "0 years";
    private PieChart pieChartt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculatorsip);
        txtinvested=findViewById(R.id.txtinvested);
        txttotalvalue=findViewById(R.id.txttotalvalue);
        txtreturnrate=findViewById(R.id.txtreturnrate);
        txtyear=findViewById(R.id.txtyear);
        rangeinvested=findViewById(R.id.rangeinvested);
        rangereturn=findViewById(R.id.rangereturn);
        rangeperiod=findViewById(R.id.rangeperiod);
        pieChartt=findViewById(R.id.pieChartt);

        decimalFormat = new DecimalFormat("#,##0.00");
        pieChartt.getDescription().setEnabled(false); // Disable chart description
        pieChartt.setDrawEntryLabels(false); // Disable labels on chart slices
        pieChartt.setDrawHoleEnabled(false); // Disable the central hole

// Create a Legend and customize its properties
        Legend legend = pieChartt.getLegend();
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
                updateInvestmentPieChart(txtinvested, txttotalvalue, pieChartt);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

// Add the TextWatcher to each TextView
        txtinvested.addTextChangedListener(textWatcher);
        txttotalvalue.addTextChangedListener(textWatcher);


        decimalFormat = new DecimalFormat("#,##0.00");


        rangereturn.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                try {
                    // Remove the percentage symbol ("%") from the string
                    String stringValue = String.valueOf(value);
                    stringValue = stringValue.replace("%", "");

                    // Update the returnRate String
                    returnRate = stringValue + "%";

                    int intValue = Math.round(value);
                    String text = intValue + "%"; // Append "%" to the value
                    txtreturnrate.setText(text);
                    updateSIPResult(txtreturnrate, txtinvested, txtyear, txttotalvalue);
                } catch (NumberFormatException e) {
                    txtreturnrate.setText("Invalid input");
                }
            }
        });

        rangeinvested.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                try {
                    // Update the investedAmount String
                    investedAmount = String.valueOf(Math.round(value));

                    int intValue = Math.round(value);
                    String text = String.valueOf(intValue);
                    txtinvested.setText(text);
                    updateSIPResult(txtreturnrate, txtinvested, txtyear, txttotalvalue);
                } catch (NumberFormatException e) {
                    txtinvested.setText("Invalid input");
                }
            }
        });

        rangeperiod.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                try {
                    // Remove any non-numeric characters from the string
                    String stringValue = String.valueOf(value);
                    stringValue = stringValue.replaceAll("[^0-9.]+", "");

                    // Update the investmentPeriod String
                    investmentPeriod = stringValue + " years";

                    int intValue = Math.round(value);
                    String text = intValue + " years"; // Append "years" to the value
                    txtyear.setText(text);
                    updateSIPResult(txtreturnrate, txtinvested, txtyear, txttotalvalue);
                } catch (NumberFormatException e) {
                    txtyear.setText("Invalid input");
                }
            }
        });



        // Add debug logs to check values during parsing

    }

    private void updateSIPResult(TextView txtReturnRate, TextView txtInvestedAmount, TextView txtInvestmentPeriod, TextView txtSIPResult) {
        // Get the input values from the TextViews
        String returnRateStr = txtReturnRate.getText().toString();
        String investedAmountStr = txtInvestedAmount.getText().toString();
        String investmentPeriodStr = txtInvestmentPeriod.getText().toString();

        try {
            // Remove any non-numeric characters and parse the cleaned strings as floats
            float returnRate = Float.parseFloat(returnRateStr.replace("%", ""));
            float investedAmount = Float.parseFloat(investedAmountStr);
            int investmentPeriod = Integer.parseInt(investmentPeriodStr.replace(" years", ""));

            // Debugging: Print the input values to the console
            Log.d("SIP_DEBUG", "returnRate: " + returnRate);
            Log.d("SIP_DEBUG", "investedAmount: " + investedAmount);
            Log.d("SIP_DEBUG", "investmentPeriod: " + investmentPeriod);

            // Ensure that the inputs are valid
            if (returnRate <= 0 || investedAmount <= 0 || investmentPeriod <= 0) {
                txtSIPResult.setText("Invalid input"); // Display a message for invalid input
                return;
            }

            // Calculate SIP total value using the correct formula:
            // SIP Total Value = P * [((1 + r/n)^(nt) - 1) / (r/n)]
            // where P = Invested Amount
            //       r = Annual return rate (as a decimal)
            //       n = Number of times interest is compounded per year (typically 12 for monthly)
            //       t = Total number of years

            // Convert the annual return rate from percentage to decimal
            float annualReturnRate = returnRate / 100;

            // Calculate monthly return rate
            float monthlyReturnRate = annualReturnRate / 12;

            // Calculate total number of months
            int totalMonths = investmentPeriod * 12;

            // Calculate SIP total value
            double sipTotalValue = investedAmount * ((Math.pow(1 + monthlyReturnRate, totalMonths) - 1) / (monthlyReturnRate));

            // Format the result and display it in the TextView
            String formattedResult = String.format("%.2f", sipTotalValue); // Format to two decimal places
            txtSIPResult.setText(formattedResult); // Set the formatted result

        } catch (NumberFormatException e) {
            txtSIPResult.setText("Invalid input");
        }
    }

    private void updateInvestmentPieChart(TextView investedAmountTextView, TextView returnValueTextView, PieChart pieChart) {
        try {
            // Get the text values from the TextViews
            String investedAmountStr = investedAmountTextView.getText().toString();
            String returnValueStr = returnValueTextView.getText().toString();

            // Parse the values from the strings
            float investedAmount = Float.parseFloat(investedAmountStr);
            float returnValue = Float.parseFloat(returnValueStr);

            // Create a PieEntry list with investment data
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(investedAmount, "Invested Amount"));
            entries.add(new PieEntry(returnValue, "Return Value"));

            // Define colors for the slices (you can customize these colors)
            int[] colors = {getColor(R.color.pie2), getColor(R.color.pie1)}; // Customize colors as needed

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