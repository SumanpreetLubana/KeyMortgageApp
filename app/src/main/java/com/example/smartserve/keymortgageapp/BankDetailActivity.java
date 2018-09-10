package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartserve.keymortgageapp.Models.BankModel;
import com.example.smartserve.keymortgageapp.Util.Network;
import com.example.smartserve.keymortgageapp.Util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BankDetailActivity extends AppCompatActivity {
TextView bname,domail; EditText branch,email;
ImageView back,close;
    HashMap<String, String> user;
    ProgressDialog progressDialog;
    String keyuserId,bid;
    String bemail,bbranch,bbname;
    Button submit;
CircleImageView circleImageView;
    Network network;
    SessionManager sessionManager;
BankModel bankModel;
String bankmail,bankdomain,newmail,bdomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);
        back = (ImageView) findViewById(R.id.back);
        close = (ImageView) findViewById(R.id.close);
        progressDialog =new ProgressDialog(this);
        network =new Network();
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        keyuserId = user.get("userID");
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
        bname = (TextView) findViewById(R.id.bname);
        domail = (TextView) findViewById(R.id.domain);
        email = (EditText) findViewById(R.id.email);
        branch = (EditText) findViewById(R.id.branch);
        submit = (Button) findViewById(R.id.submit);
        circleImageView=(CircleImageView)findViewById(R.id.profile_image);

        Intent intent=getIntent();
        bankModel = intent.getParcelableExtra("bankModel");
       bid=bankModel.getBankId();
      bankdomain= bankModel.getBankDomain();
     newmail= bankdomain.substring(bankdomain.lastIndexOf("/")+1,bankdomain.length());
Log.e("Domaincome",newmail);
        //Glide.with(this).load(bankModel.getBankLogo()).into(circleImageView);
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.bank_default)
                        .error(R.drawable.bank_default))
                .load(bankModel.getBankLogo())
                .into(circleImageView);
        bname.setText(bankModel.getBankName().toString());
        domail.setText(bankModel.getBankDomain().toString());
        branch.setText(bankModel.getBankBranch().toString());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               bbname= bname.getText().toString();
               bemail= email.getText().toString().trim();
                bdomain= domail.getText().toString();
               bankmail=bemail+newmail;
               //Log.e("Clicked",bankmail);
                bbranch=branch.getText().toString();
                if(bemail.equals("")) {
                    email.setError("Please Fill This Field!");
                }
                else if(bbranch.equals(""))
                {
                    branch.setError("Please Fill This Field!");
                }
                else
                {
                    getBankDetails();
                }
            }
        });
    }
    public void getBankDetails()
    {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = network.addBankDetails;
        Log.e("url", URL);
        // progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonObject= new JSONObject(response.toString());
                            String resposne_sucess = jsonObject.getString("status");
                            String resposne_message = jsonObject.getString("message");
                            if(resposne_sucess.equals("true"))
                            {

                               /* JSONObject resposne_userId = jsonObject.getJSONObject("user");
                                userid = resposne_userId.getString("id");
                                name = resposne_userId.getString("name");
                                fname = resposne_userId.getString("first_name");
                                lname = resposne_userId.getString("last_name");
                                emailid = resposne_userId.getString("email");
                                phone = resposne_userId.getString("phone");
                                et1.setText(name);
                                et2.setText(fname);
                                et3.setText(lname);
                                et4.setText(emailid);
                                et5.setText(phone);*/
                                progressDialog.hide();
                                Intent intent = new Intent(BankDetailActivity.this, BankListActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                                Log.e("UserID",keyuserId);
                            }
                            else{
//                                hideProgressDialog();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                progressDialog.hide();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", keyuserId);
                params.put("bank_id", bid);
               // params.put("bank_name", bbname);
                params.put("user_bank_email", bankmail);
                params.put("user_bank_branch", bbranch);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(jsObjRequest);
    }
}
