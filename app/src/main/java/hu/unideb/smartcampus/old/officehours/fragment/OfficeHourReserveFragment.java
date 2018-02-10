package hu.unideb.smartcampus.old.officehours.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.calendar.fragment.CalendarFragmentOld;
import hu.unideb.smartcampus.old.officehours.pojo.FromToDatesInLong;

import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.EXTRA_FROM_UNTIL_DATES;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.SELECTED_INSTRUCTOR_NAME;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.SELECTED_OFFICE_HOUR_ID;

/**
 * This is where the user will reserve the selected office hour.
 * //TODO New calandar event.
 * Created by Headswitcher on 2017. 03. 12..
 */

public class OfficeHourReserveFragment extends android.app.Fragment {

    //Long ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_reserve_office_hour, container, false);
        Long selectedOfficeHourId = getArguments().getLong(SELECTED_OFFICE_HOUR_ID);
        FromToDatesInLong fromUntilDates = getArguments().getParcelable(EXTRA_FROM_UNTIL_DATES);
        Long reservedSum = getArguments().getLong(SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM);
        String teacherName = getArguments().getString(SELECTED_INSTRUCTOR_NAME);
        TextView fromUntilDatesTextView = (TextView) view.findViewById(R.id.selected_office_hours_editText);

        //Show selected office hour times
        if (reservedSum != null) {
            TextView reservedTextView = (TextView) view.findViewById(R.id.reserved_sum_textview);
            String reservedSumString = reservedTextView.getText() + reservedSum.toString();
            reservedTextView.setText(reservedSumString);
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

        Button reserveButton = (Button) view.findViewById(R.id.office_hours_reserve_button);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView reasonView = (TextView) getView().findViewById(R.id.office_hours_reason_editText);
                if (reasonView == null) {
                    throw new NullPointerException("reasonView is null");
                }
                TextView durationView = getView().findViewById(R.id.office_hours_duration_editText);
                if (durationView == null) {
                    throw new NullPointerException("durationView is null");
                }

            }
        });
        TextView reasonView = view.findViewById(R.id.office_hours_reason_editText);

        reasonView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                getView().findViewById(R.id.office_hours_duration_editText).setVisibility(View.GONE);
                getView().findViewById(R.id.reserved_sum_textview).setVisibility(View.GONE);
                getView().findViewById(R.id.reserve_during_label).setVisibility(View.GONE);
            } else {
                getView().findViewById(R.id.office_hours_duration_editText).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.reserved_sum_textview).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.reserve_during_label).setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
//
/*    @Override
    public void onBackPressed() {
        Fragment fragment = new OfficeHourFragment();
        fragment.setArguments(getArguments());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
    */
}
