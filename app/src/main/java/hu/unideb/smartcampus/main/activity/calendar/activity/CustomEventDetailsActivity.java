package hu.unideb.smartcampus.main.activity.calendar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;

public class CustomEventDetailsActivity extends AppCompatActivity {

    private ListView customEventListView;

    private String customUuid;
    private String customEventName;
    private String customEventDescription;
    private String customEventPlace;
    private Long customEventStartDate;
    private Long customEventStartTime;
    private Long customEventEndDate;
    private Long customEventEndTime;
    private String customEventRepeat;
    private String customEventRemainder;

    private List<String> customEventDetailsList;
    private ListView customEventDetailsListView;
    private ArrayAdapter<String> customEventDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_event_details);

        customEventDetailsListView = (ListView) findViewById(R.id.custom_event_details_listview);

        getDataAnotherScreen();

        customEventDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customEventDetailsListSetUp());

        customEventDetailsListView.setAdapter(customEventDetailsAdapter);
    }

    private void getDataAnotherScreen() {
        Bundle bundle = getIntent().getExtras();

        customUuid = bundle.getString("uuid");
        customEventName = bundle.getString("name");
        customEventDescription = bundle.getString("description");
        customEventPlace = bundle.getString("place");
        customEventStartDate = bundle.getLong("startDate");
        customEventStartTime = bundle.getLong("startTime");
        customEventEndDate = bundle.getLong("endDate");
        customEventEndTime = bundle.getLong("endTime");
        customEventRepeat = bundle.getString("repeat");
        customEventRemainder = bundle.getString("remainder");
    }

    private List<String> customEventDetailsListSetUp() {

        customEventDetailsList = new ArrayList<>();

        customEventDetailsList.add(customEventName);
        customEventDetailsList.add(customEventDescription);
        customEventDetailsList.add(customEventPlace);
        customEventDetailsList.add(DateFormat.getDateInstance(DateFormat.SHORT).format(customEventStartTime * 1000));
        customEventDetailsList.add(DateFormat.getTimeInstance(DateFormat.SHORT).format(customEventStartDate * 1000));
        customEventDetailsList.add(DateFormat.getTimeInstance(DateFormat.SHORT).format(customEventEndTime * 1000));
        customEventDetailsList.add(DateFormat.getDateInstance(DateFormat.SHORT).format(customEventEndDate * 1000));
//        customEventDetailsList.add(customEventRepeat);
//        customEventDetailsList.add(customEventRemainder);

        return customEventDetailsList;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.event_details_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.action_edit_event) {
//            Intent i = new Intent(getApplicationContext(), EditCustomEventActivity.class);
//            startActivity(i);
//            Toast.makeText(getApplicationContext(), "Szerkeszt", Toast.LENGTH_SHORT).show();
//        }
//
//        if (id == R.id.action_delete_event) {
//            Toast.makeText(getApplicationContext(), "Töröl", Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
