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
    private LayoutInflater layoutInflater;

    public TimetableEventListAdapter(Context context, List<TimetableEvent> timetableEvents){
        this.timetableEventList = timetableEvents;
        layoutInflater = LayoutInflater.from(context);
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
       ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.event_list_row,null);
            viewHolder = new ViewHolder();
            viewHolder.eventNameAndStartTimeText = (TextView) convertView.findViewById(R.id.event_name_and_start_time);
            viewHolder.placeText = (TextView) convertView.findViewById(R.id.place);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            Date startTime = new Date(timetableEventList.get(position).getTimetableStartTime());
            Date endTime = new Date(timetableEventList.get(position).getTimetableEndTime());

            viewHolder.eventNameAndStartTimeText.setText(timetableEventList.get(position).getTimetableEventName() + " : " + DateFormat.getTimeInstance(DateFormat.SHORT).format(startTime) + " - " + DateFormat.getTimeInstance(DateFormat.SHORT).format(endTime));
            viewHolder.placeText.setText(timetableEventList.get(position).getTimetableEventPlace());
        }
        return convertView;
    }
}
