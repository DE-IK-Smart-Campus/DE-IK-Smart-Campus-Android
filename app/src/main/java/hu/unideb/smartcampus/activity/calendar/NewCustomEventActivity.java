package hu.unideb.smartcampus.activity.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;

import static hu.unideb.smartcampus.container.Container.SELECTED_DATE_LONG;

public class NewCustomEventActivity extends BaseActivity {

    // yyyy.mm.nap  magyar
    // nap.hónap.év angol

    @BindView(R.id.event_name_edit_text)
    EditText eventNameEditText;

    @BindView(R.id.event_description_edit_text)
    EditText eventDescriptionEditText;

    @BindView(R.id.event_place_edit_text)
    EditText eventPlaceEditText;

    @BindView(R.id.event_start_date_edit_text)
    EditText eventStartDateEditText;

    @BindView(R.id.event_start_time_edit_text)
    EditText eventStartTimeEditText;

    @BindView(R.id.event_end_date_edit_text)
    EditText eventEndDateEditText;

    @BindView(R.id.event_end_time_edit_text)
    EditText eventEndTimeEditText;

    @BindView(R.id.event_repeat_edit_text)
    EditText eventRepeatEditText;

    @BindView(R.id.event_notification_edit_text)
    EditText eventNotificationEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.new_event_text);
        ButterKnife.bind(this);

        Long startDate = getIntent().getExtras().getLong(SELECTED_DATE_LONG);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MMM dd.,EEE", Locale.getDefault());

        String selectedDateCalendar = dateFormatter.format(startDate);

        eventStartDateEditText.setText(selectedDateCalendar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_event_action:
                Toast.makeText(this, "Mentve az esemény", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
