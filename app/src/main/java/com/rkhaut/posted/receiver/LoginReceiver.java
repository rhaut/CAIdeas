package com.rkhaut.posted.receiver;

public interface LoginReceiver {
    void loginResult(Boolean success, String message, String username, String token);
}