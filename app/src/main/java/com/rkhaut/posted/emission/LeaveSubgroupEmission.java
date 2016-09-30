package com.rkhaut.posted.emission;

import com.rkhaut.posted.listener.LeaveSubgroupListener;
import com.rkhaut.posted.receiver.LeaveSubgroupReceiver;

public class LeaveSubgroupEmission extends BaseEmission {

    private static final String REQUEST = "leave_subgroup";
    private static final String RESULT = "leave_subgroup_result";

    public LeaveSubgroupEmission(LeaveSubgroupReceiver receiver) {
        this.request = REQUEST;
        this.result = RESULT;
        this.listener = new LeaveSubgroupListener(receiver);
    }
}
