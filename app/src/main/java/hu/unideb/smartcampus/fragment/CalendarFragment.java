package hu.unideb.smartcampus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.AboutUsActivity;
import hu.unideb.smartcampus.activity.MainActivity_SmartCampus;
import hu.unideb.smartcampus.activity.NewEventActivity;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

import static android.R.attr.minDate;

public class CalendarFragment extends Fragment implements OnBackPressedListener {

    CalendarView myCalendar;

    public CalendarFragment() {
    }

    private ArrayList<Item> generateData() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Item 1", "First Item on the lis" +
                "t"));
        items.add(new Item("Item 2", "Second Item on the list"));
        items.add(new Item("Item 3", "Third Item on the list"));

        return items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.new_event_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewEventActivity.class));
            }
        });


        c(v);

        return v;
    }

    public void c(View v) {
        myCalendar = (CalendarView) v.findViewById(R.id.calendar);

        myCalendar.setFirstDayOfWeek(2);
        GregorianCalendar minDate = new GregorianCalendar();
        minDate.set(2000, 00, 00);

        myCalendar.setMinDate(minDate.getTimeInMillis());

        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int mounth, int day) {
//                dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);
                int u = mounth + 1;

//                Toast.makeText(getContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + u + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
                GregorianCalendar selectedDate = new GregorianCalendar();
                selectedDate.set(year, u, day);

                GregorianCalendar test = new GregorianCalendar();

                test.set(2017, 3, 4);
                ListView a = (ListView) v.findViewById(R.id.calendar_event_listview);

                if (selectedDate.equals(test)) {


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
