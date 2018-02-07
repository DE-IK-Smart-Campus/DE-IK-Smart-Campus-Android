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
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.calendar.activity.CustomEventDetailsActivity;
import hu.unideb.smartcampus.old.calendar.activity.TimetableEventDetailsActivity;
import hu.unideb.smartcampus.old.calendar.adapter.CustomEventListAdapter;
import hu.unideb.smartcampus.old.calendar.adapter.TimetableEventListAdapter;
import hu.unideb.smartcampus.old.sqlite.manager.DatabaseManager;
import hu.unideb.smartcampus.old.sqlite.model.CustomEvent;
import hu.unideb.smartcampus.old.sqlite.model.TimetableEvent;

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

//        timetabelEventlistView = (ListView) view.findViewById(R.id.timetable_event);
//        customEventlistView = (ListView) view.findViewById(R.id.custom_event_list);
//        emptyTextTimetableEvent = (TextView) view.findViewById(R.id.emptyTextTimetableEvent);
//        emptyTextCustomEvent = (TextView) view.findViewById(R.id.emptyTextCustomEvent);

        noEventText = getResources().getString(R.string.noEventThisDay);

        databaseManager = new DatabaseManager(getContext());
        databaseManager.open();

        selectedDate = Calendar.getInstance();
        selectedDate.setTimeZone(TimeZone.getTimeZone("Europe/Budapest"));
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);

        setUpTab(view);
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

        timetabelEventlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimetableEvent selectedTimetableEvent = (TimetableEvent) timetabelEventlistView.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), TimetableEventDetailsActivity.class);
                intent.putExtra("eventName", selectedTimetableEvent.getTimetableEventName());
                intent.putExtra("eventDescription", selectedTimetableEvent.getTimetableEventDescription());
                intent.putExtra("eventPlace", selectedTimetableEvent.getTimetableEventPlace());
                intent.putExtra("eventDate", selectedTimetableEvent.getTimetableEventDate());
                intent.putExtra("eventStartTime", selectedTimetableEvent.getTimetableEventStartTime());
                intent.putExtra("eventEndTime", selectedTimetableEvent.getTimetableEventEndTime());
                startActivity(intent);
            }
        });

        customEventlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomEvent selectedCustomEvent = (CustomEvent) customEventlistView.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), CustomEventDetailsActivity.class);
                intent.putExtra("uuid", selectedCustomEvent.getUuid());
                intent.putExtra("name", selectedCustomEvent.getEventName());
                intent.putExtra("description", selectedCustomEvent.getEventDescription());
                intent.putExtra("place", selectedCustomEvent.getEventPlace());
                intent.putExtra("startDate", selectedCustomEvent.getEventStartDate());
                intent.putExtra("startTime", selectedCustomEvent.getEventStartTime());
                intent.putExtra("endDate", selectedCustomEvent.getEventEndDate());
                intent.putExtra("endTime", selectedCustomEvent.getEventEndTime());
                intent.putExtra("repeat", selectedCustomEvent.getEvenetRepeat());
                intent.putExtra("remainder", selectedCustomEvent.getEventReminder());
                startActivity(intent);
            }
        });
//        LoadingDialogFragment fragmentByTag = (LoadingDialogFragment) getFragmentManager().findFragmentByTag(DIALOG_TAG);
//        fragmentByTag.nDialog.dismiss();
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

    private void setUpTab(View view) {
//        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);
//        tabHost.setup();
//
//        String timetableTabText = getResources().getString(R.string.timetableTab);
//        String customTabText = getResources().getString(R.string.customTab);
//
//        TabHost.TabSpec tabSpec = tabHost.newTabSpec(timetableTabText);
//        tabSpec.setContent(R.id.timetableEventTab);
//        tabSpec.setIndicator(timetableTabText);
//        tabHost.addTab(tabSpec);
//
//
//        tabSpec = tabHost.newTabSpec(customTabText);
//        tabSpec.setContent(R.id.customEventTab);
//        tabSpec.setIndicator(customTabText);
//        tabHost.addTab(tabSpec);
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

//    @Override
//    public void onBackPressed() {
//    }

}