package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartserve.keymortgageapp.Adapters.BankAdapter;
import com.example.smartserve.keymortgageapp.Adapters.ShowBankAdapter;
import com.example.smartserve.keymortgageapp.Models.BankModel;
import com.example.smartserve.keymortgageapp.Models.ShowBankModel;
import com.example.smartserve.keymortgageapp.Models.ShowProjectModel;
import com.example.smartserve.keymortgageapp.Util.AsyncResult;
import com.example.smartserve.keymortgageapp.Util.Network;
import com.example.smartserve.keymortgageapp.Util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowBankActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<ShowBankModel> sList = new ArrayList<>();
    ShowBankAdapter adapter;
    SessionManager sessionManager;
    HashMap<String, String> user;
    ImageView back,close;
    ProgressDialog progressDialog;
    Network network;
     String keyuserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bank);

        progressDialog = new ProgressDialog(this);
          network =new Network();
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
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        keyuserId = user.get("userID");
        adapter = new ShowBankAdapter(getApplicationContext(), sList, asyncResult_addNewConnection);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        loadBankList();
    }

    AsyncResult<ShowBankModel> asyncResult_addNewConnection = new AsyncResult<ShowBankModel>() {
        @Override
        public void success(ShowBankModel click) {
//            Intent intent = new Intent(ShowBankActivity.this, BankDetailActivity.class);
//            intent.putExtra("bankModel", click);
//            startActivity(intent);
            deleteData(click);
        }

        @Override
        public void error(String error) {

        }
    };


    private void deleteData(final ShowBankModel showProjectModel) {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, network.deleteBank,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj= new JSONObject(response.toString());
                            String resposne_sucess = obj.getString("status");
                            String resposne_message = obj.getString("message");
                            if(resposne_sucess.equals("true")) {
                                progressDialog.hide();

                                loadBankList();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
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
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", showProjectModel.getBankId());
                params.put("user_id", keyuserId);
//                params.put("project_id", pid);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
    private void loadBankList() {
        if (sList != null && sList.size() >= 0) {
            sList.clear();
        }
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://keymortgage.co/app/api/Bank/getUserBank",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.hide();
                            JSONObject obj = new JSONObject(response);
                            String resposne_sucess = obj.getString("status");
                            String resposne_message = obj.getString("message");
                            if(resposne_sucess.equals("true")) {
                                JSONArray heroArray = obj.getJSONArray("data");
//String bankId,bankName,bankBranch,bankLogo,bankDomain;
                                //  bankId,bankName,userbankBranch,bankLogo,bankDomain,useremail

                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                    ShowBankModel hero = new ShowBankModel(heroObject.getString("id"),
                                            heroObject.getString("bank_name"),
                                            heroObject.getString("user_bank_branch"),
                                            heroObject.getString("bank_logo"),
                                            heroObject.getString("bank_domain"),
                                            heroObject.getString("user_bank_email"));
                                    sList.add(hero);
                                    adapter.notifyDataSetChanged();

                                }
                            }else {
                                Toast.makeText(ShowBankActivity.this,resposne_message,Toast.LENGTH_SHORT);
                                adapter.notifyDataSetChanged();
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
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", keyuserId);
//                params.put("project_id", pid);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    }