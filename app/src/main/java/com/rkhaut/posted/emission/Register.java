package com.rkhaut.posted.emission;

import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;

import io.socket.emitter.Emitter;

public class Register {

    public static final String REQUEST = "register";
    public static final String RESULT = "register_result";

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_PASSWORD = "password";

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";

    private static class RegisterListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public RegisterListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
            String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
            resultReceiver.registerResult(success, message);
        }
    }

    public static JSONObject Args(String username, String password) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_USERNAME, username);
        args.put(PARAMETER_PASSWORD, password);
        return new JSONObject(args);
    }

    public static RegisterListener Listener(Receiver resultReceiver) {
        return new RegisterListener(resultReceiver);
    }

    public interface Receiver {
        void registerResult(Boolean success, String message);
    }
}
