package com.bharathmg.irctc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bharathmg.irctc.tasks.NetworkActionsLoader;
import com.bharathmg.irctc.utils.HMACGenerator;

import org.json.JSONObject;

/**
 * Created by bharathmg on 11/01/14.
 */
public class PNRFragment extends Fragment implements LoaderManager.LoaderCallbacks<JSONObject> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pnr, container, false);
        Button pnr_button = (Button) rootView.findViewById(R.id.pnr_button);
        final EditText pnr_text = (EditText) rootView.findViewById(R.id.editText);
        pnr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = pnr_text.getText().toString();
                Bundle bundle = new Bundle();
                HMACGenerator generator = new HMACGenerator("jsonb2b2c68a2085589793c55ea75f218328" + number,"2185656b756400d6220c8f80d5083835");
                System.out.println(generator.generateHMAC());
//                String inputForHMAC = "pnr"
                bundle.putString("url","http://pnrbuddy.com/api/check_pnr/pnr/"+number+ "/format/json/pbapikey/b2b2c68a2085589793c55ea75f218328/pbapisign/"+generator.generateHMAC());
                getLoaderManager().restartLoader(1001, bundle, PNRFragment.this).forceLoad();
            }
        });
        return rootView;
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int i, Bundle bundle) {
        return new NetworkActionsLoader(this.getActivity(),bundle);
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> jsonObjectLoader, JSONObject jsonObject) {
        if(jsonObject != null)
        {

        }
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> jsonObjectLoader) {

    }
}
