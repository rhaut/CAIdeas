package com.rkhaut.posted.emission;


import com.rkhaut.posted.listener.RefreshListener;
import com.rkhaut.posted.receiver.RefreshReceiver;

import org.json.JSONObject;

import java.util.HashMap;

public class RefreshEmission extends BaseEmission {

    private static final String REQUEST = "refresh";
    private static final String RESULT = "refresh_result";

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_TOKEN = "token";

    public RefreshEmission(String username, String token, RefreshReceiver resultReceiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.args = generateArgs(username, token);
        this.listener = new RefreshListener(resultReceiver);
    }

    private JSONObject generateArgs(String username, String token) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_USERNAME, username);
        args.put(PARAMETER_TOKEN, token);
        return new JSONObject(args);
    }
}
