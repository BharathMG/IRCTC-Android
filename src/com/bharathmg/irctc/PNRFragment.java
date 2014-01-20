package com.bharathmg.irctc;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bharathmg.irctc.adapter.PNRPassengersAdapter;
import com.bharathmg.irctc.tasks.NetworkActionsLoader;
import com.bharathmg.irctc.utils.HMACGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by bharathmg on 11/01/14.
 */
public class PNRFragment extends ListFragment implements LoaderManager.LoaderCallbacks<JSONObject> {

	private TextView from_view;
	private TextView to_view;
	private TextView from_code;
	private TextView to_code;
	private TextView train_name_view;
	private TextView train_num_view;
	private TextView boarding_date_view;
	private TextView boarding_point_view;
	private TextView chart_view;
	private TextView reserved_view;
	private TextView class_view;
	private TextView header_text;
	private LinkedList<JSONObject> passengers_list;
	private PNRPassengersAdapter adapter;
	private EditText pnr_text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_pnr, container, false);
		Button pnr_button = (Button) rootView.findViewById(R.id.pnr_button);
		pnr_text = (EditText) rootView.findViewById(R.id.editText);
		pnr_text.setText("4711741085");
		pnr_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String number = pnr_text.getText().toString();
				Bundle bundle = new Bundle();
				HMACGenerator generator = new HMACGenerator("jsonb2b2c68a2085589793c55ea75f218328" + number,
						"2185656b756400d6220c8f80d5083835");
				System.out.println(generator.generateHMAC());
				bundle.putString("url", "http://pnrbuddy.com/api/check_pnr/pnr/" + number
						+ "/format/json/pbapikey/b2b2c68a2085589793c55ea75f218328/pbapisign/" + generator.generateHMAC());
				getLoaderManager().restartLoader(1001, bundle, PNRFragment.this).forceLoad();
			}
		});

		from_view = (TextView) rootView.findViewById(R.id.from_view);
		to_view = (TextView) rootView.findViewById(R.id.to_view);
		from_code = (TextView) rootView.findViewById(R.id.from_code);
		to_code = (TextView) rootView.findViewById(R.id.to_code);
		train_name_view = (TextView) rootView.findViewById(R.id.train_name_view);
		train_num_view = (TextView) rootView.findViewById(R.id.train_no_view);
		reserved_view = (TextView) rootView.findViewById(R.id.reserved_upto_view);
		chart_view = (TextView) rootView.findViewById(R.id.chart_view);
		boarding_point_view = (TextView) rootView.findViewById(R.id.boardin_point_view);
		boarding_date_view = (TextView) rootView.findViewById(R.id.date_view);
		class_view = (TextView) rootView.findViewById(R.id.class_view);
		header_text = (TextView) rootView.findViewById(R.id.header_text);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.pnr_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		passengers_list = new LinkedList<JSONObject>();
		adapter = new PNRPassengersAdapter(getActivity(), R.layout.passenger_list_item, passengers_list);
		setListAdapter(adapter);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.save:
			// SAVE PNR
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Process PNR response from http://pnrbuddy.com/hauth/api
	 */
	private void processResponse(JSONObject jsonObject) {
		try {
			String error = jsonObject.get("error").toString();
			if (error.equals("null")) {
				String train_name = jsonObject.getString("train_name");
				String train_num = jsonObject.getString("train_num");
				String doj = jsonObject.getString("doj");
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
				
				passengers_list.clear();
				for (int i = 0; i < passengers.length(); i++) {
					passengers_list.add(passengers.getJSONObject(i));
				}
				
				adapter.notifyDataSetChanged();
				
				
				from_view.setText(from_station_name);
				from_code.setText(from_station_code);
				to_view.setText(to_station_name);
				to_code.setText(to_station_code);
				train_name_view.setText(train_name);
				train_num_view.setText(train_num);
				boarding_point_view.setText(bdpoint_station_name + " ( " + bdpoint_station_code + ")");
				reserved_view.setText(reserve_station_name + " ( " + reserve_station_code + ")");
				class_view.setText(class_name);
				chart_view.setText(chart_prep);
				boarding_date_view.setText(doj);
				
				header_text.setText(no_passengers + " Passenger details");
				
				
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
	public void onLoadFinished(Loader<JSONObject> jsonObjectLoader, JSONObject jsonObject) {
		if (jsonObject != null) {
			System.out.println(jsonObject.toString());
			processResponse(jsonObject);
		}
	}

	@Override
	public void onLoaderReset(Loader<JSONObject> jsonObjectLoader) {

	}
}
