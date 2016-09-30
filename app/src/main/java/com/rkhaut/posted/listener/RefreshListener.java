package com.rkhaut.posted.listener;

import com.rkhaut.posted.receiver.RefreshReceiver;
import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class RefreshListener implements Emitter.Listener {

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";
    private static final String RESULT_USERNAME = "username";
    private static final String RESULT_TOKEN = "token";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";
    private static final String DEFAULT_USERNAME = null;
    private static final String DEFAULT_TOKEN = null;

    private final RefreshReceiver resultReceiver;

    public RefreshListener(RefreshReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    @Override
    public void call(Object... args) {
        JSONObject response = (JSONObject) args[0];
        Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
        String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
        String username = JSONParser.getString(response, RESULT_USERNAME, DEFAULT_USERNAME);
        String token = JSONParser.getString(response, RESULT_TOKEN, DEFAULT_TOKEN);
        this.resultReceiver.refreshResult(success, message, username, token);
    }
}