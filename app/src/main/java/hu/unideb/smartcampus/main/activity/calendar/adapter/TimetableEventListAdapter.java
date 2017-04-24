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
import hu.unideb.smartcampus.sqlite.model.TimetableEvent;

public class TimetableEventListAdapter extends BaseAdapter {

    private Context context;
    private List<TimetableEvent> list;

    public TimetableEventListAdapter(Context context, List<TimetableEvent> events) {
        this.context = context;
        this.list = events;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.timetable_event_list_row, parent, false);
        }

        TimetableEvent timetableEvent = (TimetableEvent) getItem(position);


        TextView event_name_and_start_time = (TextView) convertView.findViewById(R.id.event_name_and_start_time);
        TextView place = (TextView) convertView.findViewById(R.id.place);

        Long startTime = timetableEvent.getTimetableEventStartTime() * 1000;
        Long endTime = timetableEvent.getTimetableEventEndTime() * 1000;

        event_name_and_start_time.setText(timetableEvent.getTimetableEventName() + " : " + DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime) + " - " + DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
        place.setText(timetableEvent.getTimetableEventPlace() +" " + timetableEvent.getTimetableEventDescription());

        return convertView;
    }
}
