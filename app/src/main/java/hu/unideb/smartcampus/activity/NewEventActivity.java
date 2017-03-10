package hu.unideb.smartcampus.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hu.unideb.smartcampus.R;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText eventName;
    private EditText eventDescription;
    private EditText eventPlace;
    private EditText startDate;
    private EditText endDate;
    private EditText startTime;
    private EditText endTime;
    private EditText repaitEditText;

    private AlertDialog.Builder repaitDialog;
    private String[] repaitOptionText;

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
        setupVariables();

        repaitOptionText = getApplicationContext().getResources().getStringArray(R.array.remainder_array_item);

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
        spinnerSetup();

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

        repaitEditText = (EditText) findViewById(R.id.repaitText);
        repaitEditText.setInputType(InputType.TYPE_NULL);
        repaitEditText.setFocusable(false);
    }

    private void spinnerSetup() {
        Spinner repeatSpinner = (Spinner) findViewById(R.id.repeatSpinner);
        repaitEditText.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeats_array_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(adapter);

        repeatSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view1,
                                               int pos, long id) {
                        Toast.makeText(getApplicationContext(), "You have selected " + parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                }
        );

        ArrayList<String> selColors = new ArrayList<>();
        final boolean[] _selections = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        repaitDialog = new AlertDialog.Builder(NewEventActivity.this);
        repaitDialog.setTitle(R.string.context_choice);
        repaitDialog.setMultiChoiceItems(repaitOptionText, _selections, (dialogInterface, i, b) -> {
            if (b) {
                selColors.add(repaitOptionText[i]);
                Toast.makeText(getApplicationContext(), "You have selected " + i, Toast.LENGTH_SHORT).show();
            } else {
                selColors.remove(repaitOptionText[i]);
            }
        });

        repaitDialog.setPositiveButton(R.string.ok_button, (dialog, id) -> {
            Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();
            repaitEditText.setText(selColors.toString());
        });
        repaitDialog.setNegativeButton(R.string.cancel_button, (dialog, id) -> Toast.makeText(getApplicationContext(), "CANCELED", Toast.LENGTH_SHORT).show());
    }

    private void setDateTimeField() {
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        checkBoxOnOff();

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            startDate.setText(dateFormatter.format(newDate.getTime()));
            endDate.setText(startDate.getText());
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
            Calendar toS = Calendar.getInstance();
            Date to = newTime.getTime();
            toS.setTime(to);
            toS.add(Calendar.HOUR_OF_DAY, 1);
            endTime.setText(timeFormatter.format(toS.getTime()));
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
        } else if (view == repaitEditText) {
            repaitDialog.show();
        }
    }

    private void checkBoxOnOff() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.allDayEvent);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
        );
    }

    private void editTextEnableOrDisable(EditText editTextName, boolean trueOrFalse, int inputType) {
        editTextName.setFocusable(trueOrFalse);
        editTextName.setEnabled(trueOrFalse);
        editTextName.setCursorVisible(trueOrFalse);
        editTextName.setInputType(inputType);
    }

    public void cancelOnClick(View view) {
        super.onBackPressed();
    }

    public void saveOnClick(View view) {
    }

}