package com.example.programtracker.data;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonHandler {

    public ArrayList<String> courseList;

    public String readJSONFromAsset(String fileName, Activity activity) {
        String jsonString = null;

        try {
            InputStream is = activity.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public JSONArray stringToJSON(String str) throws JSONException {

        JSONArray json = new JSONArray(str);
        return json;

    }

}
