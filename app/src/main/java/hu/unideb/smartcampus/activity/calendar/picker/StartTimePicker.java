package hu.unideb.smartcampus.activity.calendar.picker;

import android.app.Activity;
import android.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import hu.unideb.smartcampus.interfaces.calendar.time.StartTimePickerInterface;

import static hu.unideb.smartcampus.container.Container.TIME_PICKER_DIALOG;

public class StartTimePicker implements TimePickerDialog.OnTimeSetListener {
    private Activity actualActivity;

    public StartTimePicker(Activity activity) {
        this.actualActivity = activity;
    }

    public void show(FragmentManager frgament, int color,Calendar startTimeCalendar) {
        TimePickerDialog timePickerDialog = new TimePickerDialog();

        timePickerDialog.initialize(StartTimePicker.this, startTimeCalendar.get(Calendar.HOUR_OF_DAY),
                startTimeCalendar.get(Calendar.MINUTE),
                startTimeCalendar.get(Calendar.SECOND),
                true
        );

        timePickerDialog.setAccentColor(color);
        timePickerDialog.show(frgament, TIME_PICKER_DIALOG);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        StartTimePickerInterface startTimePickerInterface = (StartTimePickerInterface) actualActivity;

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedDate.set(Calendar.MINUTE, minute);
        selectedDate.set(Calendar.SECOND, 0);

        startTimePickerInterface.selectedTime(selectedDate);

    }
}
