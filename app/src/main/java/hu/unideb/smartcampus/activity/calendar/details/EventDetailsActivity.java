package hu.unideb.smartcampus.activity.calendar.details;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;
import hu.unideb.smartcampus.listview.adapter.event.EventDetailsAdapter;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.EVENT_OBJECT;
import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;
import static hu.unideb.smartcampus.container.Container.EVENT_TYPE;

public class EventDetailsActivity extends BaseActivity {

    @BindView(R.id.event_details_list_view)
    ListView eventDetailsListView;

    @BindView(R.id.toolbarTextView)
    TextView toolbarTextView;

    private SimpleDateFormat dateFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_event_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        String eventTypeString = getIntent().getExtras().getString(EVENT_TYPE);

        switch (eventTypeString) {

            case "TIMETABLE":
                TimetableEvent timetableEvent = (TimetableEvent) getIntent().getExtras().getSerializable(EVENT_OBJECT);
                timetableEventListDetails(timetableEvent);
                Log.e("ASD", timetableEvent.toString());
                break;
            case "CUSTOM":
                CustomEvent customEvent = (CustomEvent) getIntent().getExtras().getSerializable(EVENT_OBJECT);
                Log.e("ASD", customEvent.toString());
                break;
        }
    }

    private void timetableEventListDetails(TimetableEvent timetableEvent) {
        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_EN, defaultLocale);
        }
        SimpleDateFormat timeFormatter = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

        String timetableEventName = timetableEvent.getTimetableEventName();
        String timetableEventDescription = timetableEvent.getTimetableEventDescription();
        String timetableEventPlace = timetableEvent.getTimetableEventPlace();

        String timetableEventDate = dateFormatter.format(new Date(timetableEvent.getTimetableEventDate()));
        String timetableEventStartTime = timeFormatter.format(new Date(timetableEvent.getTimetableEventStartTime()));
        String timetableEventEndTime = timeFormatter.format(new Date(timetableEvent.getTimetableEventEndTime()));

        String timetableEventDateAndTime = timetableEventDate + "\n\n" + timetableEventStartTime + " - " + timetableEventEndTime;

        List<String> timetableEventTexts = Arrays.asList(timetableEventDateAndTime, timetableEventPlace, timetableEventDescription);
        List<Integer> timetableEventImg = Arrays.asList(R.drawable.event_time_icon, R.drawable.event_place_icon, R.drawable.event_comment_icon);

        toolbarTextView.setText(timetableEventName);

        EventDetailsAdapter eventDetailsAdapter = new EventDetailsAdapter(this, timetableEventTexts, timetableEventImg);

        eventDetailsListView.setAdapter(eventDetailsAdapter);
    }

    private void customEventListDetails(CustomEvent customEvent) {
        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_EN, defaultLocale);
        }
        SimpleDateFormat timeFormatter = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

//        private String uuid;
//        private String eventName;
//        private String eventDescription;
//        private String eventPlace;
//        private Long eventStartDate;
//        private Long eventStartTime;
//        private Long eventEndDate;
//        private Long eventEndTime;
//        private String evenetRepeat;
//        private String eventReminder;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_event_action:
                Toast.makeText(this, "Szereksztésre tovább", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_event_action:
                Toast.makeText(this, "Törlés", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
