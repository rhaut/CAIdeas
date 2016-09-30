package com.rkhaut.posted.emission;

import com.rkhaut.posted.listener.LoginListener;
import com.rkhaut.posted.receiver.LoginReceiver;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginEmission extends BaseEmission {

    private static final String REQUEST = "login";
    private static final String RESULT = "login_result";

    private static final String PARAMETER_USERNAME = "username";
    private static final String PARAMETER_PASSWORD = "password";

    public LoginEmission(String username, String password, LoginReceiver resultReceiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.args = generateArgs(username, password);
        this.listener = new LoginListener(resultReceiver);
    }

    private JSONObject generateArgs(String username, String password) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_USERNAME, username);
        args.put(PARAMETER_PASSWORD, password);
        return new JSONObject(args);
    }
}