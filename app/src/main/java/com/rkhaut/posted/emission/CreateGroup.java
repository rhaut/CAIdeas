package com.rkhaut.posted.emission;

import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;

import io.socket.emitter.Emitter;

public class CreateGroup {

    public static final String REQUEST = "create_group";
    public static final String RESULT = "create_group_result";

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";
    private static final String RESULT_GROUP_ID = "group_id";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";
    private static final Integer DEFAULT_GROUP_ID = -1;

    private static final String PARAMETER_NAME = "name";

    private static class CreateGroupListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public CreateGroupListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
            String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
            Integer groupId = JSONParser.getInt(response, RESULT_GROUP_ID, DEFAULT_GROUP_ID);
            this.resultReceiver.createGroupResult(success, message, groupId);
        }
    }

    public static JSONObject Args(String name) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_NAME, name);
        return new JSONObject(args);
    }

    public static CreateGroupListener Listener(Receiver resultReceiver) {
        return new CreateGroupListener(resultReceiver);
    }

    public interface Receiver {
        void createGroupResult(Boolean success, String message, int groupId);
    }
}
