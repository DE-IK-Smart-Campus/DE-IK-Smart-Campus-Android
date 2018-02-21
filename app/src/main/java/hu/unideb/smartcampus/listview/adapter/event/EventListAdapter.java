package hu.unideb.smartcampus.listview.adapter.event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.pojo.calendar.CalendarEvent;

import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;

public class EventListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> calendarEvents;

    public EventListAdapter(Context context, List<Event> calendarEvents) {
        this.context = context;
        this.calendarEvents = calendarEvents;
    }

    @Override
    public int getCount() {
        return calendarEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list_row, parent, false);
        }

        Event event = (Event) getItem(position);

        CalendarEvent calendarEvent = (CalendarEvent) event.getData();

        ImageView eventColorRectangle = convertView.findViewById(R.id.rectimage);

        TextView eventNameTextView = convertView.findViewById(R.id.event_name);

        TextView eventPlaceTextView = convertView.findViewById(R.id.event_place);

        TextView eventTimeTextView = convertView.findViewById(R.id.event_time);

        SimpleDateFormat timeFormat = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

        eventColorRectangle.setBackgroundColor(event.getColor());
        eventNameTextView.setText(calendarEvent != null ? calendarEvent.getEventName() : null);
        eventPlaceTextView.setText(calendarEvent != null ? calendarEvent.getEventPlace() : null);
        eventTimeTextView.setText(timeFormat.format(calendarEvent != null ? calendarEvent.getEventStartTime() : null) + " - " + timeFormat.format(calendarEvent != null ? calendarEvent.getEventEndTime() : null));

        return convertView;
    }
}
