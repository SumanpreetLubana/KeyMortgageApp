package com.example.smartserve.keymortgageapp.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.example.smartserve.keymortgageapp.LoginActivity;

import java.util.HashMap;


public class SessionManager {

    // Condittion for password and confirm password
    //if(password.getText().toString().equals(confirmpassword.getText().toString())

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MyPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_Name = "name";
    public static final String KeY_Fname = "fname";
    public static final String Key_Lname = "lname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_DATEBIRTH= "dateBirth";
    public static final String KEY_USERID= "userID";
    public static final String KEY_IDENTITY_USERID= "identity_userID";
    public static final String QR_Image= "Qr_Image";
    public static final String Status_Code = "statusCode";
    public static final String deviceID = "deviceID";
    public static final String isNotfy = "is_notification";
    public static final String userRole = "userRole";
    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String phoneNumber,String Key) {

        if(Key.equals("fname")) {
            editor.putString(KeY_Fname, phoneNumber);
        }else  if(Key.equals("lname")) {
            editor.putString(Key_Lname, phoneNumber);
        }else if(Key.equals("dateBirth")) {
            editor.putString(KEY_DATEBIRTH, phoneNumber);
        }else if(Key.equals("userID")) {
            editor.putString(KEY_USERID, phoneNumber);
        }else if(Key.equals("email")) {
            editor.putString(KEY_EMAIL, phoneNumber);
        }else if(Key.equals("name")) {
            editor.putString(KEY_Name, phoneNumber);
        }else if(Key.equals("phoneNumber")) {
            editor.putString(KEY_PHONENUMBER, phoneNumber);
        }else if(Key.equals("Qr_Image")) {
            editor.putString(QR_Image, phoneNumber);
        }else if(Key.equals("statusCode")) {
            editor.putString(Status_Code, phoneNumber);
        }else if(Key.equals("identity_userID")) {
            editor.putString(KEY_IDENTITY_USERID, phoneNumber);
        }else if(Key.equals("deviceID")) {
            editor.putString(deviceID, phoneNumber);
        }
        else if(Key.equals("is_notification")) {
            editor.putString(isNotfy, phoneNumber);
        }
        else if(Key.equals("userRole")) {
            editor.putString(userRole, phoneNumber);
        }
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (this.isLoggedIn() == true) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        //user.put(KEY_ID, pref.getString(KEY_ID, null));
        // user email id
        user.put(KEY_Name, pref.getString(KEY_Name, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(Key_Lname, pref.getString(Key_Lname, null));
        user.put(KeY_Fname, pref.getString(KeY_Fname, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_PHONENUMBER, pref.getString(KEY_PHONENUMBER, null));
        user.put(QR_Image, pref.getString(QR_Image, null));
        user.put(Status_Code, pref.getString(Status_Code, null));
        user.put(KEY_IDENTITY_USERID, pref.getString(KEY_IDENTITY_USERID, null));
        user.put(deviceID, pref.getString(deviceID, null));
        user.put(isNotfy, pref.getString(isNotfy, null));
        user.put(userRole, pref.getString(userRole, null));
        //Log.e("DataSessionMAp", pref.getString(KeY_Fname, null) + pref.getString(KEY_EMAIL, null) + pref.getString(Key_Lname, null) + pref.getString(KEY_PASSWORD, null));
        // return user
        return user;
    }


    /**
     * Clear session details
     */
   /* public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Splash Activity
        Intent i = new Intent(_context, SplashActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }*/

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
