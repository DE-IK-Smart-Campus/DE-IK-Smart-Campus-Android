package hu.unideb.smartcampus.main.activity.calendar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.EventDetailsActivity;
import hu.unideb.smartcampus.activity.NewEventActivity;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.calendar.adapter.TimetableEventListAdapter;
import hu.unideb.smartcampus.main.activity.calendar.pojo.TimetableEvent;
import hu.unideb.smartcampus.main.activity.calendar.pojo.TimetableEventDate;

public class CalendarFragment extends Fragment implements OnBackPressedListener {

    private CalendarView myCalendar;
    private FloatingActionButton newEventFab;
    private List<TimetableEventDate> calendarDates;
    private ListView listView;
    private Calendar selectedDate;

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        listView = (ListView) view.findViewById(R.id.calendar_event);

        calendarDates = new ArrayList<>();

        selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);

        Calendar g1 = Calendar.getInstance();
        g1.set(2017, 2, 15);
        g1.set(Calendar.HOUR_OF_DAY, 0);
        g1.set(Calendar.MINUTE, 0);
        g1.set(Calendar.SECOND, 0);
        g1.set(Calendar.MILLISECOND, 0);

        Calendar g2 = Calendar.getInstance();
        g2.set(2017, 2, 22);
        g2.set(Calendar.HOUR_OF_DAY, 0);
        g2.set(Calendar.MINUTE, 0);
        g2.set(Calendar.SECOND, 0);
        g2.set(Calendar.MILLISECOND, 0);

        Calendar g3 = Calendar.getInstance();
        g3.set(2017, 2, 14);
        g3.set(Calendar.HOUR_OF_DAY, 0);
        g3.set(Calendar.MINUTE, 0);
        g3.set(Calendar.SECOND, 0);
        g3.set(Calendar.MILLISECOND, 0);


        Calendar g4 = Calendar.getInstance();
        g4.set(2017, 2, 17);
        g4.set(Calendar.HOUR_OF_DAY, 0);
        g4.set(Calendar.MINUTE, 0);
        g4.set(Calendar.SECOND, 0);
        g4.set(Calendar.MILLISECOND, 0);


        Calendar g5 = Calendar.getInstance();
        g5.set(2017, 2, 18);
        g5.set(Calendar.HOUR_OF_DAY, 0);
        g5.set(Calendar.MINUTE, 0);
        g5.set(Calendar.SECOND, 0);
        g5.set(Calendar.MILLISECOND, 0);

        Calendar g6 = Calendar.getInstance();
        g6.set(2017, 2, 20);
        g6.set(Calendar.HOUR_OF_DAY, 0);
        g6.set(Calendar.MINUTE, 0);
        g6.set(Calendar.SECOND, 0);
        g6.set(Calendar.MILLISECOND, 0);

        Calendar g10 = Calendar.getInstance();
        g10.set(2017, 2, 21);
        g10.set(Calendar.HOUR_OF_DAY, 0);
        g10.set(Calendar.MINUTE, 0);
        g10.set(Calendar.SECOND, 0);
        g10.set(Calendar.MILLISECOND, 0);

        GregorianCalendar g7 = new GregorianCalendar();
        g7.set(0, 0, 0, 8, 0, 0);

        GregorianCalendar g8 = new GregorianCalendar();
        g8.set(0, 0, 0, 10, 0, 0);

        Date start = g7.getTime();
        Date end = g8.getTime();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);


        List<TimetableEvent> even = new ArrayList<>();
        even.add(new TimetableEvent("Name", "leírás", "hely", start.getTime(), end.getTime()));
        even.add(new TimetableEvent("Name2", "leírás", "hely", start.getTime(), end.getTime()));
        even.add(new TimetableEvent("Name4", "leírás", "hely", start.getTime(), end.getTime()));

        List<TimetableEvent> even1 = new ArrayList<>();
        even1.add(new TimetableEvent("Name4", "leírás", "hely", start.getTime(), end.getTime()));

        List<TimetableEvent> even2 = new ArrayList<>();
        even2.add(new TimetableEvent("Name4234", "leírás", "hely", start.getTime(), end.getTime()));

        List<TimetableEvent> even3 = new ArrayList<>();
        even3.add(new TimetableEvent("Name42342343432342", "leírás", "hely", start.getTime(), end.getTime()));

        List<TimetableEvent> even4 = new ArrayList<>();
        even4.add(new TimetableEvent("Name423432432", "leírás", "hely", start.getTime(), end.getTime()));

        List<TimetableEvent> even5 = new ArrayList<>();
        even5.add(new TimetableEvent("Name42343243", "leírás", "hely", start.getTime(), end.getTime()));


        TimetableEventDate aa = new TimetableEventDate(g1.getTimeInMillis(), even);
        TimetableEventDate a2 = new TimetableEventDate(g2.getTimeInMillis(), even1);
        TimetableEventDate a3 = new TimetableEventDate(g3.getTimeInMillis(), even2);
        TimetableEventDate a4 = new TimetableEventDate(g4.getTimeInMillis(), even3);
        TimetableEventDate a5 = new TimetableEventDate(g5.getTimeInMillis(), even4);
        TimetableEventDate a6 = new TimetableEventDate(g10.getTimeInMillis(), even5);

        calendarDates.add(aa);
        calendarDates.add(a2);
        calendarDates.add(a3);
        calendarDates.add(a4);
        calendarDates.add(a5);
        calendarDates.add(a6);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimetableEvent e = (TimetableEvent) listView.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra("eventName", e.getTimetableEventName());
                intent.putExtra("eventDescription", " ");
                intent.putExtra("eventPlace", e.getTimetableEventPlace());
                intent.putExtra("eventDate", selectedDate.getTime().getTime());
                intent.putExtra("eventStartTime", DateFormat.getTimeInstance(DateFormat.SHORT).format(e.getTimetableStartTime()));
                intent.putExtra("eventEndTime", DateFormat.getTimeInstance(DateFormat.SHORT).format(e.getTimetableEndTime()));
                startActivity(intent);
            }
        });

        CalendarInitialize(view);

        for (TimetableEventDate asd : calendarDates) {
            if (asd.getTimetableEventDate().equals(today.getTimeInMillis())) {
                TimetableEventListAdapter adapter = new TimetableEventListAdapter(getContext(), asd.getTimetableEventList());
                listView.setAdapter(adapter);
            }
        }
        return view;
    }

    public void CalendarInitialize(View v) {
        newEventFab = (FloatingActionButton) v.findViewById(R.id.new_event_add);
        myCalendar = (CalendarView) v.findViewById(R.id.calendar);
        myCalendar.setShowWeekNumber(false);
        myCalendar.setFirstDayOfWeek(2);

        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate.set(year, month, dayOfMonth);
                for (TimetableEventDate asd : calendarDates) {
                    if (selectedDate.getTimeInMillis() == asd.getTimetableEventDate()) {
                        listView.setAdapter(new TimetableEventListAdapter(getContext(), asd.getTimetableEventList()));
                        break;
                    } else {
                        listView.setAdapter(null);
                    }
                }
            }
        });

        newEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewEventActivity.class);
                intent.putExtra("selectedDate", selectedDate.getTimeInMillis());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}