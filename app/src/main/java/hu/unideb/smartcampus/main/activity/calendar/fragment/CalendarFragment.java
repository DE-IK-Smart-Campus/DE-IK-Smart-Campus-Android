package hu.unideb.smartcampus.main.activity.calendar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.calendar.activity.NewCustomEventActivity;
import hu.unideb.smartcampus.main.activity.calendar.adapter.TimetableEventListAdapter;
import hu.unideb.smartcampus.sqlite.manager.DatabaseManager;
import hu.unideb.smartcampus.sqlite.model.TimetableEvent;

public class CalendarFragment extends Fragment implements OnBackPressedListener {

    private CalendarView myCalendar;
    private FloatingActionButton newEventFab;
    private ListView timetabelEventlistView;
    private ListView customEventlistView;
    private Calendar selectedDate;
    private TextView emptyText;
    private List<TimetableEvent> timetableEvents;

    private DatabaseManager databaseManager;

    public CalendarFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        timetabelEventlistView = (ListView) view.findViewById(R.id.calendar_event);
        customEventlistView = (ListView) view.findViewById(R.id.custom_event_list);
        emptyText = (TextView) view.findViewById(android.R.id.empty);

        databaseManager = new DatabaseManager(getContext());
        databaseManager.open();

        selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.HOUR_OF_DAY, 0);
        selectedDate.set(Calendar.MINUTE, 0);
        selectedDate.set(Calendar.SECOND, 0);
        selectedDate.set(Calendar.MILLISECOND, 0);

        setUpTab(view);
        CalendarInitialize(view);

        timetableEvents = databaseManager.getAllTimetableEvent();

        //TODO
//        for(TimetableEvent e : event) {
//            for (CustomEvent c : events) {
//                if(e.getTimetableEventDate().equals(selectedDate.getTimeInMillis()) && c.getEventStartDate().equals(selectedDate.getTimeInMillis())) {
//                    TimetableEventListAdapter adapter1 = new TimetableEventListAdapter(getContext(),event);
//                    CustomEventListAdapter adapter2 = new CustomEventListAdapter(getContext(),events);
//
//                    timetabelEventlistView.setAdapter(adapter1);
//                    listView2.setAdapter(adapter2);
////                    timetabelEventlistView.setAdapter(adapter2);
//                }
//
//
//            }
//        }

//        timetabelEventlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TimetableEvent e = (TimetableEvent) timetabelEventlistView.getItemAtPosition(position);
//                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
//                intent.putExtra("eventName", e.getTimetableEventName());
//                intent.putExtra("eventDescription", e.getTimetableEventDescription());
//                intent.putExtra("eventPlace", e.getTimetableEventPlace());
//                intent.putExtra("eventDate", e.getTimetableEventDate());
//                intent.putExtra("eventStartTime", e.getTimetableEventStartTime());
//                intent.putExtra("eventEndTime", e.getTimetableEventEndTime());
//                startActivity(intent);
//            }
//        });


//        List<TimetableEvent> timetableEventsResult = databaseManager.getTimetableEventDate(selectedDate.getTimeInMillis());
//        if(timetableEventsResult.size() > 0) {
//            TimetableEventListAdapter timetableEventListAdapter = new TimetableEventListAdapter(getContext(), timetableEventsResult);
//            timetabelEventlistView.setAdapter(timetableEventListAdapter);
//        } else if (timetableEventsResult.size() == 0) {
//            timetabelEventlistView.setAdapter(null);
//            emptyText.setText(getResources().getText(R.string.noEventThisDay));
//            timetabelEventlistView.setEmptyView(emptyText);
//        }


        return view;

    }

    public void CalendarInitialize(View v) {
        newEventFab = (FloatingActionButton) v.findViewById(R.id.new_event_add);
        myCalendar = (CalendarView) v.findViewById(R.id.calendar);
        myCalendar.setFirstDayOfWeek(2);
        myCalendar.setShowWeekNumber(false);

        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate.set(year, month , dayOfMonth);
                //TODO

//                List<TimetableEvent> timetableEventsResult = databaseManager.getTimetableEventDate(selectedDate.getTimeInMillis());
//
//                if(timetableEventsResult.size() > 0) {
//                    TimetableEventListAdapter timetableEventListAdapter = new TimetableEventListAdapter(getContext(), timetableEventsResult);
//                    timetabelEventlistView.setAdapter(timetableEventListAdapter);
//                } else if (timetableEventsResult.size() == 0) {
//                    timetabelEventlistView.setAdapter(null);
//                    emptyText.setText(getResources().getText(R.string.noEventThisDay));
//                    timetabelEventlistView.setEmptyView(emptyText);
//                }

            }
        });

        newEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewCustomEventActivity.class);
                intent.putExtra("selectedDate", selectedDate.getTimeInMillis());
                startActivity(intent);
            }
        });
    }

    private void setUpTab(View view){
        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        String timetableTabText = getResources().getString(R.string.timetableTab);
        String customTabText = getResources().getString(R.string.customTab);

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(timetableTabText);
        tabSpec.setContent(R.id.timetableEventTab);
        tabSpec.setIndicator(timetableTabText);
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec(customTabText);
        tabSpec.setContent(R.id.customEventTab);
        tabSpec.setIndicator(customTabText);
        tabHost.addTab(tabSpec);
    }

    @Override
    public void onBackPressed() {
    }

}