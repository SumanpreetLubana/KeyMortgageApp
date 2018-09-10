package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartserve.keymortgageapp.Adapters.BankAdapter;
import com.example.smartserve.keymortgageapp.Models.BankModel;
import com.example.smartserve.keymortgageapp.Models.PropertyModel;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;
import com.example.smartserve.keymortgageapp.Util.SessionManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<BankModel> sList = new ArrayList<>();
    BankAdapter adapter;
    SessionManager sessionManager;
    HashMap<String, String> user;
    ImageView back,close;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);

        progressDialog = new ProgressDialog(this);

        back = (ImageView) findViewById(R.id.back);
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(BankListActivity.this,UserDashboardActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        adapter = new BankAdapter(getApplicationContext(),sList, asyncResult_addNewConnection);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        loadBankList();
    }

    AsyncResult<BankModel> asyncResult_addNewConnection = new AsyncResult<BankModel>() {
        @Override
        public void success(BankModel click) {
            Intent intent =new Intent(BankListActivity.this,BankDetailActivity.class);
            intent.putExtra("bankModel",click);
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };
    private void loadBankList() {
        if (sList != null && sList.size() > 0) {
            sList.clear();
        }
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://keymortgage.co/app/api/Bank/getBanks",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           progressDialog.hide();
                            JSONObject obj = new JSONObject(response);
                            String resposne_sucess = obj.getString("status");
                            String resposne_message = obj.getString("message");
                            if(resposne_sucess.equals("true")) {
                                JSONArray heroArray = obj.getJSONArray("bank_details");
//String bankId,bankName,bankBranch,bankLogo,bankDomain;


                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    BankModel hero = new BankModel(heroObject.getString("bank_id"),
                                            heroObject.getString("bank_name"),
                                            heroObject.getString("bank_branch"),
                                            heroObject.getString("bank_logo"),
                                            heroObject.getString("bank_domain"));
                                    sList.add(hero);
                                    adapter.notifyDataSetChanged();

                                }
                            }else{
                                Toast.makeText(BankListActivity.this,resposne_message,Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
