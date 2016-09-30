package com.rkhaut.posted.receiver;

public interface CreateGroupReceiver {
    void createGroupResult(Boolean success, String message, int groupId);
}
