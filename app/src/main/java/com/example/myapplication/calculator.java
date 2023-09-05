package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class calculator extends AppCompatActivity {
    private TextView textviewResult;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, bp, bm, be, bmul, bdiv, bclr;
    double firstnumber;
    String operations;
    private StringBuilder inputStringBuilder = new StringBuilder();
    private char operator = '\0';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        textviewResult = findViewById(R.id.txt);

        setEqualsButtonListener();
       // setOperatorButtonListeners();
        b1 = findViewById(R.id.one);
        b2 = findViewById(R.id.two);
        b3 = findViewById(R.id.three);
        b4 = findViewById(R.id.four);
        b5 = findViewById(R.id.five);
        b6 = findViewById(R.id.six);
        b7 = findViewById(R.id.seven);
        b8 = findViewById(R.id.eight);
        b9 = findViewById(R.id.nine);
        b10 = findViewById(R.id.zero);
        be = findViewById(R.id.equal);
        bclr = findViewById(R.id.clear);
        bp=findViewById(R.id.plus);
        bdiv=findViewById(R.id.division);
        bmul=findViewById(R.id.cross);
        bm=findViewById(R.id.minus);


        buttone(b1, "1");
        buttone(b2, "2");
        buttone(b3, "3");
        buttone(b4, "4");
        buttone(b5, "5");
        buttone(b6, "6");
        buttone(b7, "7");
        buttone(b8, "8");
        buttone(b9, "9");
        buttone(b10, "0");

        bclr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textviewResult.setText(" ");
            }
        });
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '+') {
                    textviewResult.append("+");
                }

            }
       });
        bmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '*') {
                    textviewResult.append("*");
                }
            }
        });
        bdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '/') {
                    textviewResult.append("/");
                }

            }
        });
        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '-') {
                    textviewResult.append("-");
                }

            }
        });



    }

    private void exceedLength() {
        if (textviewResult.getText().toString().length() > 10) {
            textviewResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
    }

    private void setEqualsButtonListener() {
        // Set OnClickListener for the equal button
        Button be = findViewById(R.id.equal);
        be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textviewResult.getText().toString().trim();

                if (!input.isEmpty()) {
                    try {
                        double result = evaluateExpression(input);

                        if (result % 1 == 0) {
                            // If the result is an integer, display it as an integer
                            textviewResult.setText(String.valueOf((int) result));
                        } else {
                            // If the result has a fractional part, display it as a double
                            textviewResult.setText(String.valueOf(result));
                        }
                    } catch (NumberFormatException e) {
                        textviewResult.setText("Error: Invalid input");
                    } catch (ArithmeticException e) {
                        textviewResult.setText("Error: Division by zero");
                    }
                } else {
                    textviewResult.setText("Error: No input");
                }
            }
        });
    }


    private void buttone(Button x, String y) {
        x.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                textviewResult.setVisibility(View.VISIBLE);
                textviewResult.append(y);

                if (textviewResult.getText().toString().length() > 10) {
                    textviewResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                }

            }
        });

    }

    private double evaluateExpression(String expression) {
        String[] parts = expression.split("[+\\-*/]");
        String[] operators = expression.split("[0-9.]+");

        double result = Double.parseDouble(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            double operand = Double.parseDouble(parts[i]);
            char operator = operators[i].charAt(0);

            switch (operator) {
                case '+':
                    result += operand;
                    break;
                case '-':
                    result -= operand;
                    break;
                case '*':
                    result *= operand;
                    break;
                case '/':
                    if (operand != 0) {
                        result /= operand;
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
            }
        }

        return result;
    }








    private void updateResultText() {
        textviewResult.setText(inputStringBuilder.toString());
    }

  /*  private void setOperatorButtonListeners() {
        bp = findViewById(R.id.plus);
        bm = findViewById(R.id.minus);
        bmul = findViewById(R.id.cross);
        bdiv = findViewById(R.id.division);

        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '/') {
                    textviewResult.append("+");
                }

            }
        });

        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '/') {
                    textviewResult.append("-");
                }

            }
        });

        bmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '/') {
                    textviewResult.append("*");
                }

            }
        });

        bdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = textviewResult.getText().toString();
                if (currentText.length() == 0 || currentText.charAt(currentText.length() - 1) != '/') {
                    textviewResult.append("/");
                }

            }
        });


    }*/

}







