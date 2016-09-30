package com.rkhaut.posted.emission;

import com.rkhaut.posted.listener.CreateGroupListener;
import com.rkhaut.posted.receiver.CreateGroupReceiver;

import org.json.JSONObject;

import java.util.HashMap;

public class CreateGroupEmission extends BaseEmission {

    private static final String REQUEST = "create_group";
    private static final String RESULT = "create_group_result";
    private static final String PARAMETER_NAME = "name";

    public CreateGroupEmission(String name, CreateGroupReceiver createGroupReceiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.args = generateArgs(name);
        this.listener = new CreateGroupListener(createGroupReceiver);
    }

    private JSONObject generateArgs(String name) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_NAME, name);
        return new JSONObject(args);
    }
}
