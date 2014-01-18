package com.bharathmg.irctc;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bharathmg.irctc.tasks.NetworkActionsLoader;
import com.bharathmg.irctc.utils.HMACGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by bharathmg on 11/01/14.
 */
public class PNRFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<JSONObject> {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_pnr, container,
				false);
		Button pnr_button = (Button) rootView.findViewById(R.id.pnr_button);
		final EditText pnr_text = (EditText) rootView
				.findViewById(R.id.editText);
		pnr_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String number = pnr_text.getText().toString();
				Bundle bundle = new Bundle();
				HMACGenerator generator = new HMACGenerator(
						"jsonb2b2c68a2085589793c55ea75f218328" + number,
						"2185656b756400d6220c8f80d5083835");
				System.out.println(generator.generateHMAC());
				// String inputForHMAC = "pnr"
				bundle.putString(
						"url",
						"http://pnrbuddy.com/api/check_pnr/pnr/"
								+ number
								+ "/format/json/pbapikey/b2b2c68a2085589793c55ea75f218328/pbapisign/"
								+ generator.generateHMAC());
				getLoaderManager()
						.restartLoader(1001, bundle, PNRFragment.this)
						.forceLoad();
			}
		});
		return rootView;
	}

	/*
	 * Process PNR response from http://pnrbuddy.com/hauth/api
	 */
	private void processResponse(JSONObject jsonObject) {
		try {
			String train_name = jsonObject.getString("train_name");
			String train_num = jsonObject.getString("train_num");
			String doj = jsonObject.getString("");
			String from_station_code = jsonObject.getJSONObject("from_station").getString("code");
			String from_station_name = jsonObject.getJSONObject("from_station").getString("name");
			
			String to_station_code = jsonObject.getJSONObject("to_station").getString("code");
			String to_station_name = jsonObject.getJSONObject("to_station").getString("name");
			
			String reserve_station_code = jsonObject.getJSONObject("reservation_upto").getString("code");
			String reserve_station_name = jsonObject.getJSONObject("reservation_upto").getString("name");
			
			String bdpoint_station_code = jsonObject.getJSONObject("boarding_point").getString("code");
			String bdpoint_station_name = jsonObject.getJSONObject("boarding_point").getString("name");
			
			String class_name = jsonObject.getString("class");
			String no_passengers = jsonObject.getString("no_of_passengers");
			String chart_prep = jsonObject.getString("chart_prepared");
			JSONArray passengers = jsonObject.getJSONArray("passengers");
			List<JSONObject> passengers_list =new LinkedList<JSONObject>();
			for(int i=0;i<passengers.length();i++){
				passengers_list.add(passengers.getJSONObject(i));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Loader<JSONObject> onCreateLoader(int i, Bundle bundle) {
		return new NetworkActionsLoader(this.getActivity(), bundle);
	}

	@Override
	public void onLoadFinished(Loader<JSONObject> jsonObjectLoader,
			JSONObject jsonObject) {
		if (jsonObject != null) {
			System.out.println(jsonObject.toString());
			processResponse(jsonObject);
		}
	}

	@Override
	public void onLoaderReset(Loader<JSONObject> jsonObjectLoader) {

	}
}
