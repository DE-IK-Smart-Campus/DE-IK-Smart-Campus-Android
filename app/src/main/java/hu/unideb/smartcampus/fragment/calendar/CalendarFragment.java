package hu.unideb.smartcampus.fragment.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;

public class CalendarFragment extends Fragment {

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;


    private SimpleDateFormat dateFormatForMonth2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.invalidate();

        ListView listView = view.findViewById(R.id.event_list_view);
        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

                listView.setAdapter(adapter);

        Locale aDefault = Locale.getDefault();

        Locale test = new Locale("hu", "HU");

        int hu = 1;

        if (aDefault.getLanguage().equals(test.getLanguage())) {
            dateFormatForMonth2 = new SimpleDateFormat("yyyy. MMMM", aDefault);
        } else {
            dateFormatForMonth2 = new SimpleDateFormat("MMMM yyyy", aDefault);

        }

        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setCurrentSelectedDayBackgroundColor(R.color.calendar_blue);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getActivity().setTitle(dateFormatForMonth2.format(firstDayOfNewMonth));
            }
        });
        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));
    }

}
