package com.bharathmg.irctc.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.bharathmg.irctc.utils.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NetworkActionsTask<String, Integer, Object> extends AsyncTask<String, Integer, Object> {

    Activity activity;
    public NetworkActionsTask(Activity activity){
        this.activity = activity;
    }


    @Override
    protected Object doInBackground(String... strings) {
        String url = strings[0];
        try {
            StringBuilder sb = CommunicationManager.INSTANCE.getRequest(url.toString());
            JSONObject json = new JSONObject(sb.toString());
            System.out.println(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}