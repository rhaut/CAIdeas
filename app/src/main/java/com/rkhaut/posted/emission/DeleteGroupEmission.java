package com.rkhaut.posted.emission;

import com.rkhaut.posted.listener.DeleteGroupListener;
import com.rkhaut.posted.receiver.DeleteGroupReceiver;

import org.json.JSONObject;

import java.util.HashMap;

public class DeleteGroupEmission extends BaseEmission {

    private static final String REQUEST = "delete_group";
    private static final String RESULT = "delete_group_result";
    private static final String PARAMETER_GROUP_ID = "group_id";

    public DeleteGroupEmission(int groupId, DeleteGroupReceiver deleteGroupReceiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.args = generateArgs(groupId);
        this.listener = new DeleteGroupListener(deleteGroupReceiver);
    }

    private JSONObject generateArgs(int groupId) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_GROUP_ID, groupId);
        return new JSONObject(args);
    }
}
