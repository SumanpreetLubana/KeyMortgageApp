package com.example.smartserve.keymortgageapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.example.smartserve.keymortgageapp.Util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView grid;
    TextView showcalculator, tname, temail;
    SessionManager sessionManager;
    HashMap<String, String> user;
    Network network;
    ArrayList<HashMap<String, Object>> items;
    PackageManager pm;
    String headername, headermail;
    ProgressDialog progressDialog;
    String keyuserId,isNotify;
    List<PackageInfo> packs;
    public static final String CALCULATOR_PACKAGE = "com.example.smartserve.propertapp.keymortgage.myapplication.Activities";
    public static final String CALCULATOR_CLASS = "com.example.smartserve.propertapp.keymortgage.myapplication.Activities.UserDashboardActivity";
    String[] web = {
            "Notification / NewsFeed",
            "Project",
            "Bank",
            "Mortgage",
            "Legal Fees",
            "Stamping Fees",
            "Debt Service Ratio(DSR)",
            "Upload Documents"

    };
    String[] web1 = {
            "Notification / NewsFeed",
            "New Enquiries",
            "Mortgage",
            "Legal Fees",
            "Stamping Fees",
            "Debt Service Ratio(DSR)",
            "Upload Documents"

    };
    int[] imageId = {
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3,
            R.drawable.icon4,
            R.drawable.icon5,
            R.drawable.icon6,
            R.drawable.icon7,
            R.drawable.file,

    };
    int[] imageId1 = {
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon4,
            R.drawable.icon5,
            R.drawable.icon6,
            R.drawable.icon7,
            R.drawable.file,
    };

    CustomGrid adapter,adapter1,adapter2;
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        progressDialog = new ProgressDialog(this);
        network = new Network();
        grid = (GridView) findViewById(R.id.grid);
        showcalculator = (TextView) findViewById(R.id.showCalculator);
        sessionManager = new SessionManager(this);
        user = new HashMap<String, String>();
        user = sessionManager.getUserDetails();
        keyuserId = user.get("userID");
        isNotify = user.get("is_notification");
        userRole = user.get("userRole");

        if(userRole.equals("0")){
            adapter = new CustomGrid(UserDashboardActivity.this, web, imageId,isNotify);
        }else if(userRole.equals("2")){
            adapter = new CustomGrid(UserDashboardActivity.this, web1, imageId1,isNotify);
        }else{
            adapter = new CustomGrid(UserDashboardActivity.this, web1, imageId1,isNotify);
        }


    //    Log.e("Name", user.get("name").toString());

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               // Toast.makeText(UserDashboardActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
//                final Intent intent = null;
                switch (position) {
                    case 0:
                        if(userRole.equals("0")){
                            Intent intent4 = new Intent(UserDashboardActivity.this, NotificationActivity.class);
                            sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, NotificationActivity.class);
                       //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }

                        break;

                    case 1:
                        if(userRole.equals("0")){
                            Intent intent4 = new Intent(UserDashboardActivity.this, ProjectListActivity.class);
                          //  sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }else if(userRole.equals("2")){
                            Intent intent4 = new Intent(UserDashboardActivity.this, NewEnquiryActivity.class);
                            startActivity(intent4);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, UsersListActivity.class);
                            //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }
                        break;
                    case 2:
                        if(userRole.equals("0")){
                            Intent intent6 = new Intent(UserDashboardActivity.this, BankListActivity.class);
                            startActivity(intent6);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, MonthlyPayment.class);
                            //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4); }

                        break;
                    case 3:
                        if(userRole.equals("0")){
                            Intent intent6 = new Intent(UserDashboardActivity.this, MonthlyPayment.class);
                            startActivity(intent6);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, SecondMonthlyPayment.class);
                            //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }

                        break;
                    case 4:
                        if(userRole.equals("0")){
                            Intent intent6 = new Intent(UserDashboardActivity.this, SecondMonthlyPayment.class);
                            startActivity(intent6);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, StampingFeesActivity.class);
                            //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }
                        break;
                    case 5:
                        if(userRole.equals("0")){
                            Intent intent6 = new Intent(UserDashboardActivity.this, StampingFeesActivity.class);
                            startActivity(intent6);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, DebtServiceActivity.class);
                            //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }
                        break;
                    case 6:
                        if(userRole.equals("0")){
                            Intent intent6 = new Intent(UserDashboardActivity.this, DebtServiceActivity.class);
                            startActivity(intent6);
                        }else{
                            Intent intent4 = new Intent(UserDashboardActivity.this, UploadDocActivity.class);
                            //     sessionManager.createLoginSession("0","is_notification");
                            startActivity(intent4);
                        }
                        break;
                    case 7:
                        if(userRole.equals("0")){
                            Intent intent7 = new Intent(UserDashboardActivity.this, UploadDocActivity.class);
                            startActivity(intent7);
                        }else {
                            }
                        break;
                    default:
                        break;
                }

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
// Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        tname = (TextView) headerView.findViewById(R.id.personname);
        temail = (TextView) headerView.findViewById(R.id.personemail);
        getPublicDetails();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SessionManager sessionManager1 = new SessionManager(this);
        HashMap<String, String> user1 = new HashMap<String, String>();
        user1 = sessionManager1.getUserDetails();

        String IsNotification1 =user1.get("is_notification");

       // adapter.isNotification(IsNotification1);

        if(userRole.equals("0")){
            adapter1 = new CustomGrid(UserDashboardActivity.this, web, imageId,isNotify);
            grid.setAdapter(adapter1);
        }else{
            adapter1 = new CustomGrid(UserDashboardActivity.this, web1, imageId1,isNotify);
            grid.setAdapter(adapter1);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        SessionManager sessionManager1 = new SessionManager(this);
        HashMap<String, String> user1 = new HashMap<String, String>();
        user1 = sessionManager1.getUserDetails();

        String IsNotification1 =user1.get("is_notification");

      //  adapter.isNotification(IsNotification1);
        if(userRole.equals("0")){
            adapter2 = new CustomGrid(UserDashboardActivity.this, web, imageId,isNotify);
            grid.setAdapter(adapter2);
        }else{
            adapter2 = new CustomGrid(UserDashboardActivity.this, web1, imageId1,isNotify);
            grid.setAdapter(adapter2);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(UserDashboardActivity.this, ProfileActivity.class);
            //  intent.putExtra("Is_type", "1");
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.added_bank) {

            Intent intent = new Intent(UserDashboardActivity.this, ShowBankActivity.class);
            //  intent.putExtra("Is_type", "1");
            startActivity(intent);
        } else if (id == R.id.user_project) {
            Intent intent = new Intent(UserDashboardActivity.this, ShowProjectActivity.class);
            //  intent.putExtra("Is_type", "1");
            startActivity(intent);

        }/* else if (id == R.id.nav_manage) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void opencalculator() {
        int d = 0;
        if (items.size() >= 1) {
            int j = 0;
            for (j = 0; j < items.size(); j++) {
                String AppName = (String) items.get(j).get("appName");
// Log.w("Name",""+AppName);
                if (AppName.matches("Calculator")) {
                    d = j;
                    break;
                }
            }
            String packageName = (String) items.get(d).get("packageName");

            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null) {
                //Toast.makeText(getApplicationContext(),"STARTING",Toast.LENGTH_SHORT).show();

                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "SORRY I CANT OPEN CALCULATOR :(", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "SORRY I CANT START CALCULATOR :(", Toast.LENGTH_SHORT).show();


        }
    }

    public void getPublicDetails() {
        showProgressDialog();
        RequestQueue queue = Volley.newRequestQueue(this);

        String URL = network.Base_Url + network.getUserProfile;
        Log.e("url", URL);
        // progressDialog.show();

        StringRequest jsObjRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        String responsemessage = null;
                        hideProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String resposne_sucess = jsonObject.getString("status");
                            String resposne_message = jsonObject.getString("message");
                            if (resposne_sucess.equals("true")) {

                                JSONObject resposne_userId = jsonObject.getJSONObject("user");
                                headername = resposne_userId.getString("name");
                                headermail = resposne_userId.getString("email");
                                tname.setText(headername);
                                temail.setText(headermail);
                            //    Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);

                                Log.e("UserID", keyuserId);
                            } else {
//                                hideProgressDialog();
                             //   Toast.makeText(getApplicationContext(), resposne_message, Toast.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
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
}
