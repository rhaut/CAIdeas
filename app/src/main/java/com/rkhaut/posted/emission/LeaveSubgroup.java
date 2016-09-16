package com.rkhaut.posted.emission;

import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class LeaveSubgroup {

    public static final String REQUEST = "leave_subgroup";
    public static final String RESULT = "leave_subgroup_result";

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";

    private static class LeaveSubgroupListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public LeaveSubgroupListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
            String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
            this.resultReceiver.leaveSubgroupResult(success, message);
        }
    }

    public static LeaveSubgroupListener Listener(Receiver resultReceiver) {
        return new LeaveSubgroupListener(resultReceiver);
    }

    public interface Receiver {
        void leaveSubgroupResult(Boolean success, String message);
    }
}
