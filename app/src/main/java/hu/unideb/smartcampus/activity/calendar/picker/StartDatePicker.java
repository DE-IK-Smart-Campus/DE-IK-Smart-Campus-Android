package hu.unideb.smartcampus.activity.calendar.picker;

import android.app.Activity;
import android.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import hu.unideb.smartcampus.interfaces.calendar.date.StartDatePickerInterface;

import static hu.unideb.smartcampus.container.Container.DATE_PICKER_DIALOG;

public class StartDatePicker implements DatePickerDialog.OnDateSetListener {

    private Activity actualActivity;

    public StartDatePicker(Activity activity) {
        this.actualActivity = activity;
    }

    public void show(FragmentManager fragmentManager, int color, Calendar startDateCalendar) {
        DatePickerDialog datePickerDialog;

        datePickerDialog = DatePickerDialog.newInstance(
                this,
                startDateCalendar.get(Calendar.YEAR),
                startDateCalendar.get(Calendar.MONTH),
                startDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.setAccentColor(color);
        datePickerDialog.show(fragmentManager, DATE_PICKER_DIALOG);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        StartDatePickerInterface startDatePickerInterface = (StartDatePickerInterface) actualActivity;

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, monthOfYear);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        startDatePickerInterface.selectedDate(selectedDate);

    }
}
