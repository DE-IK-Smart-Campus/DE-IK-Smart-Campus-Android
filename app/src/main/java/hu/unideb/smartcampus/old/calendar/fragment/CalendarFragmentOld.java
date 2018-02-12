package hu.unideb.smartcampus.old.calendar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.calendar.adapter.CustomEventListAdapter;
import hu.unideb.smartcampus.old.calendar.adapter.TimetableEventListAdapter;
import hu.unideb.smartcampus.old.sqlite.manager.DatabaseManager;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

public class CalendarFragmentOld extends Fragment{

    private CalendarView myCalendar;
    private FloatingActionButton newEventFab;
    private ListView timetabelEventlistView;
    private ListView customEventlistView;
    private Calendar selectedDate;
    private TextView emptyTextTimetableEvent;
    private TextView emptyTextCustomEvent;
    private String noEventText;

    private DatabaseManager databaseManager;

    public CalendarFragmentOld() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        noEventText = getResources().getString(R.string.noEventThisDay);

        databaseManager = new DatabaseManager(getContext());
        databaseManager.open();

        selectedDate = Calendar.getInstance();
        selectedDate.setTimeZone(TimeZone.getTimeZone("Europe/Budapest"));
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);

        CalendarInitialize(view);

        final Long selectDate = selectedDate.getTimeInMillis() / 1000;

        List<TimetableEvent> timetableEventsResult = databaseManager.getTimetableEventDate(selectDate);
        List<CustomEvent> customEventsResult = databaseManager.getCustomEventDate(selectDate);

        sortTimetableEvent(timetableEventsResult);
        sortCustomEvent(customEventsResult);

        if (!timetableEventsResult.isEmpty()) {
            TimetableEventListAdapter timetableEventListAdapter = new TimetableEventListAdapter(getContext(), timetableEventsResult);
            timetabelEventlistView.setAdapter(timetableEventListAdapter);
        } else if (timetableEventsResult.isEmpty()) {
            timetabelEventlistView.setAdapter(null);
            emptyTextTimetableEvent.setText(noEventText);
            timetabelEventlistView.setEmptyView(emptyTextTimetableEvent);
        }

        if (!customEventsResult.isEmpty()) {
            CustomEventListAdapter customEventListAdapter = new CustomEventListAdapter(getContext(), customEventsResult);
            customEventlistView.setAdapter(customEventListAdapter);
        } else if (customEventsResult.isEmpty()) {
            customEventlistView.setAdapter(null);
            emptyTextCustomEvent.setText(noEventText);
            customEventlistView.setEmptyView(emptyTextCustomEvent);
        }

        return view;

    }

    public void CalendarInitialize(View v) {
//        newEventFab = (FloatingActionButton) v.findViewById(R.id.new_event_add);
//        myCalendar = (CalendarView) v.findViewById(R.id.calendar);
        myCalendar.setFirstDayOfWeek(2);
        myCalendar.setShowWeekNumber(false);

        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate.set(year, month, dayOfMonth);

                Long selectDate = selectedDate.getTimeInMillis() / 1000;

                List<TimetableEvent> timetableEventsResult = databaseManager.getTimetableEventDate(selectDate);
                List<CustomEvent> customEventsResult = databaseManager.getCustomEventDate(selectDate);

                sortTimetableEvent(timetableEventsResult);
                sortCustomEvent(customEventsResult);

                if (!timetableEventsResult.isEmpty()) {
                    TimetableEventListAdapter timetableEventListAdapter = new TimetableEventListAdapter(getContext(), timetableEventsResult);
                    timetabelEventlistView.setAdapter(timetableEventListAdapter);
                } else if (timetableEventsResult.isEmpty()) {
                    timetabelEventlistView.setAdapter(null);
                    emptyTextTimetableEvent.setText(noEventText);
                    timetabelEventlistView.setEmptyView(emptyTextTimetableEvent);
                }

                if (!customEventsResult.isEmpty()) {
                    CustomEventListAdapter customEventListAdapter = new CustomEventListAdapter(getContext(), customEventsResult);
                    customEventlistView.setAdapter(customEventListAdapter);
                } else if (customEventsResult.isEmpty()) {
                    customEventlistView.setAdapter(null);
                    emptyTextCustomEvent.setText(noEventText);
                    customEventlistView.setEmptyView(emptyTextCustomEvent);
                }

            }
        });

        newEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), NewCustomEventActivity.class);
//                intent.putExtra("selectedDate", selectedDate.getTimeInMillis());
//                startActivity(intent);
            }
        });
    }



    private void sortTimetableEvent(List<TimetableEvent> timetableEventsResult) {
        Collections.sort(timetableEventsResult, new Comparator<TimetableEvent>() {
            @Override
            public int compare(TimetableEvent timetableEvent1, TimetableEvent timetableEvent2) {
                return timetableEvent1.getTimetableEventStartTime().compareTo(timetableEvent2.getTimetableEventStartTime());
            }
        });
    }

    private void sortCustomEvent(List<CustomEvent> customEventsResult) {
        Collections.sort(customEventsResult, new Comparator<CustomEvent>() {
            @Override
            public int compare(CustomEvent customEvent1, CustomEvent customEvent2) {
                return customEvent1.getEventStartDate().compareTo(customEvent2.getEventStartDate());
            }
        });
    }

}