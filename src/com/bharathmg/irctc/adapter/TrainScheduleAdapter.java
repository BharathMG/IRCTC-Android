package com.bharathmg.irctc.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bharathmg.irctc.R;
import com.bharathmg.irctc.TrainScheduleFragment;

public class TrainScheduleAdapter extends ArrayAdapter<JSONObject>  {

	private LayoutInflater inflater;

    private final List<JSONObject> jsonRows;
    private final Context mContext;
    Typeface bi;
    Typeface bc;


    public TrainScheduleAdapter(TrainScheduleFragment listFrag, int textViewResourceId, List<JSONObject> mListRows) {
            super(listFrag.getActivity(), textViewResourceId, mListRows);
            this.jsonRows = mListRows;
            this.mContext = listFrag.getActivity();
            inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
           
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
            public TextView station_name;
            public TextView departure_time;
            public TextView arrival_time;
    }

    private View setHolder(View rowView, ViewGroup parent) {
            Holder holder = new Holder();
            rowView = inflater.inflate(R.layout.schedule_list_item, parent, false);
            holder.station_name = (TextView) rowView.findViewById(R.id.station_view);
            holder.departure_time = (TextView) rowView.findViewById(R.id.departure_view);
            holder.arrival_time = (TextView) rowView.findViewById(R.id.arrival_view);
            
            // Set tag
            rowView.setTag(holder);
            return rowView;
    }

    private void populateRowView(int position, View rowView) {
            try {
                    loadRowElements(jsonRows.get(position), (Holder) rowView.getTag());
            } catch (JSONException e) {
                    e.printStackTrace();
            }
    }

    private void loadRowElements(JSONObject jsonRow, Holder holder) throws JSONException {
            holder.station_name.setText(jsonRow.getString("title"));
            holder.departure_time.setText(jsonRow.getString("sub_title"));
            holder.arrival_time.setText(jsonRow.getString("sub_title"));
    }




    public List<JSONObject> getAdapterList() {
            return this.jsonRows;
    }        

}
