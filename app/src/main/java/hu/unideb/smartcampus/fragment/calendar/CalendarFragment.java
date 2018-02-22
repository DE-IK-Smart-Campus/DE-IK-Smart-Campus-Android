package hu.unideb.smartcampus.fragment.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import es.dmoral.toasty.Toasty;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.calendar.custom.NewCustomEventActivity;
import hu.unideb.smartcampus.activity.calendar.details.EventDetailsActivity;
import hu.unideb.smartcampus.listview.adapter.event.EventListAdapter;
import hu.unideb.smartcampus.pojo.calendar.CalendarEvent;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;
import hu.unideb.smartcampus.pojo.calendar.type.EventType;

import static hu.unideb.smartcampus.container.Container.CALENDAR_ACTION_BAR_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.CALENDAR_ACTION_BAR_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.SELECTED_DATE_LONG;

public class CalendarFragment extends Fragment {

    private final String TAG = CalendarFragment.class.getSimpleName();

    @BindView(R.id.compact_calendar_view)
    CompactCalendarView compactCalendarView;

    @BindView(R.id.event_list_view)
    ListView eventListView;

    @BindView(R.id.emptyElement)
    TextView textView;

    private SimpleDateFormat dateFormatForMonth2;
    private Date selectedDate = getCurrentDate();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.invalidate();

        Locale defaultLocale = Locale.getDefault();
        Locale hunLocale = new Locale("hu", "hu");

        if (defaultLocale.getLanguage().equals(hunLocale.getLanguage())) {
            dateFormatForMonth2 = new SimpleDateFormat(CALENDAR_ACTION_BAR_DATE_FORMAT_PATTERN_HU, defaultLocale);
        } else {
            dateFormatForMonth2 = new SimpleDateFormat(CALENDAR_ACTION_BAR_DATE_FORMAT_PATTERN_EN, defaultLocale);

        }

        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        eventListView.setEmptyView(textView);

        List<TimetableEvent> timetableEvents = addEvents();
        List<CustomEvent> customEvents = addEvents1();
        int color = getResources().getColor(R.color.color_choices_3);
        int color1 = getResources().getColor(R.color.color_choices_17);

        List<Event> events = new ArrayList<>();

        for (int i = 0; i < timetableEvents.size(); i++) {
            TimetableEvent timetableEvent = timetableEvents.get(i);
            events.add(new Event(color, timetableEvent.getTimetableEventDate(), new CalendarEvent(timetableEvent.getTimetableEventName(), timetableEvent.getTimetableEventPlace(), timetableEvent.getTimetableEventStartTime(), timetableEvent.getTimetableEventEndTime(), EventType.TIMETABLE, timetableEvent, null)));
        }

        for (int i = 0; i < customEvents.size(); i++) {
            CustomEvent customEvent = customEvents.get(i);
            events.add(new Event(color1, customEvent.getEventStartDate(), new CalendarEvent(customEvent.getEventName(), customEvent.getEventPlace(), customEvent.getEventStartTime(), customEvent.getEventEndTime(), EventType.CUSTOM,null, customEvent)));
        }

        List<Event> events1 = new ArrayList<>();

        for (int i = 0; i < events.size(); i++) {
            if (getCurrentDate().getTime() == events.get(i).getTimeInMillis()) {
                events1.add(events.get(i));

                EventListAdapter eventListAdapter = new EventListAdapter(getContext(), events1);

                eventListView.setAdapter(eventListAdapter);

            }
        }

        compactCalendarView.addEvents(events);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;

                eventListView.setAdapter(null);

