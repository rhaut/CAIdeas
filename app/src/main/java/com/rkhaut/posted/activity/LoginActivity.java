package com.rkhaut.posted.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rkhaut.posted.R;
import com.rkhaut.posted.util.SessionManager;

public class LoginActivity extends BaseEchoActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button action = (Button)findViewById(R.id.action);
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //register("Richard", "1z2x3c");
        login("Richard", "1z2x3c");
        //joinSubgroup(11);
    }

    @Override
    public void registerResult(Boolean success, String message) {
        if(success) {
            Log.v(TAG, "Registered");
            login("Richard", "1z2x3c");
        }
    }

    @Override
    public void loginResult(Boolean success, String message, String username, String token) {
        if(success) {
            Log.v(TAG, "Saving Session");
            SessionManager.saveSession(this, username, token);
            //createGroup("Test Group");
            //joinSubgroup(1);
            //leaveSubgroup();
        } else {
            Log.v(TAG, message);
        }
    }
}
