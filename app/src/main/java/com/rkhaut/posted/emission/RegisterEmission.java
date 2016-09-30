package com.rkhaut.posted.emission;

import com.rkhaut.posted.listener.RegisterListener;
import com.rkhaut.posted.receiver.RegisterReceiver;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterEmission extends BaseEmission {

    private static final String REQUEST = "register";
    private static final String RESULT = "register_result";

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_PASSWORD = "password";

    public RegisterEmission(String username, String password, RegisterReceiver resultReceiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.args = generateArgs(username, password);
        this.listener = new RegisterListener(resultReceiver);
    }

    private JSONObject generateArgs(String username, String password) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_USERNAME, username);
        args.put(PARAMETER_PASSWORD, password);
        return new JSONObject(args);
    }
}
