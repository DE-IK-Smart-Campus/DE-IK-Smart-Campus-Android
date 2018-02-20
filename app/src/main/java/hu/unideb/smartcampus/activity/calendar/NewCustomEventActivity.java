package hu.unideb.smartcampus.activity.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;
import hu.unideb.smartcampus.activity.calendar.picker.StartDatePicker;
import hu.unideb.smartcampus.activity.calendar.picker.StartTimePicker;
import hu.unideb.smartcampus.interfaces.calendar.date.StartDatePickerInterface;
import hu.unideb.smartcampus.interfaces.calendar.time.StartTimePickerInterface;

import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;
import static hu.unideb.smartcampus.container.Container.SELECTED_DATE_LONG;

public class NewCustomEventActivity extends BaseActivity implements StartDatePickerInterface, StartTimePickerInterface {

    private final String TAG = NewCustomEventActivity.class.getSimpleName();

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

    private int pickerColor;
    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar startTimeCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

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

        pickerColor = getResources().getColor(R.color.calendar_blue);

        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_EN, defaultLocale);
        }

        Long startDate = getIntent().getExtras().getLong(SELECTED_DATE_LONG);
        startDateCalendar.setTimeInMillis(startDate);

        setDefaultDateAndTime(startDate);

    }

//    SimpleDateFormat timeFormatter = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());
//    String formatStartTime = timeFormatter.format(startTimeCalendar.getTimeInMillis());
//
//        eventStartTimeEditText.setText(formatStartTime);
//    @Override
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
//        String minuteString = minute < 10 ? "0"+minute : ""+minute;
//        SimpleDateFormat timeFormatter1 = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());
//
//        Calendar  v = Calendar.getInstance();
//        v.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        v.set(Calendar.MINUTE, minute);
//        v.set(Calendar.SECOND,0);
//        Log.e("asd", timeFormatter1.format(new Date(v.getTimeInMillis())));
//        String time = hourString+":"+minuteString;
//        Log.e("ad", time);
//        eventStartTimeEditText.setText(time);
//    }


    @OnClick(R.id.event_start_date_edit_text)
    public void startDateOnClick() {
        StartDatePicker startDatePicker = new StartDatePicker(this);
        startDatePicker.show(getFragmentManager(), pickerColor, startDateCalendar);
    }

    @OnClick(R.id.event_start_time_edit_text)
    public void startTimeOnClick() {
        StartTimePicker startTimePicker = new StartTimePicker(this);
        startTimePicker.show(getFragmentManager(), pickerColor, startTimeCalendar);
    }

    private void setDefaultDateAndTime(Long selectedDate) {

        startTimeCalendar.set(Calendar.SECOND, 0);
        startTimeCalendar.set(Calendar.MILLISECOND, 0);
        startTimeCalendar.setTimeZone(TimeZone.getDefault());
        roundingThirtyMinutes(startTimeCalendar);

        eventStartDateEditText.setFocusable(false);
        eventStartDateEditText.setFocusableInTouchMode(false);

        eventStartTimeEditText.setFocusable(false);
        eventStartTimeEditText.setFocusableInTouchMode(false);

        eventStartDateEditText.setText(dateFormatter.format(startDateCalendar.getTimeInMillis()));
        eventStartTimeEditText.setText(timeFormatter.format(startTimeCalendar.getTimeInMillis()));

//        eventEndDateEditText.setFocusable(false);
//        eventEndDateEditText.setFocusableInTouchMode(false);
//        eventEndTimeEditText.setFocusable(false);
//        eventEndTimeEditText.setFocusableInTouchMode(false);
//        Calendar endTimeCalendar = Calendar.getInstance();
//        endTimeCalendar.set(Calendar.SECOND, 0);
//        endTimeCalendar.set(Calendar.MILLISECOND, 0);
//        endTimeCalendar.add(Calendar.HOUR_OF_DAY, 1);
//        roundingThirtyMinutes(endTimeCalendar);
//        String formatStartDate = dateFormatter.format(selectedDate);
//        String formatEndDate = dateFormatter.format(selectedDate);
//        String formatEndTime = timeFormatter.format(endTimeCalendar.getTimeInMillis());
//
//        eventStartDateEditText.setText(formatStartDate);
//        eventEndDateEditText.setText(formatEndDate);
//        eventEndTimeEditText.setText(formatEndTime);
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

    @Override
    public void selectedDate(Calendar calendar) {
        startDateCalendar = calendar;

        String formatStartDate = dateFormatter.format(calendar.getTimeInMillis());

        eventStartDateEditText.setText(formatStartDate);

        Log.d(TAG, "SelectedDate " + formatStartDate);
    }

    @Override
    public void selectedTime(Calendar calendar) {
        startTimeCalendar = calendar;

        String formatStartTime = timeFormatter.format(calendar.getTimeInMillis());

        eventStartTimeEditText.setText(formatStartTime);
        Log.d(TAG, "SelectedTime " + formatStartTime);
    }
}
