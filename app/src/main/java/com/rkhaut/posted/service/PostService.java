package com.rkhaut.posted.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.rkhaut.posted.emission.Connect;
import com.rkhaut.posted.emission.DeleteGroup;
import com.rkhaut.posted.emission.Disconnect;
import com.rkhaut.posted.emission.JoinSubgroup;
import com.rkhaut.posted.emission.LeaveSubgroup;
import com.rkhaut.posted.emission.UpdatePosition;
import com.rkhaut.posted.util.SessionManager;
import com.rkhaut.posted.emission.CreateGroup;
import com.rkhaut.posted.emission.Refresh;
import com.rkhaut.posted.emission.Register;
import com.rkhaut.posted.emission.Login;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PostService extends Service implements Connect.Receiver, Disconnect.Receiver, Refresh.Receiver {

    private static final String TAG = "PostService";
    private static final String ADDRESS = "";

    private static Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            if(PostService.socket == null || !PostService.socket.connected()) {
                PostService.socket = IO.socket(ADDRESS);
                PostService.socket.on(Socket.EVENT_CONNECT, Connect.Listener(this));
                PostService.socket.on(Socket.EVENT_DISCONNECT, Disconnect.Listener(this));
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

    public void register(String username, String password, Register.Receiver resultReceiver) {
        emitAndListen(
                Register.REQUEST,
                Register.RESULT,
                Register.Args(username, password),
                Register.Listener(resultReceiver));
    }

    public void login(String username, String password, Login.Receiver resultReceiver) {
        emitAndListen(
                Login.REQUEST,
                Login.RESULT,
                Login.Args(username, password),
                Login.Listener(resultReceiver));
    }

    public void refresh(String username, String token, Refresh.Receiver resultReceiver) {
        emitAndListen(
                Refresh.REQUEST,
                Refresh.RESULT,
                Refresh.Args(username, token),
                Refresh.Listener(resultReceiver));
    }

    public void createGroup(String name, CreateGroup.Receiver resultReceiver) {
        emitAndListen(
                CreateGroup.REQUEST,
                CreateGroup.RESULT,
                CreateGroup.Args(name),
                CreateGroup.Listener(resultReceiver));
    }

    public void joinSubgroup(int subgroupId, JoinSubgroup.Receiver resultReceiver) {
        emitAndListen(
                JoinSubgroup.REQUEST,
                JoinSubgroup.RESULT,
                JoinSubgroup.Args(subgroupId),
                JoinSubgroup.Listener(resultReceiver));
    }

    public void leaveSubgroup(LeaveSubgroup.Receiver resultReceiver) {
        emitAndListen(
                LeaveSubgroup.REQUEST,
                LeaveSubgroup.RESULT,
                LeaveSubgroup.Listener(resultReceiver));
    }

    public void deleteGroup(int groupId, DeleteGroup.Receiver resultReceiver) {
        emitAndListen(
                DeleteGroup.REQUEST,
                DeleteGroup.RESULT,
                DeleteGroup.Args(groupId),
                DeleteGroup.Listener(resultReceiver));
    }

    public void updatePosition(double longitude, double latitude) {
        socket.emit(
                UpdatePosition.REQUEST,
                UpdatePosition.Args(latitude, longitude));
    }

    @Override
    public void connectResult() {
        String username = SessionManager.getUsername(this);
        String token = SessionManager.getToken(this);
        if(username != null && token != null) {
            Log.v(TAG, "Attempting Refresh");
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

    private void emitAndListen(String request, String result, JSONObject args, Emitter.Listener listener) {
        socket.once(result, listener);
        socket.emit(request, args);
    }

    private void emitAndListen(String request, String result, Emitter.Listener listener) {
        socket.once(result, listener);
        socket.emit(request);
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
