package hu.unideb.smartcampus.main.activity.attendance.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.attendance.adapter.SubjectDateExpandableListAdaper;
import hu.unideb.smartcampus.main.activity.attendance.pojo.Subject;
import hu.unideb.smartcampus.main.activity.attendance.pojo.SubjectDate;

public class AttendanceFragment extends Fragment implements OnBackPressedListener {

    public AttendanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_attendance, container, false);

        ExpandableListView expandableListViewList = (ExpandableListView) view.findViewById(R.id.class_attendance_ExpandableListView);

        GregorianCalendar c1 = new GregorianCalendar();
        c1.set(2017, 2, 22);
        GregorianCalendar c2 = new GregorianCalendar();
        c2.set(2017, 2, 29);
        GregorianCalendar c3 = new GregorianCalendar();
        c3.set(2017, 3, 5);
        GregorianCalendar c4 = new GregorianCalendar();
        c4.set(2017, 3, 12);

        SubjectDate subjectDate1 = new SubjectDate(c1.getTimeInMillis());
        SubjectDate subjectDate2 = new SubjectDate(c2.getTimeInMillis());
        SubjectDate subjectDate3 = new SubjectDate(c3.getTimeInMillis());
        SubjectDate subjectDate4 = new SubjectDate(c4.getTimeInMillis());

        List<SubjectDate> subjectDateList = Arrays.asList(subjectDate1, subjectDate2, subjectDate3, subjectDate4);

        Subject subject = new Subject("Programozási technológiák", subjectDateList);
        Subject subject2 = new Subject("Programozási környezetek", subjectDateList);
        Subject subject3 = new Subject("Mestint", subjectDateList);

        List<Subject> subjects = Arrays.asList(subject, subject2, subject3);

        final ExpandableListAdapter SubjectChildDateListAdapter = new SubjectDateExpandableListAdaper(subjects, getContext());
        expandableListViewList.setAdapter(SubjectChildDateListAdapter);
        expandableListViewList.setOnChildClickListener(new OnChildClicikListenerOnStatusAskDates());

        return view;
    }

    private class OnChildClicikListenerOnStatusAskDates implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Subject parentList = (Subject) parent.getExpandableListAdapter().getGroup(groupPosition);
            final SubjectDate subjectDate = parentList.getSubjectDates().get(childPosition);

            return true;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
