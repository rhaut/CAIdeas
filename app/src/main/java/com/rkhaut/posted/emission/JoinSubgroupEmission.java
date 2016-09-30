package com.rkhaut.posted.emission;

import com.rkhaut.posted.listener.JoinSubgroupListener;
import com.rkhaut.posted.receiver.JoinSubgroupReceiver;

import org.json.JSONObject;

import java.util.HashMap;

public class JoinSubgroupEmission extends BaseEmission {

    private static final String REQUEST = "join_subgroup";
    private static final String RESULT = "join_subgroup_result";

    private static final String PARAMETER_SUBGROUP_ID = "subgroup_id";

    public JoinSubgroupEmission(int subgroupId, JoinSubgroupReceiver resultReceiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.args = generateArgs(subgroupId);
        this.listener = new JoinSubgroupListener(resultReceiver);
    }

    private JSONObject generateArgs(int subgroupId) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_SUBGROUP_ID, subgroupId);
        return new JSONObject(args);
    }
}
