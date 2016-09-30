package com.rkhaut.posted.emission;


import org.json.JSONObject;

import io.socket.emitter.Emitter;

public abstract class BaseEmission {

    public String request;
    public String result;
    public JSONObject args;
    public Emitter.Listener listener;
}
