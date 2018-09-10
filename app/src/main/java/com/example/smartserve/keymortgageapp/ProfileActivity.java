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
import com.example.smartserve.keymortgageapp.Util.Network;
import com.example.smartserve.keymortgageapp.Util.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    EditText et1,et2,et3,et5;
    ImageView back, close;
    TextView et4;
    SessionManager sessionManager;
    Network network;
    Button update;
    ProgressDialog progressDialog;
    HashMap<String,String> user;
    String keyuserId;
    String userid,fname,lname,phone,emailid,name;
    String userid1,fname1,lname1,phone1,emailid1,name1;
    String uname,uid,ufname,ulname,uemail,uphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        close = (ImageView) findViewById(R.id.close);
        back = (ImageView) findViewById(R.id.back);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UserDashboardActivity.class);
               // intent.putExtra("Model", click);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UserDashboardActivity.class);
                // intent.putExtra("Model", click);
                startActivity(intent);
            }
        });
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (TextView) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        update = (Button) findViewById(R.id.update);
        progressDialog =new ProgressDialog(this);
        network =new Network();
        sessionManager= new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();
        //phoneNumber = user.get("phoneNumber");
        keyuserId = user.get("userID");
        getDetails();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=et1.getText().toString();
                ufname=et2.getText().toString();
                ulname=et3.getText().toString();
                uemail=et4.getText().toString();
                uphone=et5.getText().toString();
               // uid = user.get("userID");
                UpdateUser();
            }
        });
    }
    public void getDetails()
    {
        showProgressDialog();
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = network.Base_Url + network.getUserProfile;
        Log.e("url", URL);
        // progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            JSONObject jsonObject= new JSONObject(response.toString());
                            String resposne_sucess = jsonObject.getString("status");
                            String resposne_message = jsonObject.getString("message");
                            if(resposne_sucess.equals("true"))
                            {

                                JSONObject resposne_userId = jsonObject.getJSONObject("user");
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
                                et5.setText(phone);
                             //   Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                                hideProgressDialog();
                                Log.e("UserID",keyuserId);
                            }
                            else{
//                                hideProgressDialog();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                hideProgressDialog();
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
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(jsObjRequest);
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
    public void UpdateUser()
    {
        showProgressDialog();
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = network.Base_Url + network.editProfile;
        Log.e("url", URL);
        // progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        try {
                            JSONObject jsonObject= new JSONObject(response.toString());
                            String resposne_sucess = jsonObject.getString("status");
                            String resposne_message = jsonObject.getString("message");
                            if(resposne_sucess.equals("true"))
                            {
                                JSONObject resposne_userId = jsonObject.getJSONObject("user");
                                userid1 = resposne_userId.getString("id");
                                name1 = resposne_userId.getString("name");
                                fname1 = resposne_userId.getString("first_name");
                                lname1 = resposne_userId.getString("last_name");
                                phone1 = resposne_userId.getString("phone");
                                et1.setText(name1);
                                et2.setText(fname1);
                                et3.setText(lname1);
//                                et4.setText(emailid1);
                                et5.setText(phone1);
                                hideProgressDialog();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                                Log.e("UserIDUpdation",keyuserId);
                            }
                            else{
//                                hideProgressDialog();
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                hideProgressDialog();
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
                params.put("name", uname);
                params.put("first_name", ufname);
                params.put("last_name", ulname);
                params.put("email", uemail);
                params.put("phone", uphone);
                params.put("user_id", keyuserId);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(jsObjRequest);
    }
}
