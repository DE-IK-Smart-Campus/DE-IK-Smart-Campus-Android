package hu.unideb.smartcampus.activity.calendar.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;

public class EventDetailsActivity extends BaseActivity {

    @BindView(R.id.event_details_list_view)
    ListView eventDetailsListView;

    private SimpleDateFormat dateFormatter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Esemény részletei");
        ButterKnife.bind(this);
        String eventTypeString = getIntent().getExtras().getString("TEST1");
        TimetableEvent timetableEvent = null;
        switch (eventTypeString) {

            case "TIMETABLE":
                timetableEvent = (TimetableEvent) getIntent().getExtras().getSerializable("TEST");
                Log.e("ASD", timetableEvent.toString());
                break;
            case "CUSTOM":
                CustomEvent customEvent = (CustomEvent) getIntent().getExtras().getSerializable("TEST");
                Log.e("ASD", customEvent.toString());
                break;
        }

        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_EN, defaultLocale);
        }

       SimpleDateFormat timeFormatter = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

        String t1 = "Esemény Időpontja:";
        String z = dateFormatter.format(new Date(timetableEvent.getTimetableEventDate()));
        String t2 = "Esemény neve:";
        String z1 = timetableEvent.getTimetableEventName();
        String t3 = "Esemény leírása:";
        String z2 = timetableEvent.getTimetableEventDescription();
        String t4 = "Esemény helye:";
        String z3 = timetableEvent.getTimetableEventPlace();
        String t5 = "Esemény kezdeti ideje:";
        String z4 = timeFormatter.format(new Date(timetableEvent.getTimetableEventStartTime()));
        String t6 = "Esemény befejező ideje:";
        String z5 = timeFormatter.format(new Date(timetableEvent.getTimetableEventEndTime()));


        List<String> strings = Arrays.asList(t1,z,t2, z1,t3, z2,t4, z3,t5, z4,t6, z5);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, strings);
//

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, strings){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(position %2 == 1)
                {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                }
                else
                {
                    // Set the background color for alternate row/item
                    view.setBackgroundColor(getResources().getColor(R.color.calendar_blue));
                }
                return view;
            }
        };

                eventDetailsListView.setAdapter(arrayAdapter);



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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
