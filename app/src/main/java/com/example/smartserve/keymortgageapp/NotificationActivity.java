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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartserve.keymortgageapp.Adapters.NotificationAdapter;
import com.example.smartserve.keymortgageapp.Adapters.ShowBankAdapter;
import com.example.smartserve.keymortgageapp.Models.BankModel;
import com.example.smartserve.keymortgageapp.Models.NotificationModel;
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

public class NotificationActivity extends AppCompatActivity {
ImageView back,close;
    ProgressDialog progressDialog;
    List<NotificationModel> sList = new ArrayList<>();
    SessionManager sessionManager;
    HashMap<String, String> user;
    RecyclerView recyclerView;
    Network network;
    NotificationAdapter adapter;
    String keyuserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        close =(ImageView)findViewById(R.id.close);
        back =(ImageView)findViewById(R.id.back);
        network =new Network();
        progressDialog=new ProgressDialog(this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(NotificationActivity.this, UserDashboardActivity.class);
                startActivity(intent4);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        keyuserId = user.get("userID");

        showNewsFeeds();
        showNotifications();
        adapter = new NotificationAdapter(sList,asyncResult_addNewConnection);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    AsyncResult<NotificationModel> asyncResult_addNewConnection = new AsyncResult<NotificationModel>() {
        @Override
        public void success(NotificationModel click) {
            Intent intent =new Intent(NotificationActivity.this,NewsFeedDetailsActivity.class);
            intent.putExtra("notificationModel",click);
            startActivity(intent);
        }

        @Override
        public void error(String error) {

        }
    };
    private void showNotifications() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, network.getNotif,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj= new JSONObject(response.toString());
                            String resposne_sucess = obj.getString("status");
                            String resposne_message = obj.getString("message");
                            if(resposne_sucess.equals("true")) {
                                progressDialog.hide();
                                //JSONObject obj = new JSONObject(response);
                                JSONArray heroArray = obj.getJSONArray("data");

                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                   // notif_id,user_id,text,type,seen,status;
                                    // userpid, pid,name,location,cat_name,cost,size,description,project_image;
                                    //pid,name,location,cat_name,cost,size,description,project_image;
                                    NotificationModel hero = new NotificationModel(heroObject.getString("id"),
                                            heroObject.getString("user_id"),
                                            heroObject.getString("text"),
                                            heroObject.getString("type"),
                                            heroObject.getString("seen"),
                                            heroObject.getString("status"),"","0","");
                                    sList.add(hero);
                                    adapter.notifyDataSetChanged();
                                }
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
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

    private void showNewsFeeds() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, network.getNewsFeed,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj= new JSONObject(response.toString());
                            String resposne_sucess = obj.getString("status");
                            String resposne_message = obj.getString("message");
                            if(resposne_sucess.equals("true")) {
                                progressDialog.hide();
                                //JSONObject obj = new JSONObject(response);
                                JSONArray heroArray = obj.getJSONArray("data");

                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);
                                   // notif_id,user_id,text,type,seen,status;
                                    // userpid, pid,name,location,cat_name,cost,size,description,project_image;
                                    //pid,name,location,cat_name,cost,size,description,project_image;
                                    NotificationModel hero = new NotificationModel(heroObject.getString("id"),
                                            heroObject.getString("user_id"),
                                            heroObject.getString("title"),
                                            "",
                                           "",
                                            "" ,
                                            heroObject.getString("image"),"1",
                                            heroObject.getString("message"));
                                    sList.add(hero);
                                    adapter.notifyDataSetChanged();
                                }
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
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
