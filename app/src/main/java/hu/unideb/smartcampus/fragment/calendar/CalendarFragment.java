package hu.unideb.smartcampus.fragment.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.calendar.NewCustomEventActivity;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

import static hu.unideb.smartcampus.container.Container.CALENDAR_ACTION_BAR_DATE_FORMAT_PATTERN_EN;
import static hu.unideb.smartcampus.container.Container.CALENDAR_ACTION_BAR_DATE_FORMAT_PATTERN_HU;
import static hu.unideb.smartcampus.container.Container.SELECTED_DATE_LONG;

public class CalendarFragment extends Fragment {

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;

    @BindView(R.id.event_list_view)
    ListView eventListView;

    private SimpleDateFormat dateFormatForMonth2;

    private Date selectedDate = getCurrentDate();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

//        String[] values = new String[]{"Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, values);
//
//        eventListView.setAdapter(adapter);


        List<Event> e = new ArrayList<>();
        List<TimetableEvent> k = addEvents();
        for(int i = 0; i< k.size(); i++){
            TimetableEvent z =   k.get(i);

            e.add(new Event(getResources().getColor(R.color.color_choices_3),z.getTimetableEventDate(),z));

        }

        Log.e("asd",e.toString());

        compactCalendarView.addEvents(e);



        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setCurrentSelectedDayBackgroundColor(R.color.calendar_blue);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;
                Log.e("asd", dateClicked.toString());
                Log.e("long", String.valueOf(dateClicked.getTime()));
                List<Event> events = new ArrayList<>();
                for(int i = 0; i < e.size(); i++){
                    if(dateClicked.getTime() == e.get(i).getTimeInMillis()) {
                        events.add(e.get(i));
                    }

                    ListAdapter listAdapter = new ListAdapter(getContext(),events );

                    eventListView.setAdapter(listAdapter);

                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getActivity().setTitle(dateFormatForMonth2.format(firstDayOfNewMonth));
            }
        });
        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));

    }

    @OnClick(R.id.add_new_event_fab)
    public void addNewEvent() {

        Intent intent = new Intent(getActivity(), NewCustomEventActivity.class);
        intent.putExtra(SELECTED_DATE_LONG, selectedDate.getTime());
        startActivity(intent);
        Toast.makeText(getContext(), "Selected: "
                + selectedDate.toString(), Toast.LENGTH_LONG).show();
        Log.e("asd", selectedDate.toString());
    }

        private List<TimetableEvent> addEvents(){

        TimetableEvent timetableEvent = new TimetableEvent(1, 1519081200000L,"esemény neve1", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent1 = new TimetableEvent(2, 1519167600000L,"esemény neve2", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent2 = new TimetableEvent(3, 1519254000000L,"esemény neve3", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent3 = new TimetableEvent(4, 1519081200000L,"esemény neve4", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent4 = new TimetableEvent(5, 1519340400000L,"esemény neve5", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent5 = new TimetableEvent(6, 1519426800000L,"esemény neve6", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent6 = new TimetableEvent(7, 1519513200000L,"esemény neve7", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent7 = new TimetableEvent(8, 1519599600000L,"esemény neve8", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent8 = new TimetableEvent(9, 1519686000000L,"esemény neve9", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent9 = new TimetableEvent(10, 1519772400000L,"esemény neve10", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent10 = new TimetableEvent(11, 1519081200000L,"esemény neve11", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);
            TimetableEvent timetableEvent11 = new TimetableEvent(12, 1518994800000L,"esemény neve12", "esemény leírása", "esemény helye", 2211681600000L, 2211681600000L);

            return Arrays.asList(timetableEvent, timetableEvent1,timetableEvent2,timetableEvent3,timetableEvent4,timetableEvent5,timetableEvent6,timetableEvent7,timetableEvent8,timetableEvent9,timetableEvent10,timetableEvent11);
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

}
