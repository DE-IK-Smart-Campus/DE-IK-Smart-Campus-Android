package hu.unideb.smartcampus.activity.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;
import static hu.unideb.smartcampus.container.Container.SELECTED_DATE_LONG;

public class NewCustomEventActivity extends BaseActivity {

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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Long startDate = getIntent().getExtras().getLong(SELECTED_DATE_LONG);

        setDefaultDateAndTime(startDate);
    }

    private void setDefaultDateAndTime(Long selectedDate) {

        SimpleDateFormat dateFormatter;
        SimpleDateFormat timeFormatter = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_EN, defaultLocale);
        }

        eventStartDateEditText.setFocusable(false);
        eventStartDateEditText.setFocusableInTouchMode(false);

        eventStartTimeEditText.setFocusable(false);
        eventStartTimeEditText.setFocusableInTouchMode(false);

        eventEndDateEditText.setFocusable(false);
        eventEndDateEditText.setFocusableInTouchMode(false);

        eventEndTimeEditText.setFocusable(false);
        eventEndTimeEditText.setFocusableInTouchMode(false);

        Calendar startTimeCalendar = Calendar.getInstance();
        startTimeCalendar.set(Calendar.SECOND, 0);
        startTimeCalendar.set(Calendar.MILLISECOND, 0);
        startTimeCalendar.setTimeZone(TimeZone.getDefault());
        roundingThirtyMinutes(startTimeCalendar);

        Calendar endTimeCalendar = Calendar.getInstance();
        endTimeCalendar.set(Calendar.SECOND, 0);
        endTimeCalendar.set(Calendar.MILLISECOND, 0);
        endTimeCalendar.add(Calendar.HOUR_OF_DAY, 1);
        roundingThirtyMinutes(endTimeCalendar);


        String formatStartDate = dateFormatter.format(selectedDate);
        String formatStartTime = timeFormatter.format(startTimeCalendar.getTimeInMillis());

        String formatEndDate = dateFormatter.format(selectedDate);
        String formatEndTime = timeFormatter.format(endTimeCalendar.getTimeInMillis());

        eventStartDateEditText.setText(formatStartDate);
        eventStartTimeEditText.setText(formatStartTime);

        eventEndDateEditText.setText(formatEndDate);
        eventEndTimeEditText.setText(formatEndTime);
    }

    private void roundingThirtyMinutes(Calendar calendar) {
        int rounding = calendar.get(Calendar.MINUTE) % 30;
        calendar.add(Calendar.MINUTE, rounding < 8 ? -rounding : (30 - rounding));
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
