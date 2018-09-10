package com.example.smartserve.keymortgageapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DebtServiceActivity extends AppCompatActivity {
    ImageView back,close;
    EditText rm,newloan,carloan,hloan,ptptn,cci,pli,oi;
    Button calc;
    TextView dsr;
    String text1,text2,text3,text4,text5,text6,text7,text8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_service);
        close =(ImageView)findViewById(R.id.close);
        back =(ImageView)findViewById(R.id.back);
        rm = (EditText) findViewById(R.id.rm);
        calc = (Button) findViewById(R.id.calc);
        dsr = (TextView) findViewById(R.id.dsr);
        newloan = (EditText) findViewById(R.id.newloan);
        carloan = (EditText) findViewById(R.id.carLoan);
        hloan = (EditText) findViewById(R.id.hLoan);
        ptptn = (EditText) findViewById(R.id.ptptn);
        cci = (EditText) findViewById(R.id.cci);
//        loanamt = (EditText) findViewById(R.id.ptptn);
        pli = (EditText) findViewById(R.id.pli);
        oi = (EditText) findViewById(R.id.oi);
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
                if(rm.getText().toString().equals(""))
                {
                   rm.setError("Please fill this field!");
                }
                if(newloan.getText().toString().equals(""))
                {
                    newloan.setError("Please fill this field!");
                }
                if(carloan.getText().toString().equals(""))
                {
                    carloan.setError("Please fill this field!");
                }
                if(hloan.getText().toString().equals(""))
                {
                    hloan.setError("Please fill this field!");
                }
                if(ptptn.getText().toString().equals(""))
                {
                    ptptn.setText("0");
                }
                if(cci.getText().toString().equals(""))
                {
                    cci.setError("Please fill this field!");
                }
                if(pli.getText().toString().equals(""))
                {
                    pli.setError("Please fill this field!");
                }
                if(oi.getText().toString().equals(""))
                {
                    oi.setError("Please fill this field!");
                }
                text1= rm.getText().toString();
                text2= newloan.getText().toString();
                text3= carloan.getText().toString();
                text4= hloan.getText().toString();
                text5= ptptn.getText().toString();
                text6= cci.getText().toString();
                text7= pli.getText().toString();
                text8= oi.getText().toString();
                //      if (text1.length() != 0 && text2.length() != 0 && text3.length() != 0 && text4.length() != 0 && text8.length() != 0 && text6.length() != 0 && text7.length() != 0 ) {
                    double input1 = Double.valueOf(text1);
                    double input2 = Double.valueOf(text2);
                    double input3 = Double.valueOf(text3);
                    double input4 = Double.valueOf(text4);
                    double input5 = Double.valueOf(text5);
                    double input6 = Double.valueOf(text6);
                    double input7 = Double.valueOf(text7);
                    double input8 = Double.valueOf(text8);
                 //   double input3 = Double.valueOf(text3);
                    double output=(input1-(input2+input3+input4+input6+input7+input8));
                    double output1=output+input5;
                    double res=(output1/input1)*100;
                  //  int a = (int) Math.round(res);
//                    DecimalFormat df=new DecimalFormat("0.00");
//                    String formate = df.format(output);
//                    try {
//                        double finalValue = (Double)df.parse(formate) ;
                    Log.e("Debt Fee",""+res);
                  //  Log.e("Stamping Fee(Int)",""+a);
                    dsr.setText(""+res+"%");
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
            }
        });
    }
}
