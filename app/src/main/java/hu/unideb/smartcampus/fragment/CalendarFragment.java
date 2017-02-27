package hu.unideb.smartcampus.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import hu.unideb.smartcampus.R;

public class CalendarFragment extends Fragment {

    CalendarView myCalendar;

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        myCalendar = (CalendarView) v.findViewById(R.id.calendar);

        myCalendar.setFirstDayOfWeek(2);

        return v;
    }
}
