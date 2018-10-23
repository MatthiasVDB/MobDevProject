package be.pxl.kartingapp;

import org.json.JSONObject;

import java.util.List;

public interface CallbackInterface {
    void processFinished(List<String> names, List<String> addresses);
}
