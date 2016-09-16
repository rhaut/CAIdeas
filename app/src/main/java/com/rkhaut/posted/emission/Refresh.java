package com.rkhaut.posted.emission;

import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;

import io.socket.emitter.Emitter;

public class Refresh {

    public static final String REQUEST = "refresh";
    public static final String RESULT = "refresh_result";

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_TOKEN = "token";

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";
    private static final String RESULT_USERNAME = "username";
    private static final String RESULT_TOKEN = "token";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";
    private static final String DEFAULT_USERNAME = null;
    private static final String DEFAULT_TOKEN = null;

    private static class RefreshListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public RefreshListener(Receiver resultReceiver) {
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

    public static JSONObject Args(String username, String token) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_USERNAME, username);
        args.put(PARAMETER_TOKEN, token);
        return new JSONObject(args);
    }

    public static RefreshListener Listener(Receiver resultReceiver) {
        return new RefreshListener(resultReceiver);
    }

    public interface Receiver {
        void refreshResult(Boolean success, String message, String username, String token);
    }
}
