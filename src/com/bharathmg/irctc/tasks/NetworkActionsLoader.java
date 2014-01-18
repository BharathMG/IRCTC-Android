package com.bharathmg.irctc.tasks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.bharathmg.irctc.utils.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by bharathmg on 12/01/14.
 */
public class NetworkActionsLoader extends AsyncTaskLoader<JSONObject> {

    Bundle bundle;
    public NetworkActionsLoader(Activity activity,Bundle bundle){
        super(activity);
        this.bundle = bundle;
    }
    @Override
    public JSONObject loadInBackground() {
        String url = bundle.getString("url");
        try {
            StringBuilder sb = CommunicationManager.INSTANCE.getRequest(url);
            JSONObject result_object = new JSONObject(sb.toString());
            return result_object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
