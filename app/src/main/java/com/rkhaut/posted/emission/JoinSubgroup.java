package com.rkhaut.posted.emission;

import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import java.util.HashMap;

import io.socket.emitter.Emitter;

public class JoinSubgroup {

    public static final String REQUEST = "join_subgroup";
    public static final String RESULT = "join_subgroup_result";

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";

    private static final String PARAMETER_SUBGROUP_ID = "subgroup_id";

    private static class JoinSubgroupListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public JoinSubgroupListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
            String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
            this.resultReceiver.joinSubgroupResult(success, message);
        }
    }

    public static JSONObject Args(int subgroupId) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_SUBGROUP_ID, subgroupId);
        return new JSONObject(args);
    }

    public static JoinSubgroupListener Listener(Receiver resultReceiver) {
        return new JoinSubgroupListener(resultReceiver);
    }

    public interface Receiver {
        void joinSubgroupResult(Boolean success, String message);
    }
}
