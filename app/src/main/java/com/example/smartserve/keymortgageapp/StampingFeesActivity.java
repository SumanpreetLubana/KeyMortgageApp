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

import org.w3c.dom.Text;


public class StampingFeesActivity extends AppCompatActivity {
    ImageView back,close;
    TextView sfee;
    EditText mr,noc,years;
    Button calc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamping_fees);
        close =(ImageView)findViewById(R.id.close);
        back =(ImageView)findViewById(R.id.back);
        sfee =(TextView)findViewById(R.id.sfee);
        mr =(EditText)findViewById(R.id.mr);
        calc = (Button) findViewById(R.id.calc);
        noc =(EditText) findViewById(R.id.noc);
        years =(EditText) findViewById(R.id.years);
        noc.setText("0");
        years.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                stampcalc();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                stampcalc();
            }
        });
        noc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stampcalc();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                stampcalc();
            }
        });
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
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stampcalc();
               /* String text1 = mr.getText().toString();
                String text2 = years.getText().toString();
                String text3 = noc.getText().toString();
                if (text1.length() != 0 && text2.length() != 0 && text3.length() != 0) {
                    double input1 = Double.valueOf(text1);
                    double input2 = Double.valueOf(text2);
                    double input3 = Double.valueOf(text3);
                    double output=(((((input1*12)-2400)/250)*input2)+(input3*10));
                    int a = (int) Math.round(output);
//                    DecimalFormat df=new DecimalFormat("0.00");
//                    String formate = df.format(output);
//                    try {
//                        double finalValue = (Double)df.parse(formate) ;
                      Log.e("Stamping Fee",""+output);
                    Log.e("Stamping Fee(Int)",""+a);
                    sfee.setText(""+output);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                }*/
            }
        });
    }
    public void stampcalc()
    {
        String text1 = mr.getText().toString();
        String text2 = years.getText().toString();
        String text3 = noc.getText().toString();
        if (text1.length() != 0 && text2.length() != 0 && text3.length() != 0) {
            double input1 = Double.valueOf(text1);
            double input2 = Double.valueOf(text2);
            double input3 = Double.valueOf(text3);
            double output=(((((input1*12)-2400)/250)*input2)+(input3*10));
            int a = (int) Math.round(output);
//                    DecimalFormat df=new DecimalFormat("0.00");
//                    String formate = df.format(output);
//                    try {
//                        double finalValue = (Double)df.parse(formate) ;
            Log.e("Stamping Fee",""+output);
            Log.e("Stamping Fee(Int)",""+a);
            sfee.setText(""+output);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
        }
    }
}
