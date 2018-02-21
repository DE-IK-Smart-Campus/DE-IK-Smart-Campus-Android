package hu.unideb.smartcampus.old.calendar.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

public class CalendarFragmentOld extends Fragment{

    private Calendar selectedDate;
    private String noEventText;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        noEventText = getResources().getString(R.string.noEventThisDay);

        selectedDate = Calendar.getInstance();
        selectedDate.setTimeZone(TimeZone.getTimeZone("Europe/Budapest"));
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);

//        sortTimetableEvent(timetableEventsResult);
//        sortCustomEvent(customEventsResult);
//
//        if (!timetableEventsResult.isEmpty()) {
//            TimetableEventListAdapter timetableEventListAdapter = new TimetableEventListAdapter(getContext(), timetableEventsResult);
//            timetabelEventlistView.setAdapter(timetableEventListAdapter);
//        } else if (timetableEventsResult.isEmpty()) {
//            timetabelEventlistView.setAdapter(null);
//            emptyTextTimetableEvent.setText(noEventText);
//            timetabelEventlistView.setEmptyView(emptyTextTimetableEvent);
//        }
//
//        if (!customEventsResult.isEmpty()) {
//            CustomEventListAdapter customEventListAdapter = new CustomEventListAdapter(getContext(), customEventsResult);
//            customEventlistView.setAdapter(customEventListAdapter);
//        } else if (customEventsResult.isEmpty()) {
//            customEventlistView.setAdapter(null);
//            emptyTextCustomEvent.setText(noEventText);
//            customEventlistView.setEmptyView(emptyTextCustomEvent);
//        }

        return view;

    }

}