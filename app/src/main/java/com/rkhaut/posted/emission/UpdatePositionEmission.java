package com.rkhaut.posted.emission;

import org.json.JSONObject;

import java.util.HashMap;

public class UpdatePositionEmission extends BaseEmission {

    private static final String REQUEST = "update_position";

    private static final String PARAMETER_LONGITUDE = "longitude";
    private static final String PARAMETER_LATITUDE = "latitude";

    public UpdatePositionEmission(double longitude, double latitude) {
        this.request = REQUEST;
        this.args = Args(longitude, latitude);
    }

    private JSONObject Args(double latitude, double longitude) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(PARAMETER_LATITUDE, latitude);
        args.put(PARAMETER_LONGITUDE, longitude);
        return new JSONObject(args);
    }
}