                List<Event> events1 = new ArrayList<>();
                for (int i = 0; i < events.size(); i++) {
                    if (dateClicked.getTime() == events.get(i).getTimeInMillis()) {
                        events1.add(events.get(i));
                    }

                    sortEventsStartTime(events1);
                    EventListAdapter eventListAdapter = new EventListAdapter(getContext(), events1);

                    eventListView.setAdapter(eventListAdapter);

                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getActivity().setTitle(dateFormatForMonth2.format(firstDayOfNewMonth));
            }
        });
        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));
    }

    @OnItemClick(R.id.event_list_view)
    public void eventListViewClickListener(int position) {
        Event listItem = (Event) eventListView.getItemAtPosition(position);

        CalendarEvent t = (CalendarEvent) listItem.getData();

        if((t != null ? t.getEventType() : null) == EventType.TIMETABLE) {

            startIntet(EventType.TIMETABLE.name(), t.getTimetableEvent());
//            Log.e("timetable", t.getTimetableEvent().toString());
//            Toasty.info(getContext(), "Timetable", Toast.LENGTH_SHORT).show();
        } else if((t != null ? t.getEventType() : null) == EventType.CUSTOM){

            startIntet(EventType.CUSTOM.name(), t.getCustomEvent());

//            Log.e("custom", t.getCustomEvent().toString());
//            Toasty.info(getContext(), "custom", Toast.LENGTH_SHORT).show();

        }
    }

    private void startIntet(String eventType, Object object){
                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
            intent.putExtra("TEST1",eventType);
            intent.putExtra("TEST", (Serializable) object);
            startActivity(intent);

    }

    @OnClick(R.id.add_new_event_fab)
    public void addNewEvent() {
        Intent intent = new Intent(getActivity(), NewCustomEventActivity.class);
        intent.putExtra(SELECTED_DATE_LONG, selectedDate.getTime());
        startActivity(intent);
        Log.d(TAG, selectedDate.toString());
    }

    private Date getCurrentDate() {
        Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.HOUR_OF_DAY, 0);
        currentCalender.set(Calendar.MINUTE, 0);
        currentCalender.set(Calendar.SECOND, 0);
        currentCalender.set(Calendar.MILLISECOND, 0);
        return currentCalender.getTime();
    }

    private void sortEventsStartTime(List<Event> events) {
        Collections.sort(events, (event1, event2) -> {
            CalendarEvent calendarEvent1 = (CalendarEvent) event1.getData();
            CalendarEvent calendarEvent2 = (CalendarEvent) event2.getData();
            return calendarEvent1 != null ? calendarEvent1.getEventStartTime().compareTo(calendarEvent2 != null ? calendarEvent2.getEventStartTime() : null) : 0;
        });
    }


    private List<TimetableEvent> addEvents() {

        TimetableEvent timetableEvent = new TimetableEvent(1, 1519081200000L, "esemény neve1", "esemény leírása", "esemény helye", 2211703200000L, 2211681600000L);
        TimetableEvent timetableEvent1 = new TimetableEvent(2, 1519167600000L, "esemény neve2", "esemény leírása", "esemény helye", 2211703200000L, 2211681600000L);
        TimetableEvent timetableEvent2 = new TimetableEvent(3, 1519254000000L, "Teszt esemény", "Teszt leírás", "Teszt hely", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent3 = new TimetableEvent(4, 1519081200000L, "esemény neve4", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent4 = new TimetableEvent(5, 1519340400000L, "esemény neve5", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent5 = new TimetableEvent(6, 1519426800000L, "esemény neve6", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent6 = new TimetableEvent(7, 1519513200000L, "esemény neve7", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent7 = new TimetableEvent(8, 1519599600000L, "esemény neve8", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent8 = new TimetableEvent(9, 1519686000000L, "esemény neve9", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent9 = new TimetableEvent(10, 1519772400000L, "esemény neve10", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent10 = new TimetableEvent(11, 1519081200000L, "esemény neve11", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
        TimetableEvent timetableEvent11 = new TimetableEvent(12, 1518994800000L, "esemény neve12", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);

        return Arrays.asList(timetableEvent, timetableEvent1, timetableEvent2, timetableEvent3, timetableEvent4, timetableEvent5, timetableEvent6, timetableEvent7, timetableEvent8, timetableEvent9, timetableEvent10, timetableEvent11);
    }


    private List<CustomEvent> addEvents1() {


        CustomEvent customEvent = new CustomEvent("1", "esemény1", "esemény leírása", "esemény helye", 1519081200000L, 2211681600000L, 1519081200000L, 2211681600000L, "re", "re");
        CustomEvent customEvent1 = new CustomEvent("1", "esemény2", "esemény leírása", "esemény helye", 1519081200000L, 2211714000000L, 1519081200000L, 2211681600000L, "re", "re");
        CustomEvent customEvent2 = new CustomEvent("1", "esemény3", "esemény leírása", "esemény helye", 1519081200000L, 2211699600000L, 1519081200000L, 2211681600000L, "re", "re");
        CustomEvent customEvent3 = new CustomEvent("1", "esemény4", "esemény leírása", "esemény helye", 1519081200000L, 2211681600000L, 1519081200000L, 2211681600000L, "re", "re");


        return Arrays.asList(customEvent, customEvent1, customEvent2, customEvent3);
    }
}
