package com.bharathmg.irctc;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.bharathmg.irctc.adapter.TrainScheduleAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by bharathmg on 11/01/14.
 */
public class TrainScheduleFragment extends Fragment {

	private ListView listView;
	private List<JSONObject> scheduleList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_train_schedule, container, false);
		listView = (ListView) rootView.findViewById(R.id.schedule_list);
		scheduleList = new LinkedList<JSONObject>();
		initAdapter();
		return rootView;
	}

	private void initAdapter() {
		TrainScheduleAdapter adapter = new TrainScheduleAdapter(this, R.layout.schedule_list_item, scheduleList);
		listView.setAdapter(adapter);
	}

}
