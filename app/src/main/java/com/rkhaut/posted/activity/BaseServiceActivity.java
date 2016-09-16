package com.rkhaut.posted.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.rkhaut.posted.service.PostService;

public abstract class BaseServiceActivity extends AppCompatActivity implements ServiceConnection {

    private ServiceConnection mConnection;
    private PostService mService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mConnection = this;
        this.mBound = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, PostService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isBound()) {
            unbindService(mConnection);
        }
    }

    PostService getService() {
        return mService;
    }

    private boolean isBound() {
        return mBound;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        PostService.LocalBinder binder = (PostService.LocalBinder) service;
        mService = binder.getService();
        mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mBound = false;
    }
}
