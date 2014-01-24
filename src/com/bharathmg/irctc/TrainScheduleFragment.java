package com.bharathmg.irctc;

import java.util.LinkedList;
import java.util.List;

import mobi.vserv.android.ads.ViewNotEmptyException;
import mobi.vserv.android.ads.VservController;
import mobi.vserv.android.ads.VservManager;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bharathmg.irctc.adapter.TrainScheduleAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by bharathmg on 11/01/14.
 */
public class TrainScheduleFragment extends Fragment {

	private ListView listView;
	private List<JSONObject> scheduleList;
	private VservController controller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_train_schedule, container, false);
		listView = (ListView) rootView.findViewById(R.id.schedule_list);
		scheduleList = new LinkedList<JSONObject>();
		initAdapter();
		VservManager renderAdManager = VservManager.getInstance(getActivity());
		ViewGroup vservGroup = (ViewGroup) rootView.findViewById(R.id.vserv_ads_group);
		try {
			if(controller == null){
			controller = renderAdManager.renderAd("7f8ac297", vservGroup);
			controller.setRefresh(30);
			}
		} catch (ViewNotEmptyException e) {
			Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
		
		RequestQueue queue=Volley.newRequestQueue(getActivity());
		
		
		return rootView;
	}

	private void initAdapter() {
		TrainScheduleAdapter adapter = new TrainScheduleAdapter(this, R.layout.schedule_list_item, scheduleList);
		listView.setAdapter(adapter);
	}

}
