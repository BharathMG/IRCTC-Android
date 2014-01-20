package com.bharathmg.irctc.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bharathmg.irctc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PNRPassengersAdapter extends ArrayAdapter<JSONObject> {

	private List<JSONObject> passengers_list;
	private LayoutInflater inflater;
	public PNRPassengersAdapter(Context context, int resource, List<JSONObject> jsonRows) {
		super(context, resource);
		this.passengers_list = jsonRows;
	}
	
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	            if (convertView == null) {
	                    convertView = setHolder(convertView, parent);
	            }
	            populateRowView(position, convertView);
	            return convertView;
	    }

	    private static class Holder {
	            public TextView sl_no;
	            public TextView reservation;
	            public TextView current;
	    }

	    private View setHolder(View rowView, ViewGroup parent) {
	            Holder holder = new Holder();
	            rowView = inflater.inflate(R.layout.passenger_list_item, parent, false);
	            holder.sl_no = (TextView) rowView.findViewById(R.id.textView1);
	            holder.reservation = (TextView) rowView.findViewById(R.id.textView2);
	            holder.current = (TextView) rowView.findViewById(R.id.textView3);
	            
	            // Set tag
	            rowView.setTag(holder);
	            return rowView;
	    }

	    private void populateRowView(int position, View rowView) {
	            try {
	                    loadRowElements(passengers_list.get(position), (Holder) rowView.getTag());
	            } catch (JSONException e) {
	                    e.printStackTrace();
	            }
	    }

	    private void loadRowElements(JSONObject jsonRow, Holder holder) throws JSONException {
	            holder.sl_no.setText(jsonRow.getString("sr"));
	            holder.reservation.setText(jsonRow.getString("booking_status"));
	            holder.current.setText(jsonRow.getString("current_status"));
	    }




	    public List<JSONObject> getAdapterList() {
	            return this.passengers_list;
	    }      
	
	

}
