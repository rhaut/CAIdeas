package com.rkhaut.posted.receiver;

public interface RefreshReceiver {
    void refreshResult(Boolean success, String message, String username, String token);
}