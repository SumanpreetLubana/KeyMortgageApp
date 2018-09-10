package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignInActivity extends AppCompatActivity {
Button signin;
SessionManager sessionManager;
ProgressDialog progressDialog;
EditText email,password;
Network network;
String Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        progressDialog =new ProgressDialog(this);
        signin =(Button)findViewById(R.id.signIn);
        sessionManager=new SessionManager(this);
        network =new Network();
        sessionManager= new SessionManager(this);
        email= (EditText)findViewById(R.id.email);
        password= (EditText)findViewById(R.id.password);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = email.getText().toString();
                Password = password.getText().toString();
                if(Email!=null && Password!=null){
                    progressDialog.show();
                    Login();
                }else{
                    email.setError("Please enter email.");
                    password.setError("Please enter password.");
                }

            }
        });


    }
    private void Login() {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strRequest = new StringRequest(Request.Method.POST, network.getLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String resposne_stat = jsonObject.getString("status");
                            String resposne_message = jsonObject.getString("message");
                            // resposne_stat = resposne_stat.trim();
                             progressDialog.hide();
                              Log.e("Status", resposne_stat);
                            if (resposne_stat.equals("true")) {
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
                                JSONObject resposne_userId = jsonObject.getJSONObject("user");
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);

                               String User_ID = resposne_userId.getString("id");
                               String user_role = resposne_userId.getString("user_role");

                                sessionManager.createLoginSession(user_role,"userRole");
                                sessionManager.createLoginSession(User_ID,"userID");
                                Intent intent = new Intent(SignInActivity.this, UserDashboardActivity.class);
                                startActivity(intent);
                            } else {

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
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Email);
                params.put("password", Password);
                return params;
            }
        };

        //queue.add(strRequest);


        Volley.newRequestQueue(getApplicationContext()).add(strRequest);
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

}
