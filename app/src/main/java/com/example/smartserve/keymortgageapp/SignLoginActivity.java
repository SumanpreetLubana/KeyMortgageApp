package com.example.smartserve.keymortgageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class SignLoginActivity extends AppCompatActivity {
Button bt1,bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_login);
        bt1=(Button)findViewById(R.id.signup);
        bt2=(Button)findViewById(R.id.login);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignLoginActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(SignLoginActivity.this, LoginActivity.class);
//                startActivity(i);
                Intent i = new Intent(SignLoginActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
