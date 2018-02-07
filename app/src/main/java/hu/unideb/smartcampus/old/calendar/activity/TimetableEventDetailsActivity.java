package hu.unideb.smartcampus.old.calendar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hu.unideb.smartcampus.R;

public class TimetableEventDetailsActivity extends AppCompatActivity {

    private ListView eventDetailsListView;

    private String eventName;
    private String eventDescription;
    private String eventPlace;
    private Long eventDate;
    private Long eventStartTime;
    private Long eventEndTime;

    private ArrayAdapter<String> timetableEventDetailsAdapter;
    private List<String> eventDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_timetable_event_details);
//
//        eventDetailsListView = (ListView) findViewById(R.id.timetable_event_details_listview);

        getDataAnotherScreen();

        timetableEventDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventDetailsListSetUp());

//        eventDetailsListView.setAdapter(timetableEventDetailsAdapter);

    }

    private void getDataAnotherScreen() {
        Bundle bundle = getIntent().getExtras();

        eventName = getIntent().getExtras().getString("eventName");
        eventDescription = bundle.getString("eventDescription");
        eventPlace = bundle.getString("eventPlace");
        eventDate = bundle.getLong("eventDate");
        eventStartTime = bundle.getLong("eventStartTime");
        eventEndTime = bundle.getLong("eventEndTime");
    }

    public List<String> eventDetailsListSetUp() {

        eventDetails = new ArrayList<>();

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(eventDate * 1000);

        Calendar fromTime = Calendar.getInstance();
        fromTime.setTimeInMillis(eventStartTime * 1000);

        Calendar toTime = Calendar.getInstance();
        toTime.setTimeInMillis(eventEndTime * 1000);

        eventDetails.add(eventName);
        eventDetails.add(eventDescription);
        eventDetails.add(eventPlace);
        eventDetails.add(DateFormat.getDateInstance(DateFormat.SHORT).format(date.getTime()));
        eventDetails.add(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromTime.getTime()));
        eventDetails.add(DateFormat.getTimeInstance(DateFormat.SHORT).format(toTime.getTime()));

        return eventDetails;
    }


}
