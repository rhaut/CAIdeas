package com.rkhaut.posted.activity;

import android.util.Log;

import com.rkhaut.posted.receiver.CreateGroupReceiver;
import com.rkhaut.posted.receiver.DeleteGroupReceiver;
import com.rkhaut.posted.receiver.JoinSubgroupReceiver;
import com.rkhaut.posted.receiver.LeaveSubgroupReceiver;
import com.rkhaut.posted.receiver.LoginReceiver;
import com.rkhaut.posted.receiver.RefreshReceiver;
import com.rkhaut.posted.receiver.RegisterReceiver;

public abstract class BaseEchoActivity extends BaseServiceActivity implements LoginReceiver, RegisterReceiver, RefreshReceiver, CreateGroupReceiver, JoinSubgroupReceiver, LeaveSubgroupReceiver, DeleteGroupReceiver {

    private static final String TAG = "Echo";

    public void login(String username, String password) {
        getService().login(username, password, this);
    }

    @Override
    public void loginResult(Boolean success, String message, String username, String token) {
        Log.v(TAG, message);
    }

    public void register(String username, String password) {
        getService().register(username, password, this);
    }

    @Override
    public void registerResult(Boolean success, String message) {
        Log.v(TAG, message);
    }

    public void refresh(String username, String token) {
        getService().refresh(username, token, this);
    }

    @Override
    public void refreshResult(Boolean success, String message, String username, String token) {
        Log.v(TAG, message);
    }

    public void createGroup(String groupName) {
        getService().createGroup(groupName, this);
    }

    @Override
    public void createGroupResult(Boolean success, String message, int groupId) {
        Log.v(TAG, message);
    }

    public void joinSubgroup(int subgroupId) {
        getService().joinSubgroup(subgroupId, this);
    }

    @Override
    public void joinSubgroupResult(Boolean success, String message) {
        Log.v(TAG, message);
    }

    public void leaveSubgroup() {
        getService().leaveSubgroup(this);
    }

    @Override
    public void leaveSubgroupResult(Boolean success, String message) {
        Log.v(TAG, message);
    }

    public void deleteGroup(int groupId) {
        getService().deleteGroup(groupId, this);
    }

    @Override
    public void deleteGroupResult(Boolean success, String message) {
        Log.v(TAG, message);
    }
}
