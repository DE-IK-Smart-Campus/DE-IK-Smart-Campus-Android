package hu.unideb.smartcampus.fragment.calendar;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.unideb.smartcampus.R;

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
            dateFormatForMonth2 = new SimpleDateFormat("yyyy. MMMM", defaultLocale);
        } else {
            dateFormatForMonth2 = new SimpleDateFormat("MMMM yyyy", defaultLocale);

        }

        String[] values = new String[]{"Android List View",
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

        eventListView.setAdapter(adapter);

        getActivity().setTitle(dateFormatForMonth2.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setCurrentSelectedDayBackgroundColor(R.color.calendar_blue);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;
                Log.e("asd", dateClicked.toString());
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


        Toast.makeText(getContext(), "Selected: "
                + selectedDate.toString(), Toast.LENGTH_LONG).show();
        Log.e("asd", selectedDate.toString());

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
