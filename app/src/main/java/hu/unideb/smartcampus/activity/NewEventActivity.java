package hu.unideb.smartcampus.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hu.unideb.smartcampus.R;


public class NewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText startDate;
    private EditText endDate;
    private EditText startTime;
    private EditText endTime;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;

    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);

        dateFormatter = new SimpleDateFormat("yyyy.MMM dd.,EEE", Locale.getDefault());
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        String date = dateFormatter.format(Calendar.getInstance().getTime());
        String time = timeFormatter.format(Calendar.getInstance().getTime());

        findViewsById();

        startDate.setText(date);
        startTime.setText(time);
        endDate.setText(date);
        endTime.setText(time);

        setDateTimeField();
        setTimeField();
    }

    private void findViewsById() {
        startDate = (EditText) findViewById(R.id.start_date);
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setFocusable(false);

        startTime = (EditText) findViewById(R.id.start_time);
        startTime.setInputType(InputType.TYPE_NULL);
        startTime.setFocusable(false);

        endDate = (EditText) findViewById(R.id.end_date);
        endDate.setInputType(InputType.TYPE_NULL);
        endDate.setFocusable(false);

        endTime = (EditText) findViewById(R.id.end_time);
        endTime.setInputType(InputType.TYPE_NULL);
        endTime.setFocusable(false);
    }


    private void setDateTimeField() {
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            startDate.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            endDate.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setTimeField() {
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        fromTimePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            Calendar newTime = Calendar.getInstance();
            newTime.set(0, 0, 0, hourOfDay, minute);
            startTime.setText(timeFormatter.format(newTime.getTime()));
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        toTimePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            Calendar newTime = Calendar.getInstance();
            newTime.set(0, 0, 0, hourOfDay, minute);
            endTime.setText(timeFormatter.format(newTime.getTime()));
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

    }

    @Override
    public void onClick(View view) {
        if (view == startDate) {
            fromDatePickerDialog.show();
        } else if (view == endDate) {
            toDatePickerDialog.show();
        } else if (view == startTime) {
            fromTimePickerDialog.show();
        } else if (view == endTime) {
            toTimePickerDialog.show();
        }
    }

    public void cancelOnClick(View view) {
        super.onBackPressed();
    }

    public void allDayEventOnClick(View view) {
        CheckBox ce = (CheckBox) findViewById(R.id.allDayEvent);
        if (ce.isChecked()) {
            startTime.setVisibility(View.INVISIBLE);
            endTime.setVisibility(View.INVISIBLE);
        } else if (!ce.isChecked()) {
            startTime.setVisibility(View.VISIBLE);
            endTime.setVisibility(View.VISIBLE);
        }
    }

}