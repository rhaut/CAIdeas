package com.rkhaut.posted.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.rkhaut.posted.emission.DeleteGroupEmission;
import com.rkhaut.posted.emission.JoinSubgroupEmission;
import com.rkhaut.posted.emission.LeaveSubgroupEmission;
import com.rkhaut.posted.emission.RefreshEmission;
import com.rkhaut.posted.emission.UpdatePositionEmission;
import com.rkhaut.posted.listener.ConnectionListener;
import com.rkhaut.posted.listener.DisconnectionListener;
import com.rkhaut.posted.emission.BaseEmission;
import com.rkhaut.posted.receiver.ConnectionReceiver;
import com.rkhaut.posted.receiver.CreateGroupReceiver;
import com.rkhaut.posted.receiver.DeleteGroupReceiver;
import com.rkhaut.posted.receiver.DisconnectionReceiver;
import com.rkhaut.posted.receiver.JoinSubgroupReceiver;
import com.rkhaut.posted.receiver.LeaveSubgroupReceiver;
import com.rkhaut.posted.receiver.LoginReceiver;
import com.rkhaut.posted.receiver.RefreshReceiver;
import com.rkhaut.posted.receiver.RegisterReceiver;
import com.rkhaut.posted.util.SessionManager;
import com.rkhaut.posted.emission.CreateGroupEmission;
import com.rkhaut.posted.emission.RegisterEmission;
import com.rkhaut.posted.emission.LoginEmission;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class PostService extends Service implements ConnectionReceiver, DisconnectionReceiver, RefreshReceiver {

    private static final String TAG = "PostService";
    private static final String ADDRESS = "";

    private static Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if(PostService.socket == null || !PostService.socket.connected()) {
                PostService.socket = IO.socket(ADDRESS);
                PostService.socket.on(Socket.EVENT_CONNECT, new ConnectionListener(this));
                PostService.socket.on(Socket.EVENT_DISCONNECT, new DisconnectionListener(this));
                PostService.socket.connect();
            }
        } catch (URISyntaxException e) {
            Log.v(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(PostService.socket != null && PostService.socket.connected()) {
            PostService.socket.close();
        }
    }

    public void register(String username, String password, RegisterReceiver resultReceiver) {
        emitAndListen(new RegisterEmission(username, password, resultReceiver));
    }

    public void login(String username, String password, LoginReceiver resultReceiver) {
        emitAndListen(new LoginEmission(username, password, resultReceiver));
    }

    public void refresh(String username, String token, RefreshReceiver resultReceiver) {
        emitAndListen(new RefreshEmission(username, token, resultReceiver));
    }

    public void createGroup(String name, CreateGroupReceiver resultCreateGroupReceiver) {
        emitAndListen(new CreateGroupEmission(name, resultCreateGroupReceiver));
    }

    public void joinSubgroup(int subgroupId, JoinSubgroupReceiver resultReceiver) {
        emitAndListen(new JoinSubgroupEmission(subgroupId, resultReceiver));
    }

    public void leaveSubgroup(LeaveSubgroupReceiver resultReceiver) {
        emitAndListen(new LeaveSubgroupEmission(resultReceiver));
    }

    public void deleteGroup(int groupId, DeleteGroupReceiver resultReceiver) {
        emitAndListen(new DeleteGroupEmission(groupId, resultReceiver));
    }

    public void updatePosition(double longitude, double latitude) {
        emit(new UpdatePositionEmission(longitude, latitude));
    }

    @Override
    public void connectResult() {
        String username = SessionManager.getUsername(this);
        String token = SessionManager.getToken(this);
        if(username != null && token != null) {
            Log.v(TAG, "Attempting RefreshEmission");
            refresh(username, token, this);
        } else {
            Log.v(TAG, "No Session Saved");
        }
    }

    @Override
    public void disconnectResult() {

    }

    @Override
    public void refreshResult(Boolean success, String message, String username, String token) {
        if(success) {
            SessionManager.saveSession(this, username, token);
        } else {
            SessionManager.removeSession(this);
        }
    }

    private void emitAndListen(BaseEmission emission) {
        socket.once(emission.result, emission.listener);
        if(emission.args != null) {
            socket.emit(emission.request, emission.args);
        } else {
            socket.emit(emission.request);
        }
    }

    private void emit(BaseEmission emission) {
        socket.emit(emission.request, emission.args);
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public PostService getService() {
            return PostService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
