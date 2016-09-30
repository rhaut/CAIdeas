package com.rkhaut.posted.listener;

import com.rkhaut.posted.receiver.DeleteGroupReceiver;
import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class DeleteGroupListener implements Emitter.Listener {

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";

    private final DeleteGroupReceiver resultReceiver;

    public DeleteGroupListener(DeleteGroupReceiver resultReceiver) {
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