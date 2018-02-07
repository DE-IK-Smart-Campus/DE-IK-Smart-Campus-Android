package hu.unideb.smartcampus.fragment.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hu.unideb.smartcampus.R;

public class CalendarFragment extends Fragment {

    private SimpleDateFormat dateFormatForMonth2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    CompactCalendarView compactCalendarView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compactCalendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.invalidate();

        Locale aDefault = Locale.getDefault();

        Locale test = new Locale("hu", "HU");

        int hu = 1;

        if (aDefault.getCountry().equals(test.getCountry())) {
            dateFormatForMonth2 = new SimpleDateFormat("yyyy. MMMM", aDefault);
        } else {
            dateFormatForMonth2 = new SimpleDateFormat("MMMM yyyy", aDefault);

        }

        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setCurrentSelectedDayBackgroundColor(R.color.calendar_blue);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getActivity().setTitle(dateFormatForMonth2.format(firstDayOfNewMonth));
            }
        });
        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));
    }

}
