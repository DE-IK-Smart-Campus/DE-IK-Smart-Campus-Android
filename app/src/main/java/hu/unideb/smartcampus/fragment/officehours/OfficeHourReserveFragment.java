package hu.unideb.smartcampus.fragment.officehours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.pojo.officehours.FromToDatesInLong;
import hu.unideb.smartcampus.pojo.officehours.Instructor;
import hu.unideb.smartcampus.pojo.officehours.OfficeHour;

import static hu.unideb.smartcampus.fragment.officehours.OfficeHourFragment.OFFICE_HOUR_INSTRUCTOR_KEY;
import static hu.unideb.smartcampus.fragment.officehours.OfficeHourFragment.OFFICE_HOUR_SUBJECT_KEY;

/**
 * This is where the user will reserve the selected office hour.
 * //TODO New calandar event.
 * Created by Headswitcher on 2017. 03. 12..
 */

public class OfficeHourReserveFragment extends android.app.Fragment {

    public static final String OFFICE_HOURS_TAG = "OFFICE_HOURS_TAG";


    @BindView(R.id.selected_office_hours_textview)
    TextView fromUntilDatesTextView;

    @BindView(R.id.reserved_sum_textview)
    TextView reservedSumTextView;

    @BindView(R.id.office_hours_reserve_button)
    Button reserveButton;

    @BindView(R.id.office_hours_reason_editText)
    EditText reasonView;

    @BindView(R.id.office_hours_duration_editText)
    TextView durationView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_reserve_office_hour, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        Instructor selectedInstructor = (Instructor) arguments.getSerializable(OFFICE_HOUR_INSTRUCTOR_KEY);
        OfficeHour selectedOfficeHour = (OfficeHour) arguments.getSerializable(OFFICE_HOUR_SUBJECT_KEY);


      //  Long selectedOfficeHourId = selectedOfficeHour.getConsultingHourId();
      //  FromToDatesInLong fromUntilDates = selectedOfficeHour.getFromToDates();
      //  Long reservedSum = selectedOfficeHour.getReservedSum();
      //  String teacherName = selectedInstructor.getName();

    /*
        //Show selected office hour times
        if (reservedSum != null) {
            String reservedSumString = reservedSumTextView.getText() + reservedSum.toString();
            reservedSumTextView.setText(reservedSumString);
        }
        if (fromUntilDates != null) {

            Date d = new Date(fromUntilDates.getFrom());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String sDate = sdf.format(d);
            StringBuilder builder = new StringBuilder();
            builder.append(teacherName)
                    .append("\n")
                    .append(sDate).
                    append(" ").
                    append(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getFrom())).
                    append("  -  ").
                    append(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getTo()));
            final String dateDisplayName = builder.toString();

            fromUntilDatesTextView.
                    setText(dateDisplayName);
        } else {
            throw new NullPointerException("getArguments().getParcelable(EXTRA_FROM_UNTIL_DATES) IS NULL");
        }
        */

        return view;
    }

    @OnClick(R.id.office_hours_reserve_button)
    public void reserve() {
        //TODO RESERVE
    }
}
