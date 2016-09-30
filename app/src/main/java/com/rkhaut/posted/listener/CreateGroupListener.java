package com.rkhaut.posted.listener;

import com.rkhaut.posted.receiver.CreateGroupReceiver;
import com.rkhaut.posted.util.JSONParser;

import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class CreateGroupListener implements Emitter.Listener {

    private static final String RESULT_SUCCESS = "success";
    private static final String RESULT_MESSAGE = "message";
    private static final String RESULT_GROUP_ID = "group_id";

    private static final Boolean DEFAULT_SUCCESS = false;
    private static final String DEFAULT_MESSAGE = "FATAL ERROR";
    private static final Integer DEFAULT_GROUP_ID = -1;

    private final CreateGroupReceiver resultCreateGroupReceiver;

    public CreateGroupListener(CreateGroupReceiver resultCreateGroupReceiver) {
        this.resultCreateGroupReceiver = resultCreateGroupReceiver;
    }

    @Override
    public void call(Object... args) {
        JSONObject response = (JSONObject) args[0];
        Boolean success = JSONParser.getBoolean(response, RESULT_SUCCESS, DEFAULT_SUCCESS);
        String message = JSONParser.getString(response, RESULT_MESSAGE, DEFAULT_MESSAGE);
        Integer groupId = JSONParser.getInt(response, RESULT_GROUP_ID, DEFAULT_GROUP_ID);
        this.resultCreateGroupReceiver.createGroupResult(success, message, groupId);
    }
}
