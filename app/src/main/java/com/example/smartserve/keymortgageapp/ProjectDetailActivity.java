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
import android.widget.LinearLayout;
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
import com.example.smartserve.keymortgageapp.Models.PropertyModel;
import com.example.smartserve.keymortgageapp.Models.ShowProjectModel;
import com.example.smartserve.keymortgageapp.Util.Network;
import com.example.smartserve.keymortgageapp.Util.SessionManager;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProjectDetailActivity extends AppCompatActivity {
PropertyModel userDetailModel;
Network network;
SessionManager sessionManager;
ImageView close,back;
    ProgressDialog progressDialog;
    public TextView name, location, cat, cost, size, description;
    LinearLayout line;
    // ImageView imageView;
    //CustomImageView imageView;
    RoundedImageView imageView;
    HashMap<String, String> user;
    Button book;
    String keyuserId,pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
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
        network =new Network();
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        keyuserId = user.get("userID");
        name = (TextView) findViewById(R.id.name);
        location = (TextView) findViewById(R.id.location);
        progressDialog =new ProgressDialog(this);
        cat = (TextView) findViewById(R.id.category);
        cost = (TextView) findViewById(R.id.cost);
        size = (TextView) findViewById(R.id.size);
        book = (Button) findViewById(R.id.book);
        description = (TextView) findViewById(R.id.desc);
        imageView =  (RoundedImageView ) findViewById(R.id.imageView);
        Intent intent=getIntent();
        userDetailModel = intent.getParcelableExtra("Model");
        pid=userDetailModel.getPid();
      //  Glide.with(this).load(userDetailModel.getProject_image()).into(imageView);

        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.property_default)
                        .error(R.drawable.property_default))
                .load(userDetailModel.getProject_image())
                .into(imageView);
        name.setText(userDetailModel.getName().toString());
        location.setText(userDetailModel.getLocation().toString());
        cat.setText(userDetailModel.getCat_name().toString());
        cost.setText(userDetailModel.getCost().toString());
        size.setText(userDetailModel.getSize().toString());
        description.setText(userDetailModel.getDescription().toString());
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetails();
            }
        });

    }
    public void getDetails()
    {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = network.BookProject;
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
                                Intent intent = new Intent(ProjectDetailActivity.this, ProjectListActivity.class);
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
                params.put("project_id", pid);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(jsObjRequest);
    }
}
