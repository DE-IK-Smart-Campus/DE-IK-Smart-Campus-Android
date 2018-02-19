package hu.unideb.smartcampus.fragment.calendar;

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
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> parkingHistoryList;

    public ListAdapter(Context context, List<Event> parkingHistoryList) {
        this.context = context;
        this.parkingHistoryList = parkingHistoryList;
    }

    @Override
    public int getCount() {
        return parkingHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return parkingHistoryList.get(position);
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

        Event event = (Event) getItem(position);

        TimetableEvent timetableEvent = (TimetableEvent) event.getData();

        ImageView im = convertView.findViewById(R.id.rectimage);

        TextView t1 = convertView.findViewById(R.id.event_name);

        TextView t2 = convertView.findViewById(R.id.event_place);

        TextView t3 = convertView.findViewById(R.id.event_time);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());


        im.setBackgroundColor(event.getColor());
        t1.setText(timetableEvent.getTimetableEventName());
        t2.setText(timetableEvent.getTimetableEventPlace());
        t3.setText(simpleDateFormat1.format(timetableEvent.getTimetableEventStartTime()) + " - " + simpleDateFormat2.format(timetableEvent.getTimetableEventEndTime()));



//        ParkingHistory parkingHistory = (ParkingHistory) getItem(position);
//
//        TextView parkingHistoryStartAndEndTime = convertView.findViewById(R.id.parking_history_start_and_end_time);
//        TextView parkingHistoryPrice = convertView.findViewById(R.id.parking_history_price);
//
//        String parkingStartDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(parkingHistory.getStartTime().getTime());
//        String parkingStartTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(parkingHistory.getStartTime().getTime());
//
//        String parkingEndDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(parkingHistory.getEndTime().getTime());
//        String parkingEndTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(parkingHistory.getEndTime().getTime());
//
//        String parkingStartDateTime = parkingStartDate+ " " + parkingStartTime;
//        String parkingEndDateTime = parkingEndDate + " " + parkingEndTime;
//
//        String parkingPriceText = context.getResources().getString(R.string.parking_history_list_view_text);
//        String parkingPriceCurrency = context.getResources().getString(R.string.currency);

//        parkingHistoryStartAndEndTime.setText(String.format("%s - %s", parkingStartDateTime, parkingEndDateTime));
//        parkingHistoryPrice.setText(String.format("%s %s %s", parkingPriceText, String.valueOf(parkingHistory.getPrice()), parkingPriceCurrency));

        return convertView;
    }
}
