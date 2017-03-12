package hu.unideb.smartcampus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.NewEventActivity;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

public class CalendarFragment extends Fragment implements OnBackPressedListener {

    CalendarView myCalendar;
    FloatingActionButton newEventFab;
    GregorianCalendar today;

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarInitialize(view);
        return view;
    }

    public void CalendarInitialize(View v) {
        newEventFab = (FloatingActionButton) v.findViewById(R.id.new_event_add);
        myCalendar = (CalendarView) v.findViewById(R.id.calendar);

        myCalendar.setFirstDayOfWeek(2);

        today = new GregorianCalendar();
        today.setTime(Calendar.getInstance().getTime());

        GregorianCalendar selectedDate = new GregorianCalendar();

        if (selectedDate.getTime() == null) {
            selectedDate.setTime(today.getTime());
        }

        myCalendar.setOnDateChangeListener((calendarView, year, mounth, day) -> {
            selectedDate.set(year, mounth, day, 0, 0, 0);
        });

        newEventFab.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), NewEventActivity.class);
            i.putExtra("selectedDate", selectedDate.getTimeInMillis());
            startActivity(i);
        });
    }


    @Override
    public void onBackPressed() {
    }
}
