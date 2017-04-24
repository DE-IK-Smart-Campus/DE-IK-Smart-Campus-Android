package hu.unideb.smartcampus.main.activity.calendar.activity;

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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.calendar.handler.AddCustomEventHandler;
import hu.unideb.smartcampus.sqlite.manager.DatabaseManager;

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

    private Calendar startDateCalendar;
    private Calendar endDateCalendar;
    private Calendar startTimeCalendar;
    private Calendar endTimeCalendar;

    private String repeatSelect;
    private String remainderSelect;

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event_activity);
        setupVariables();

        databaseManager = new DatabaseManager(getApplicationContext());
        databaseManager.open();

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
        remainderSetup();
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

    private void setDateTimeField() {
        checkBoxOnOff();
        startDateCalendar = Calendar.getInstance();
        startDateCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Budapest"));
        startDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startDateCalendar.set(Calendar.MINUTE, 0);
        startDateCalendar.set(Calendar.SECOND, 0);
        startDateCalendar.set(Calendar.MILLISECOND, 0);
        endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Budapest"));
        endDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endDateCalendar.set(Calendar.MINUTE, 0);
        endDateCalendar.set(Calendar.SECOND, 0);
        endDateCalendar.set(Calendar.MILLISECOND, 0);

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDateCalendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                startDate.setText(dateFormatter.format(startDateCalendar.getTime()));
                endDate.setText(startDate.getText());
            }
        }, startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));


        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDateCalendar.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormatter.format(endDateCalendar.getTime()));
            }
        }, startDateCalendar.get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void roundingThirtyMinutes(Calendar calendar) {
        int rounding = calendar.get(Calendar.MINUTE) % 30;
        calendar.add(Calendar.MINUTE, rounding < 8 ? -rounding : (30 - rounding));
    }

    private void setTimeField() {
        startTimeCalendar = Calendar.getInstance();
        startTimeCalendar.set(Calendar.SECOND, 0);
        startTimeCalendar.set(Calendar.MILLISECOND, 0);

        roundingThirtyMinutes(startTimeCalendar);

        endTimeCalendar = Calendar.getInstance();
        endTimeCalendar.set(Calendar.SECOND, 0);
        endTimeCalendar.set(Calendar.MILLISECOND, 0);
        endTimeCalendar.add(Calendar.HOUR_OF_DAY, 1);
        roundingThirtyMinutes(endTimeCalendar);

        startTime.setText(timeFormatter.format(startTimeCalendar.getTime()));

        fromTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                startTimeCalendar.set(Calendar.MINUTE, minute);
                startTime.setText(timeFormatter.format(startTimeCalendar.getTime()));
            }
        }, startTimeCalendar.get(Calendar.HOUR_OF_DAY), startTimeCalendar.get(Calendar.MINUTE), true);

        endTime.setText(timeFormatter.format(endTimeCalendar.getTime()));

        toTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                endTimeCalendar.set(Calendar.MINUTE, minute);
                endTime.setText(timeFormatter.format(endTimeCalendar.getTime()));
            }
        }, endTimeCalendar.get(Calendar.HOUR_OF_DAY), endTimeCalendar.get(Calendar.MINUTE), true);

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
                    startTimeCalendar.set(Calendar.HOUR_OF_DAY,0);
                    startTimeCalendar.set(Calendar.MINUTE,0);
                    startTimeCalendar.set(Calendar.MILLISECOND,0);

                    endTimeCalendar.set(Calendar.HOUR_OF_DAY,0);
                    endTimeCalendar.set(Calendar.MINUTE,0);
                    endTimeCalendar.set(Calendar.MILLISECOND,0);
                } else if (!checkBox.isChecked()) {
                    startTime.setVisibility(View.VISIBLE);
                    endTime.setVisibility(View.VISIBLE);
                    editTextEnableOrDisable(endDate, true, InputType.TYPE_DATETIME_VARIATION_DATE);
                }
            }
        });
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
                        repeatSelect = parent.getItemAtPosition(pos).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                }
        );
    }

    private void remainderSetup() {
        //TODO
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

    public void cancelOnClick(View view) {
        super.onBackPressed();
    }

    public void saveOnClick(View view) {
        String uuid = UUID.randomUUID().toString();
        startTimeCalendar.set(Calendar.YEAR, startDateCalendar.get(Calendar.YEAR));
        startTimeCalendar.set(Calendar.MONTH, startDateCalendar.get(Calendar.MONTH));
        startTimeCalendar.set(Calendar.DAY_OF_MONTH, startDateCalendar.get(Calendar.DAY_OF_MONTH));
        endTimeCalendar.set(Calendar.YEAR, startDateCalendar.get(Calendar.YEAR));
        endTimeCalendar.set(Calendar.MONTH, startDateCalendar.get(Calendar.MONTH));
        endTimeCalendar.set(Calendar.DAY_OF_MONTH, startDateCalendar.get(Calendar.DAY_OF_MONTH));

        AddCustomEventHandler.add(uuid, startDateCalendar.getTimeInMillis() /1000, eventName.getText().toString(), eventDescription.getText().toString(), eventPlace.getText().toString(), startTimeCalendar.getTimeInMillis() /1000, endTimeCalendar.getTimeInMillis() /1000, repeatSelect, remainderSelect);
//        databaseManager.insertCustomEvent(new CustomEvent(uuid, eventName.getText().toString(),eventDescription.getText().toString(),eventPlace.getText().toString(),startDateCalendar.getTimeInMillis(), startTimeCalendar.getTimeInMillis(),endDateCalendar.getTimeInMillis(), endTimeCalendar.getTimeInMillis(), repeatSelect, remainderSelect));

//        DeleteCustomEventHandler.delete("b58ddd62-ba30-42d9-8e3d-0e1edf761026");

        Toast.makeText(this, "Esemény hozzáadva", Toast.LENGTH_SHORT).show();
//        super.onBackPressed();
    }
}