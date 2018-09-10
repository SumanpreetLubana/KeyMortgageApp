package com.example.smartserve.keymortgageapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;


public class SecondMonthlyPayment extends AppCompatActivity {
    ImageView back,close;
    EditText price,margin;
    Button calc;
    TextView loanamt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_monthly_payment);
        close =(ImageView)findViewById(R.id.close);
        back =(ImageView)findViewById(R.id.back);
        price = (EditText) findViewById(R.id.price);
        margin = (EditText) findViewById(R.id.margin);
        calc = (Button) findViewById(R.id.calc);
        loanamt = (TextView) findViewById(R.id.loanamt);
        margin.setText("90");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        price.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculate();
                }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    String text1 = price.getText().toString();
                String text2 = margin.getText().toString();
                if (text1.length() != 0 && text2.length() != 0) {
                    double input1 = Double.valueOf(text1);
                    double input2 = Double.valueOf(text2);
                    double output=(input1*input2)/100;
//                    DecimalFormat df=new DecimalFormat("0.00");
//                    String formate = df.format(output);
//                    try {
//                        double finalValue = (Double)df.parse(formate) ;
//                        Log.e("Final",""+finalValue);
                        loanamt.setText(""+output);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                }*/
            calculate();
            }
        });
    }
    public void calculate()
    {
        String text1 = price.getText().toString();
        String text2 = margin.getText().toString();
        if (text1.length() != 0 && text2.length() != 0) {
            double input1 = Double.valueOf(text1);
            double input2 = Double.valueOf(text2);
            double output=(input1*input2)/100;
//                    DecimalFormat df=new DecimalFormat("0.00");
//                    String formate = df.format(output);
//                    try {
//                        double finalValue = (Double)df.parse(formate) ;
//                        Log.e("Final",""+finalValue);
            loanamt.setText(""+output);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
        }
    }
}
