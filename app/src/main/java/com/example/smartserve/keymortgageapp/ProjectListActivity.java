package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.smartserve.keymortgageapp.Adapters.ProjectAdapter;
import com.example.smartserve.keymortgageapp.Models.ImagesModel;
import com.example.smartserve.keymortgageapp.Models.PropertyModel;
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

public class ProjectListActivity extends AppCompatActivity {
    ImageView back,close;
    LinearLayout linearLayout;
    List<PropertyModel> propertyModelList;
    PropertyModel propertyModel;
    ProjectAdapter projectAdapter;
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
        setContentView(R.layout.activity_project_list);
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

        projectAdapter = new ProjectAdapter(getApplicationContext(),propertyModelList,asyncResult_addNewConnection1);
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
                Intent intent =new Intent(ProjectListActivity.this,UserDashboardActivity.class);
                startActivity(intent);
            }
        });
        loadCarList();

        networkSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });

    }

    void filter(String text){
        List<PropertyModel> temp = new ArrayList();
        if(propertyModelList!=null && propertyModelList.size()>0) {
            for (PropertyModel d : propertyModelList) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if ((d.getName().toLowerCase().contains(text)||d.getName().toLowerCase().contains(text))||(d.getLocation().toLowerCase().contains(text)||d.getLocation().toLowerCase().contains(text))||(d.getCat_name().toLowerCase().contains(text)||d.getCat_name().toLowerCase().contains(text))) {
                    temp.add(d);
                }
            }
            projectAdapter.setAdapterData(temp);
        }
        //update recyclerview

    }

    private void loadCarList() {
        if (propertyModelList != null && propertyModelList.size() > 0) {
            propertyModelList.clear();
        }
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, network.getProjects,
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
                                JSONArray heroArray = obj.getJSONArray("project_details");

                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject heroObject = heroArray.getJSONObject(i);

                                    //pid,name,location,cat_name,cost,size,description,project_image;
                                    PropertyModel hero = new PropertyModel(heroObject.getString("id"),
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
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    AsyncResult<PropertyModel> asyncResult_addNewConnection1 = new AsyncResult<PropertyModel>() {
        @Override
        public void success(PropertyModel  click) {
            ImageId=click.getPid();
            Intent intent = new Intent(ProjectListActivity.this, ProjectDetailActivity.class);
              intent.putExtra("Model", click);
            startActivity(intent);

        }
        @Override
        public void error(String error) {

        }
    };
}
