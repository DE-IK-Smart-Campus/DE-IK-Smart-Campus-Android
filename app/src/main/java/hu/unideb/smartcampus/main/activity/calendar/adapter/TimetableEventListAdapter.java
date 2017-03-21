package hu.unideb.smartcampus.main.activity.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.calendar.pojo.TimetableEvent;

public class TimetableEventListAdapter extends BaseAdapter {

    private List<TimetableEvent> timetableEventList;
    private Context context;

    public TimetableEventListAdapter(Context context, List<TimetableEvent> events) {
        this.context = context;
        this.timetableEventList = events;
    }

    @Override
    public int getCount() {
        return timetableEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return timetableEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list_row, parent, false);
        }
        TimetableEvent currentItem = (TimetableEvent) getItem(position);

        TextView event_name_and_start_time = (TextView) convertView.findViewById(R.id.event_name_and_start_time);
        TextView place = (TextView) convertView.findViewById(R.id.place);

        Date startDate = new Date(currentItem.getTimetableStartTime());
        Date endDate = new Date(currentItem.getTimetableEndTime());

        event_name_and_start_time.setText(currentItem.getTimetableEventName() + " : " + DateFormat.getTimeInstance(DateFormat.SHORT).format(startDate) + " - " + DateFormat.getTimeInstance(DateFormat.SHORT).format(endDate));
        place.setText(currentItem.getTimetableEventPlace());

        return convertView;
    }
}
