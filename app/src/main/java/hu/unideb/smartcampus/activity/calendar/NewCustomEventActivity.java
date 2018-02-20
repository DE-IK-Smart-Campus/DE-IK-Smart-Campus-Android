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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;

import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.EVENT_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.EVENT_TIME_FORMAT_PATTERN;
import static hu.unideb.smartcampus.container.Container.SELECTED_DATE_LONG;

public class NewCustomEventActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener {

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

    TimePickerDialog tpd = new TimePickerDialog();
    Long startDate;

    com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = new com.wdullaer.materialdatetimepicker.date.DatePickerDialog();

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
        startDate = getIntent().getExtras().getLong(SELECTED_DATE_LONG);

        setDefaultDateAndTime(startDate);




    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        SimpleDateFormat timeFormatter1 = new SimpleDateFormat(EVENT_TIME_FORMAT_PATTERN, Locale.getDefault());

        Calendar  v = Calendar.getInstance();
        v.set(Calendar.HOUR_OF_DAY, hourOfDay);
        v.set(Calendar.MINUTE, minute);
        v.set(Calendar.SECOND,0);
        Log.e("asd", timeFormatter1.format(new Date(v.getTimeInMillis())));
        String time = hourString+":"+minuteString;
        Log.e("ad", time);
        eventStartTimeEditText.setText(time);
    }


    @OnClick(R.id.event_start_date_edit_text)
    public void t1(){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(startDate);

        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setAccentColor(getResources().getColor(R.color.calendar_blue));
        dpd.show(getFragmentManager(), "Timepickasdsaerdialog");


    }

    @OnClick(R.id.event_start_time_edit_text)
    public void t(){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(startDate);

        tpd.setAccentColor(getResources().getColor(R.color.calendar_blue));
        tpd.show(getFragmentManager(), "Timepickerdialog");


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

        tpd.initialize(this,
                startTimeCalendar.get(Calendar.HOUR_OF_DAY),
                startTimeCalendar.get(Calendar.MINUTE),
                startTimeCalendar.get(Calendar.SECOND),
                true
        );

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

//    @OnClick(R.id.event_start_date_edit_text)
//    public void eventStartDate() {
//        Log.e("asd", "Lenyomva");
//    }

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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormatter;

        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatter = new SimpleDateFormat(EVENT_DATE_FORMAT_PATTERN_EN, defaultLocale);
        }

        eventStartDateEditText.setText(dateFormatter.format(new Date(c.getTimeInMillis())));


    }
}
