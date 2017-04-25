package hu.unideb.smartcampus.main.activity.attendance.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.apache.commons.lang3.StringUtils;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.attendance.adapter.SubjectDateExpandableListAdaper;
import hu.unideb.smartcampus.main.activity.attendance.pojo.AskSubjectPojo;
import hu.unideb.smartcampus.main.activity.attendance.pojo.Subject;
import hu.unideb.smartcampus.main.activity.attendance.pojo.SubjectDate;

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.ASKSUBJECTSDATE;
import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.ASKSUBJECTSNAME;
import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.ASK_SUBJECTS_PROCESS_MESSAGE_POJO;
import static hu.unideb.smartcampus.main.activity.attendance.constant.AttendanceConstant.STATUSATTENDANCE;

public class AttendanceFragment extends Fragment implements OnBackPressedListener {

    private int lastExpandedPosition = -1;

    public AttendanceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_attendance, container, false);

        final ExpandableListView expandableListViewList = (ExpandableListView) view.findViewById(R.id.class_attendance_ExpandableListView);

        if (StringUtils.equals(getArguments().getString(STATUSATTENDANCE), ASKSUBJECTSNAME)) {
            AskSubjectPojo askSubjectPojo = getArguments().getParcelable(ASK_SUBJECTS_PROCESS_MESSAGE_POJO);
            final ExpandableListAdapter SubjectExpandableListAdapter;
            if (askSubjectPojo != null) {
                SubjectExpandableListAdapter = new SubjectDateExpandableListAdaper(askSubjectPojo.getSubjectList(), getContext());
                expandableListViewList.setAdapter(SubjectExpandableListAdapter);
                expandableListViewList.setOnChildClickListener(new OnChildClicikListenerOnStatusAskDates());

                expandableListViewList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (lastExpandedPosition != -1
                                && groupPosition != lastExpandedPosition) {
                            expandableListViewList.collapseGroup(lastExpandedPosition);
                        }
                        lastExpandedPosition = groupPosition;
                    }
                });

            } else {
                throw new NullPointerException("getArguments().getParcelable(ASK_SUBJECTS_PROCESS_MESSAGE_POJO IS NULL");
            }
        }

        return view;
    }

    private class OnChildClicikListenerOnStatusAskDates implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Bundle bundle = new Bundle();
            bundle.putString(STATUSATTENDANCE, ASKSUBJECTSDATE);
            Subject parentList = (Subject) parent.getExpandableListAdapter().getGroup(groupPosition);
            bundle.putInt("asd", childPosition);
            bundle.putInt("asd", groupPosition);
            final SubjectDate subjectDate = parentList.getSubjectDates().get(childPosition);
            SubjectDate subjectDate1 = new SubjectDate();
            subjectDate1.setDate(subjectDate.getDate());
            subjectDate1.setDateId(subjectDate.getDateId());
            subjectDate1.setYesOrNo(subjectDate.getYesOrNo());
            Log.e(TAG, "onChildClick: " + subjectDate1);
            return true;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
