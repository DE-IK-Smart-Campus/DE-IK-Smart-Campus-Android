package hu.unideb.smartcampus.main.activity.officehours.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.calendar.fragment.CalendarFragment;
import hu.unideb.smartcampus.main.activity.officehours.pojo.FromToDatesInLong;

import static hu.unideb.smartcampus.activity.MainActivity_SmartCampus.CURRENT_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_FROM_UNTIL_DATES;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.OFFICE_HOURS_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SELECTED_OFFICE_HOUR_ID;

/**
 * This is where the user will reserve the selected office hour.
 * //TODO New calandar event.
 * Created by Headswitcher on 2017. 03. 12..
 */

public class OfficeHourReserveFragment extends Fragment implements OnBackPressedListener {

    Long selectedOfficeHourId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_reserve_consultation, container, false);
        selectedOfficeHourId = getArguments().getLong(SELECTED_OFFICE_HOUR_ID);
        FromToDatesInLong fromUntilDates = getArguments().getParcelable(EXTRA_FROM_UNTIL_DATES);

        //Show selected office hour times
        TextView fromUntilDatesTextView = (TextView) view.findViewById(R.id.selected_consulting_hour_editText);
        if (fromUntilDates != null) {
            fromUntilDatesTextView.
                    setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getFrom())
                            + "-"
                            + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getTo()));
        } else {
            throw new NullPointerException("getArguments().getParcelable(EXTRA_FROM_UNTIL_DATES) IS NULL");
        }
        if (selectedOfficeHourId != null) {


            Button reserveButton = (Button) view.findViewById(R.id.consulting_hour_reserve_button);

            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView reasonView = (TextView) getView().findViewById(R.id.consulting_hours_reason_editText);
                    if (reasonView == null) {
                        throw new NullPointerException("reasonView is null");
                    }
                    TextView durationView = (TextView) getView().findViewById(R.id.consulting_hours_duration_editText);
                    if (durationView == null) {
                        throw new NullPointerException("durationView is null");
                    }
                    CalendarFragment fragment = new CalendarFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();


                }
            });
        } else {
            throw new NullPointerException("getArguments().getLong(SELECTED_OFFICE_HOUR_ID) IS NULL");
        }
        return view;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = new OfficeHourFragment();
        fragment.setArguments(getArguments());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
}