package hu.unideb.smartcampus.activity.calendar.picker;

import android.app.Activity;
import android.app.FragmentManager;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import hu.unideb.smartcampus.interfaces.calendar.date.EndDatePickerInterface;

import static hu.unideb.smartcampus.container.Container.DATE_PICKER_DIALOG;

public class EndDatePicker implements DatePickerDialog.OnDateSetListener {

    private Activity actualActivity;

    public EndDatePicker(Activity activity) {
        this.actualActivity = activity;
    }

    public void show(FragmentManager fragmentManager, int color, Calendar endDateCalendar) {
        DatePickerDialog datePickerDialog;

        datePickerDialog = DatePickerDialog.newInstance(
                this,
                endDateCalendar.get(Calendar.YEAR),
                endDateCalendar.get(Calendar.MONTH),
                endDateCalendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.setAccentColor(color);
        datePickerDialog.show(fragmentManager, DATE_PICKER_DIALOG);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        EndDatePickerInterface startDatePickerInterface = (EndDatePickerInterface) actualActivity;

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, monthOfYear);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        startDatePickerInterface.selectedEndDate(selectedDate);

    }
}