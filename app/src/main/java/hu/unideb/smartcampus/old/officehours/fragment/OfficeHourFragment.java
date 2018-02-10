package hu.unideb.smartcampus.old.officehours.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.Collections;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.officehours.adapter.InstructorOfficeHourExpandableListAdapter;
import hu.unideb.smartcampus.old.officehours.adapter.SubjectInstructorExpandableListAdapter;
import hu.unideb.smartcampus.old.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.old.officehours.pojo.Instructor;
import hu.unideb.smartcampus.old.officehours.pojo.OfficeHour;
import hu.unideb.smartcampus.old.officehours.pojo.Subject;
import hu.unideb.smartcampus.task.officehours.OfficeHoursTeacherTask;

import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.EXTRA_FROM_UNTIL_DATES;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.OFFICE_HOURS_TAG;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.SELECTED_INSTRUCTOR_NAME;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM;
import static hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler.SELECTED_OFFICE_HOUR_ID;


/**
 * One of the features in Smartcampus is the Office hours.
 * This class responsible for showing view and OfficeHourHandler is the controller.
 */

public class OfficeHourFragment extends android.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office_hour, container, false);
        ExpandableListView expandableListViewList = view.findViewById(R.id.consulting_hours_ExpandableListView);

        AskSubjectsPojo subjectList = (AskSubjectsPojo) getArguments().getSerializable("SUBJECTS");
        Instructor instructor = (Instructor) getArguments().getSerializable("INSTRUCTOR");
        if (instructor == null) { // TODO
            final ExpandableListAdapter ClassChildTeacherListAdapter =
                    new SubjectInstructorExpandableListAdapter(getActivity().getApplicationContext(), subjectList.getSubjects());
            expandableListViewList.setAdapter(ClassChildTeacherListAdapter);

            expandableListViewList.setOnChildClickListener(new OnChildClickListenerOnStatusAskSubjects());
        } else {

            final ExpandableListAdapter ClassChildTeacherListAdapter;
            ClassChildTeacherListAdapter = new InstructorOfficeHourExpandableListAdapter(getActivity().getApplicationContext(),
                    Collections.singletonList(
                            instructor));
            expandableListViewList.setAdapter(ClassChildTeacherListAdapter);
            expandableListViewList.setOnChildClickListener(new OnChildClickListenerOnStatusAskInstructorCH());
            expandableListViewList.expandGroup(0);
        }
        return view;
    }

    /**
     * Implements ExpandableListView.OnChildClickListener this will happen when the user clicks any Subject's teacher.
     */
    private class OnChildClickListenerOnStatusAskSubjects implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            final Subject parentListAdapterGroup = (Subject) parent.getExpandableListAdapter().getGroup(groupPosition);
            new OfficeHoursTeacherTask(getActivity()).execute(parentListAdapterGroup.getInstructors().get(childPosition));
            return true;
        }
    }

    /**
     * Implements ExpandableListView.OnChildClickListener this will happen when the user clicks any Teacher's office hour.
     */
    private class OnChildClickListenerOnStatusAskInstructorCH implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Instructor selectedInstructor = (Instructor) parent.getExpandableListAdapter().getGroup(groupPosition);

            final OfficeHour selectedOfficeHour = selectedInstructor.getOfficeHourList().get(childPosition);

            Bundle bundle = new Bundle();
            bundle.putParcelable(EXTRA_FROM_UNTIL_DATES, selectedOfficeHour.getFromToDates());
            bundle.putLong(SELECTED_OFFICE_HOUR_ID, selectedOfficeHour.getConsultingHourId());
            bundle.putLong(SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM, selectedOfficeHour.getReservedSum());
            bundle.putString(SELECTED_INSTRUCTOR_NAME, selectedInstructor.getName());
            Fragment fragment = new OfficeHourReserveFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment, OFFICE_HOURS_TAG);
            fragmentTransaction.commitAllowingStateLoss();


            return true;
        }
    }

/*
    @Override
    public void onBackPressed() {
        final OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();
        if (officeHourHandler.getStatus().equals(OfficeHourHandler.Status.ASKINSTRUCTOROFFICEHOURS)) {
            officeHourHandler.onBackPressed();
            officeHourHandler.changeToOfficeFragmentView(new Bundle());
        }
    }
    */
}
