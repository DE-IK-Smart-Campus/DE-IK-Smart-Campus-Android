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

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.NewEventActivity;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

public class CalendarFragment extends Fragment implements OnBackPressedListener {

    CalendarView myCalendar;
    GregorianCalendar Date;
    FloatingActionButton fab;

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

        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.new_event_add);

        c(v);

        return v;
    }

    public void c(View v) {
        myCalendar = (CalendarView) v.findViewById(R.id.calendar);

        myCalendar.setFirstDayOfWeek(2);
        GregorianCalendar minDate = new GregorianCalendar();
        minDate.set(2000, 0, 0);

        myCalendar.setMinDate(minDate.getTimeInMillis());

        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int mounth, int day) {
                int u = mounth + 1;
                Date = new GregorianCalendar();
                Date.set(year, u, day);

                GregorianCalendar asd = new GregorianCalendar(year, mounth, day);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getContext(), NewEventActivity.class);

                        i.putExtra("tess", asd.getTimeInMillis());

                        startActivity(i);
                    }
                });

                GregorianCalendar test = new GregorianCalendar();

                test.set(2017, 3, 4);
                ListView a = (ListView) v.findViewById(R.id.calendar_event_listview);

                if (Date.equals(test)) {

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
