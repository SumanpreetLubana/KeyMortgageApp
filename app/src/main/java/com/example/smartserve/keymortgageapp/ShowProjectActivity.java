package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartserve.keymortgageapp.Adapters.BankAdapter;
import com.example.smartserve.keymortgageapp.Adapters.ProjectAdapter;
import com.example.smartserve.keymortgageapp.Adapters.ShowProjectAdapter;
import com.example.smartserve.keymortgageapp.Models.BankModel;
import com.example.smartserve.keymortgageapp.Models.PropertyModel;
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

public class ShowProjectActivity extends AppCompatActivity {
    ImageView back,close;
    LinearLayout linearLayout;
    List<ShowProjectModel> propertyModelList;
   // PropertyModel propertyModel;
    ShowProjectAdapter projectAdapter;
    String ImageId;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Network network;
    SessionManager sessionManager;
    HashMap<String, String> user;
    EditText networkSearch;
    String keyuserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);
        propertyModelList=new ArrayList<>();
        network =new Network();
        progressDialog=new ProgressDialog(this);
        close =(ImageView)findViewById(R.id.close);
        back =(ImageView)findViewById(R.id.back);

        linearLayout=(LinearLayout)findViewById(R.id.linear1);
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        networkSearch =(EditText)findViewById(R.id.network_search);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        keyuserId = user.get("userID");
        projectAdapter = new ShowProjectAdapter(getApplicationContext(),propertyModelList,asyncResult_addNewConnection);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(projectAdapter);

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
        loadCarList();

    }

  AsyncResult<ShowProjectModel> asyncResult_addNewConnection = new AsyncResult<ShowProjectModel>() {
        @Override
        public void success(ShowProjectModel click) {
            Log.e("IIdddd",click.getPid());
            deleteData(click);
        }

        @Override
        public void error(String error) {

        }
    };
    private void loadCarList() {
        if (propertyModelList != null && propertyModelList.size() > 0) {
            propertyModelList.clear();
        }
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, network.getBookProjects,
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
                                   // userpid, pid,name,location,cat_name,cost,size,description,project_image;
                                    //pid,name,location,cat_name,cost,size,description,project_image;
                                    ShowProjectModel hero = new ShowProjectModel(heroObject.getString("user_project_id"),
                                            heroObject.getString("project_id"),
                                            heroObject.getString("name"),
                                            heroObject.getString("location"),
                                            heroObject.getString("cat_name"),
                                            heroObject.getString("cost"),
                                            heroObject.getString("size"),
                                            heroObject.getString("descp"),
                                            heroObject.getString("project_image")
                                    );
                                    propertyModelList.add(hero);
                                    projectAdapter.notifyDataSetChanged();
                                }
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
                                projectAdapter.notifyDataSetChanged();
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

    private void deleteData(final ShowProjectModel showProjectModel) {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, network.deleteProject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj= new JSONObject(response.toString());
                            String resposne_sucess = obj.getString("status");
                            String resposne_message = obj.getString("message");
                            if(resposne_sucess.equals("true")) {
                                progressDialog.hide();
                                loadCarList();
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
                params.put("id", showProjectModel.getUserpid());
                params.put("user_id", keyuserId);
//                params.put("project_id", pid);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}
