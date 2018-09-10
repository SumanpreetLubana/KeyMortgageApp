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


public class MonthlyPayment extends AppCompatActivity {
    ImageView back, close;
    EditText price, rate, time,loanrm;
    String text1, text2, text3, text4;
    Button calc;
    TextView downppercent, downpaymentrm, loanamt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_payment);
        close = (ImageView) findViewById(R.id.close);
        back = (ImageView) findViewById(R.id.back);
        price = (EditText) findViewById(R.id.price);
        loanrm = (EditText) findViewById(R.id.loanrm);
        calc = (Button) findViewById(R.id.calc);
        downppercent = (TextView) findViewById(R.id.downperc);
        downpaymentrm = (TextView) findViewById(R.id.downrm);
        time = (EditText) findViewById(R.id.loanyrs);
        rate = (EditText) findViewById(R.id.rate);
        loanamt = (TextView) findViewById(R.id.loanamt);
        time.setText("30");
        rate.setText("4.4");
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

        downppercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateValues();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateValues();
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateValues();
            }
        });
       /*   loanrm.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              String price1     = price.getText().toString();
                String downPayment   = downpaymentrm.getText().toString();
                  double input1 = Double.valueOf(price1);
                  double input2 = Double.valueOf(downPayment);
                  //   int input2 = Integer.valueOf(s.toString());

                  double output = input1-input2;
                  //Log.e("Out", "" + output);
                  loanrm.setText("" + output);
              }

              @Override
              public void afterTextChanged(Editable editable) {

              }
          });*/
/*        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String text1 = downppercent.getText().toString();
                String text2 = price.getText().toString();
                if (text1.length() != 0 && text2.length()!=0) {
                    double input1 = Double.valueOf(text1);
                    double input2 = Double.valueOf(text2);
                    //   int input2 = Integer.valueOf(s.toString());

                    double output = (input2*(input1 / 100));
                    Log.e("Out",""+output);
                    downpaymentrm.setText("" + output);
                }
            }
        });*/
        price.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                text1 = downppercent.getText().toString();
                text2 = price.getText().toString();
                if (text1.length() != 0 && text2.length() != 0) {
                    double input1 = Double.valueOf(text1);
                    double input2 = Double.valueOf(text2);
                    //   int input2 = Integer.valueOf(s.toString());

                    double output = (input2 * (input1 / 100));
                    Log.e("Out", "" + output);
                    downpaymentrm.setText("" + output);
                    double output1= input2-output;
                    loanrm.setText("" + output1);
//                    String price1     = price.getText().toString();
//                    String downPayment   = downpaymentrm.getText().toString();
//                    double input11 = Double.valueOf(price1);
//                    double input22 = Double.valueOf(downPayment);
//                    //   int input2 = Integer.valueOf(s.toString());
//
//                    double output2 = input11-input22;
//                    //Log.e("Out", "" + output);
//                    loanrm.setText("" + output2);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateValues();
            }
        });

        downppercent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                text1 = downppercent.getText().toString();
                text2 = price.getText().toString();
                if (text1.length() != 0 && text2.length() != 0) {
                    double input1 = Double.valueOf(text1);
                    double input2 = Double.valueOf(text2);
                    //   int input2 = Integer.valueOf(s.toString());

                    double output = (input2 * (input1 / 100));
                    Log.e("Out", "" + output);
                    downpaymentrm.setText("" + output);
                    calculateValues();

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void calculateValues() {
        String text1 = price.getText().toString();
        String text2 = downpaymentrm.getText().toString();
        String text3 = time.getText().toString();
        String text4 = rate.getText().toString();
        if (text1.length() != 0 && text2.length() != 0) {
            double input1 = Double.valueOf(text1);
            double input2 = Double.valueOf(text2);
            int input3 = Integer.valueOf(text3);
            double input4 = Double.valueOf(text4);
            if (input1 > input2) {
                double t1 = input1 - input2;
                double rate = input4 / 1200;
                int time = input3 * 12;
                //  double num=Math.pow(rate*(1+rate),time);
                double denom = Math.pow((1 + rate), time);
                double res = t1 * ((rate * denom) / (denom - 1));
                Log.e("Result", "" + res);
                DecimalFormat df = new DecimalFormat("0.00");
                String formate = df.format(res);
                try {
                    double finalValue = (Double) df.parse(formate);
                    Log.e("Final", "" + finalValue);
                    loanamt.setText("" + finalValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

