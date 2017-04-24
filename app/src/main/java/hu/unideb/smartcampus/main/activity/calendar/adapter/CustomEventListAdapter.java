package hu.unideb.smartcampus.main.activity.calendar.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.sqlite.model.CustomEvent;

public class CustomEventListAdapter extends BaseAdapter {

    private Context context;
    private List<CustomEvent> customEventList;

    public CustomEventListAdapter(Context context, List<CustomEvent> events) {
        this.context = context;
        this.customEventList = events;
    }

    @Override
    public int getCount() {
        return customEventList.size();
    }

    @Override
    public Object getItem(int i) {
        return customEventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_event_list_row, parent, false);
        }

        CustomEvent customEvent = (CustomEvent) getItem(position);

        TextView event_name_and_start_time = (TextView) convertView.findViewById(R.id.custum_event_name_and_time);
        TextView place = (TextView) convertView.findViewById(R.id.custom_event_place);

        Long startTime = customEvent.getEventStartDate() * 1000;
        Long endTime = customEvent.getEventEndTime() * 1000;

        event_name_and_start_time.setText(customEvent.getEventName() + " : " + DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime) + " - " + DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
        place.setText(customEvent.getEventPlace());

        return convertView;
    }
}
