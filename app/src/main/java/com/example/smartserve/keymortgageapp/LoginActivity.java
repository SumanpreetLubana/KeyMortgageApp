package com.example.smartserve.keymortgageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
EditText et1,et2;
String Email_Id,Password;
LoginButton loginButton;
    private SignInButton btnSignIn;
    private static final String TAG = SignInActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private CallbackManager callbackManager;
    ProgressDialog progressDialog;
    public static final String KEY_USERID= "userID";
    public static final String KEY_Name = "name";
    public static final String KEY_EMAIL = "email";
    private GoogleApiClient mGoogleApiClient;
    String personName,email1,id;
    SessionManager sessionManager;
    Network network;
    HashMap<String,String> user;
    String deviceId;
    String User_ID;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);

       // callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
       loginButton = (LoginButton) findViewById(R.id.login_button);
       textView = (TextView) findViewById(R.id.salesPErson);
        progressDialog =new ProgressDialog(this);
        network =new Network();
        sessionManager= new SessionManager(this);
        user = new HashMap<String, String>();
        user= sessionManager.getUserDetails();

        //phoneNumber = user.get("phoneNumber");
        deviceId = user.get("deviceID");

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFb();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(LoginActivity.this,SignInActivity.class);
            startActivity(intent);
            }
        });

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());
            personName = acct.getDisplayName();
            id = acct.getId();
            email1 = acct.getEmail();
            signIn(personName, email1, id,"G");
            Log.e(TAG, "Name: " + personName + ", email: " + email1);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            // btnSignOut.setVisibility(View.VISIBLE);
            // btnRevokeAccess.setVisibility(View.VISIBLE);
            // llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            //  btnSignOut.setVisibility(View.GONE);
            // btnRevokeAccess.setVisibility(View.GONE);
            //  llProfileLayout.setVisibility(View.GONE);
        }
    }
    private void signIn(final String PersonName, final String Email, final String Id, final String type) {
             showProgressDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = Network.Base_Url + Network.Social_Login;
        Log.e("url", URL);
        StringRequest strRequest = new StringRequest(Request.Method.POST, URL,
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
                                JSONObject resposne_userId = jsonObject.getJSONObject("user");
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);

                                User_ID = resposne_userId.getString("id");

//                                String isOtp_Verified = resposne_userId.getString("otp_verify");
                                String userName =resposne_userId.getString("name");
                                String userEmail =resposne_userId.getString("email");
                                sessionManager.createLoginSession(User_ID,KEY_USERID);
                                Log.e("UserId",User_ID+" "+userName+" "+userEmail);
                                sessionManager.createLoginSession(userName, KEY_Name);
                                sessionManager.createLoginSession(userEmail, KEY_EMAIL);

                                //progressBar.setVisibility(View.GONE); otp_verify
                                // String User_ID = resposne_userId.getString("id");
                              //  if(isOtp_Verified.equals("0")) {

                                registerId();
                              /*  }else{
                                    setDeviceId(User_ID);
                                }*/
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
                if(type.equals("G")) {
                    params.put("gmail_id", Id);
                }else{
                    params.put("facebook_id", Id);
                }
                params.put("login_type", type);
                params.put("name", PersonName);
                params.put("email", Email);
                return params;
            }
        };

        //queue.add(strRequest);


        Volley.newRequestQueue(getApplicationContext()).add(strRequest);
    }
    private void registerId() {
        showProgressDialog();
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL ="http://keymortgage.co/app/api/user/registerDevice";
        Log.e("url", URL);
        StringRequest strRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String resposne_stat = jsonObject.getString("status");
                            String resposne_message = jsonObject.getString("message");
                            // resposne_stat = resposne_stat.trim();
                            hideProgressDialog();
                            Log.e("Status", resposne_stat);
                            if (resposne_stat.equals("true")) {
                                Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
                                //  intent.putExtra("Is_type", "1");
                                sessionManager.createLoginSession("0","userRole");
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
                params.put("user_id", User_ID);
                params.put("device_id", deviceId);
                params.put("device_type", "A");
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    public void loginWithFb(){
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.e("DataFb32323", "==" + object.toString() + "///" + response.toString());

                                try {
                                    String fname = object.getString("first_name");
                                    String email = object.getString("email");
                                    String fb_id = object.getString("id");
                                    String name = object.getString("name");
                                    String t = name.replaceAll("\\s+", "");
                                    signIn(name,email,fb_id,"F");
                                    //String urll = App.FACEBOOK_URL + "fb_id=" + fb_id + "&email=" + email + "&name=" + t;

                                    //    Log.e("urll", "==" + urll + "///");
                                    Log.e("DataFb32323", "==" + object.toString() + "///" + response.toString());
                                    //   loginapp(urll);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Application code
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        });
    }
}
