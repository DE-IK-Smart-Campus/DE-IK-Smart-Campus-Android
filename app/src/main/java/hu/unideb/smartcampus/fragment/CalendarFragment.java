package hu.unideb.smartcampus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.NewEventActivity;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

public class CalendarFragment extends Fragment implements OnBackPressedListener {

    CalendarView myCalendar;
    FloatingActionButton newEventFab;

    public CalendarFragment() {
    }

    private ArrayList<Item> generateData() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Item 1", "First Item on the list"));
        items.add(new Item("Item 2", "Second Item on the list"));
        items.add(new Item("Item 3", "Third Item on the list"));

        return items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarInitialize(view);

        newEventFab = (FloatingActionButton) view.findViewById(R.id.new_event_add);

        GregorianCalendar as = new GregorianCalendar();
        as.set(2017,3-1,6);


        GregorianCalendar ad = new GregorianCalendar(TimeZone.getTimeZone("Europe/Budapest"));
        ad.setTime(Calendar.getInstance().getTime());

        ListView a = (ListView) view.findViewById(R.id.calendar_event_listview);
        if(ad.getTime().toString().equals(as.getTime().toString())) {

            MyAdapter aa = new MyAdapter(getContext(), generateData());

            a.setAdapter(aa);
        }

        return view;
    }

    public void CalendarInitialize(View v) {
        myCalendar = (CalendarView) v.findViewById(R.id.calendar);

        myCalendar.setFirstDayOfWeek(2);

        GregorianCalendar ad = new GregorianCalendar(TimeZone.getTimeZone("Europe/Budapest"));
        ad.setTime(Calendar.getInstance().getTime());

        GregorianCalendar ac = new GregorianCalendar();
        ac.set(2017,3-1,4,0,0,0);


        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int mounth, int day) {
                GregorianCalendar selectedDate = new GregorianCalendar();

                selectedDate.set(year,mounth,day,0,0,0);

                newEventFab.setOnClickListener(view -> {
                    Intent i = new Intent(getContext(), NewEventActivity.class);
                    i.putExtra("selectedDate", selectedDate.getTimeInMillis());
                    startActivity(i);
                });

                ListView a = (ListView) v.findViewById(R.id.calendar_event_listview);

                if (selectedDate.getTime().toString().equals(ac.getTime().toString())) {

                    MyAdapter aa = new MyAdapter(getContext(), generateData());

                    a.setAdapter(aa);
                } else {
                    a.setAdapter(null);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {

    }
}
