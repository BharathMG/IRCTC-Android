package com.bharathmg.irctc;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bharathmg.irctc.utils.HMACGenerator;
import com.bharathmg.irctc.utils.SecureConstants;

import mobi.vserv.android.ads.ViewNotEmptyException;
import mobi.vserv.android.ads.VservController;
import mobi.vserv.android.ads.VservManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by bharathmg on 11/01/14.
 */
public class TrainStatusFragment extends Fragment {

	private VservController controller;
	private EditText train_no_view;
	private EditText journey_date_view;
	private AutoCompleteTextView from_view;
	private AutoCompleteTextView to_view;
	private Spinner class_spinner;
	private Handler handler;
	private Spinner quota_spinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_train_status, container, false);
		handler = new Handler();
		VservManager renderAdManager = VservManager.getInstance(getActivity());
		ViewGroup vservGroup = (ViewGroup) rootView.findViewById(R.id.vserv_ads_group);
		try {
			if (controller == null) {
				controller = renderAdManager.renderAd("7f8ac297", vservGroup);
				controller.setRefresh(30);
			}

		} catch (ViewNotEmptyException e) {
			Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		train_no_view = (EditText) rootView.findViewById(R.id.editText1);
		journey_date_view = (EditText) rootView.findViewById(R.id.editText2);
		from_view = (AutoCompleteTextView) rootView.findViewById(R.id.from_text);
		to_view = (AutoCompleteTextView) rootView.findViewById(R.id.to_text);
		class_spinner = (Spinner) rootView.findViewById(R.id.spinner1);
		quota_spinner = (Spinner) rootView.findViewById(R.id.spinner2);

		from_view.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				final String search_key = s.toString();
				handler.removeCallbacksAndMessages(null);
				handler.removeCallbacks(null);
				if (!search_key.isEmpty() && search_key != "") {
					handler.postDelayed(new Runnable() {
						public void run() {
							
							String hmac_input = "json" + search_key + "0" + SecureConstants.public_key;
							HMACGenerator generator = new HMACGenerator(hmac_input,
									SecureConstants.private_key);
							RequestQueue queue = Volley.newRequestQueue(getActivity());

							JsonObjectRequest jsonReq = new JsonObjectRequest(
									Method.GET,
									"http://pnrbuddy.com/api/station_by_name/name/" + search_key + "/partial/0/format/json/pbapikey/" +SecureConstants.public_key+ "/pbapisign/" + generator.generateHMAC(),
									null, createMyReqSuccessListener(), createMyReqErrorListener());
							queue.add(jsonReq);
						}
					}, 1000);
				}
			}

		});

		return rootView;
	}

	public void getStatus(View v) {
		String train_number = train_no_view.getText().toString();
		String journey_date = journey_date_view.getText().toString();
		String from_text = from_view.getText().toString();
		String to_text = to_view.getText().toString();
		String class_selected = class_spinner.getSelectedItem().toString();
		String quota_selected = quota_spinner.getSelectedItem().toString();
	}

	// name = delhi,partial=1,format=json,pbapikey

	// format,name,partial,pbapikey
	// String value = from_text.getText() + 1 + "json" + pbcdcmd;.tolowercase

	private Response.Listener<JSONObject> createMyReqSuccessListener() {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					// mTvResult.setText(response.getString("one"));
				} catch (Exception e) {
					// mTvResult.setText("Parse error");
				}
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// mTvResult.setText(error.getMessage());
			}
		};
	}

}
