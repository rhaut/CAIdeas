package com.rkhaut.posted.emission;

import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;

import io.socket.emitter.Emitter;

public class DeleteGroup {

    public static final String REQUEST = "delete_group";
    public static final String RESULT = "delete_group_result";

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";

    private static final String PARAMETER_GROUP_ID = "group_id";

    private static class DeleteGroupListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public DeleteGroupListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
            String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
            this.resultReceiver.deleteGroupResult(success, message);
        }
    }

    public static JSONObject Args(int groupId) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_GROUP_ID, groupId);
        return new JSONObject(args);
    }

    public static DeleteGroupListener Listener(Receiver resultReceiver) {
        return new DeleteGroupListener(resultReceiver);
    }

    public interface Receiver {
        void deleteGroupResult(Boolean success, String message);
    }
}
