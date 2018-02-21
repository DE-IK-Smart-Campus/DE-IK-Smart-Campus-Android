package hu.unideb.smartcampus.old.calendar.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class NewCustomEventActivity extends AppCompatActivity {

    private void checkBoxOnOff() {
//        final CheckBox checkBox = (CheckBox) findViewById(R.id.allDayEvent);

//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (checkBox.isChecked()) {
//                    startTime.setVisibility(View.INVISIBLE);
//                    endTime.setVisibility(View.INVISIBLE);
//                    endDate.setText(startDate.getText());
//                    editTextEnableOrDisable(endDate, false, InputType.TYPE_NULL);
//                    startTimeCalendar.set(Calendar.HOUR_OF_DAY,0);
//                    startTimeCalendar.set(Calendar.MINUTE,0);
//                    startTimeCalendar.set(Calendar.MILLISECOND,0);
//
//                    endTimeCalendar.set(Calendar.HOUR_OF_DAY,0);
//                    endTimeCalendar.set(Calendar.MINUTE,0);
//                    endTimeCalendar.set(Calendar.MILLISECOND,0);
//                } else if (!checkBox.isChecked()) {
//                    startTime.setVisibility(View.VISIBLE);
//                    endTime.setVisibility(View.VISIBLE);
//                    editTextEnableOrDisable(endDate, true, InputType.TYPE_DATETIME_VARIATION_DATE);
//                }
//            }
//        });
    }

    private void repeatSetup() {
//        Spinner repeatSpinner = (Spinner) findViewById(R.id.repeatSpinner);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeats_array_item, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        repeatSpinner.setAdapter(adapter);
//
//        repeatSpinner.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view1,
//                                               int pos, long id) {
////                        Toast.makeText(getApplicationContext(), "You have selected " + parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
//                        repeatSelect = parent.getItemAtPosition(pos).toString();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//
//                    }
//
//                }
//        );
    }

    private void editTextEnableOrDisable(EditText editTextName, boolean trueOrFalse, int inputType) {
        editTextName.setFocusable(trueOrFalse);
        editTextName.setEnabled(trueOrFalse);
        editTextName.setCursorVisible(trueOrFalse);
        editTextName.setInputType(inputType);
    }

    public void saveOnClick(View view) {
        String uuid = UUID.randomUUID().toString();
//        startTimeCalendar.set(Calendar.YEAR, startDateCalendar.get(Calendar.YEAR));
//        startTimeCalendar.set(Calendar.MONTH, startDateCalendar.get(Calendar.MONTH));
//        startTimeCalendar.set(Calendar.DAY_OF_MONTH, startDateCalendar.get(Calendar.DAY_OF_MONTH));
//        endTimeCalendar.set(Calendar.YEAR, startDateCalendar.get(Calendar.YEAR));
//        endTimeCalendar.set(Calendar.MONTH, startDateCalendar.get(Calendar.MONTH));
//        endTimeCalendar.set(Calendar.DAY_OF_MONTH, startDateCalendar.get(Calendar.DAY_OF_MONTH));

//        AddCustomEventHandler.add(uuid, startDateCalendar.getTimeInMillis() /1000, eventName.getText().toString(), eventDescription.getText().toString(), eventPlace.getText().toString(), startTimeCalendar.getTimeInMillis() /1000, endTimeCalendar.getTimeInMillis() /1000, repeatSelect, remainderSelect);
//        databaseManager.insertCustomEvent(new CustomEvent(uuid, eventName.getText().toString(),eventDescription.getText().toString(),eventPlace.getText().toString(),startDateCalendar.getTimeInMillis(), startTimeCalendar.getTimeInMillis(),endDateCalendar.getTimeInMillis(), endTimeCalendar.getTimeInMillis(), repeatSelect, remainderSelect));

//        DeleteCustomEventHandler.delete("b58ddd62-ba30-42d9-8e3d-0e1edf761026");

        Toast.makeText(this, "Esemény hozzáadva", Toast.LENGTH_SHORT).show();
//        super.onBackPressed();
    }
}