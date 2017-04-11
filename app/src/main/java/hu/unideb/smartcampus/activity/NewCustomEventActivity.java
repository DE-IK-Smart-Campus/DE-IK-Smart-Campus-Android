package hu.unideb.smartcampus.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hu.unideb.smartcampus.R;

public class NewCustomEventActivity extends AppCompatActivity {

    private EditText eventName;
    private EditText eventDescription;
    private EditText eventPlace;
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

    Calendar newTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);
        setupVariables();

        dateFormatter = new SimpleDateFormat("yyyy.MMM dd.,EEE", Locale.getDefault());
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 1);

        String fromTime = timeFormatter.format(Calendar.getInstance().getTime());
        String toTime = timeFormatter.format(now.getTime());

        Date selectedDate = new Date(getIntent().getExtras().getLong("selectedDate"));

        String selectedDateCalendar = dateFormatter.format(selectedDate);

        startDate.setText(selectedDateCalendar);
        startTime.setText(fromTime);
        endDate.setText(selectedDateCalendar);
        endTime.setText(toTime);

        setDateTimeField();
        setTimeField();
        repeatSetup();
    }

    private void setupVariables() {

        eventName = (EditText) findViewById(R.id.eventName);
        eventDescription = (EditText) findViewById(R.id.eventDescription);
        eventPlace = (EditText) findViewById(R.id.eventPlaceText);

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

    private void repeatSetup() {
        Spinner repeatSpinner = (Spinner) findViewById(R.id.repeatSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeats_array_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(adapter);

        repeatSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view1,
                                               int pos, long id) {
//                        Toast.makeText(getApplicationContext(), "You have selected " + parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                }
        );
    }

    private void remainderSetup(){
        Spinner remainderSpinner =(Spinner) findViewById(R.id.remainderSpinner);

    }

    private void setDateTimeField() {
        checkBoxOnOff();

        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormatter.format(newDate.getTime()));
                endDate.setText(startDate.getText());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void roundingThirtyMinutes(Calendar calendar) {
        int rounding = calendar.get(Calendar.MINUTE) % 30;
        calendar.add(Calendar.MINUTE, rounding < 8 ? -rounding : (30 - rounding));
    }

    private void setTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        roundingThirtyMinutes(newCalendar);

        startTime.setText(timeFormatter.format(newCalendar.getTime()));

        fromTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                newTime.set(0, 0, 0, hourOfDay, minute);
                startTime.setText(timeFormatter.format(newTime.getTime()));
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        Calendar toTime = Calendar.getInstance();
        toTime.setTime(newTime.getTime());
        toTime.add(Calendar.HOUR_OF_DAY, 1);
        roundingThirtyMinutes(toTime);
        endTime.setText(timeFormatter.format(toTime.getTime()));

        toTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newEndTime = Calendar.getInstance();
                newEndTime.set(0, 0, 0, hourOfDay, minute);
                endTime.setText(timeFormatter.format(newEndTime.getTime()));
            }
        }, toTime.get(Calendar.HOUR_OF_DAY), toTime.get(Calendar.MINUTE), true);

    }

    private void checkBoxOnOff() {
        final CheckBox checkBox = (CheckBox) findViewById(R.id.allDayEvent);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()) {
                    startTime.setVisibility(View.INVISIBLE);
                    endTime.setVisibility(View.INVISIBLE);
                    endDate.setText(startDate.getText());
                    editTextEnableOrDisable(endDate, false, InputType.TYPE_NULL);
                } else if (!checkBox.isChecked()) {
                    startTime.setVisibility(View.VISIBLE);
                    endTime.setVisibility(View.VISIBLE);
                    editTextEnableOrDisable(endDate, true, InputType.TYPE_DATETIME_VARIATION_DATE);
                }
            }
        });
    }

    private void editTextEnableOrDisable(EditText editTextName, boolean trueOrFalse, int inputType) {
        editTextName.setFocusable(trueOrFalse);
        editTextName.setEnabled(trueOrFalse);
        editTextName.setCursorVisible(trueOrFalse);
        editTextName.setInputType(inputType);
    }

    public void startDateSet(View view) {
        fromDatePickerDialog.show();
    }

    public void endDateSet(View view) {
        toDatePickerDialog.show();
    }

    public void startTimeSet(View view) {
        fromTimePickerDialog.show();
    }

    public void endTimeSet(View view) {
        toTimePickerDialog.show();
    }

//    public void repeatTimeSet(View view) {
//        repeatDialog.show();
//    }

    public void cancelOnClick(View view) {
        super.onBackPressed();
    }

    public void saveOnClick(View view) {
    }
}