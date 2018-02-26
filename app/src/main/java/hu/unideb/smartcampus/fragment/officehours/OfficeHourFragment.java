package hu.unideb.smartcampus.fragment.officehours;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.officehour.InstructorOfficeHourExpandableListAdapter;
import hu.unideb.smartcampus.adapter.officehour.SubjectInstructorExpandableListAdapter;
import hu.unideb.smartcampus.pojo.officehours.AskSubjectsPojo;
import hu.unideb.smartcampus.pojo.officehours.Instructor;
import hu.unideb.smartcampus.pojo.officehours.OfficeHour;
import hu.unideb.smartcampus.pojo.officehours.Subject;
import hu.unideb.smartcampus.task.officehours.OfficeHoursTeacherTask;

import static hu.unideb.smartcampus.fragment.officehours.OfficeHourReserveFragment.OFFICE_HOURS_TAG;


/**
 * One of the features in Smartcampus is the Office hours.
 * This class responsible for showing view and OfficeHourHandler is the controller.
 */

public class OfficeHourFragment extends Fragment {

    public static String OFFICE_HOUR_INSTRUCTOR_KEY = "INSTRUCTOR";
    public static String OFFICE_HOUR_SUBJECT_KEY = "OFFICE_HOUR_SUBJECT";

    @BindView(R.id.consulting_hours_ExpandableListView)
    ExpandableListView expandableListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office_hour, container, false);
        ButterKnife.bind(this, view);

        AskSubjectsPojo askSubjectsPojo1 = new AskSubjectsPojo();
            ArrayList<Subject> subjects = new ArrayList<>();
            ArrayList<Instructor> instructors = new ArrayList<>();
            instructors.add(new Instructor(1L, "Pató Pál", null));

            subjects.add(new Subject(1, "Tárgy1", instructors));
            subjects.add(new Subject(2, "Tárgy2", instructors));
            subjects.add(new Subject(3, "Tárgy3", instructors));
            askSubjectsPojo1.setSubjects(subjects);

//            bundle.putSerializable("OFFICE_HOUR_SUBJECT_KEY", askSubjectsPojo1);


//        AskSubjectsPojo subjectList = (AskSubjectsPojo) getArguments().getSerializable(OFFICE_HOUR_SUBJECT_KEY);
//        Instructor instructor = (Instructor) getArguments().getSerializable(OFFICE_HOUR_INSTRUCTOR_KEY);
       Instructor instructor1 =  new Instructor(1L, "Pató Pál", null);
        AskSubjectsPojo subjectList = askSubjectsPojo1;
//        Instructor instructor = (Instructor) getArguments().getSerializable(OFFICE_HOUR_INSTRUCTOR_KEY);
        Instructor instructor = instructor1;

        if (instructor == null) { // TODO

            final ExpandableListAdapter ClassChildTeacherListAdapter =
                    new SubjectInstructorExpandableListAdapter(getActivity().getApplicationContext(), subjectList.getSubjects());
            expandableListView.setAdapter(ClassChildTeacherListAdapter);
            expandableListView.setOnChildClickListener(new OnChildClickListenerOnStatusAskSubjects());
        } else {
            final ExpandableListAdapter ClassChildTeacherListAdapter;
            ClassChildTeacherListAdapter = new InstructorOfficeHourExpandableListAdapter(getActivity().getApplicationContext(),
                    Collections.singletonList(
                            instructor));
            expandableListView.setAdapter(ClassChildTeacherListAdapter);
            expandableListView.setOnChildClickListener(new OnChildClickListenerOnStatusAskInstructorCH());
            expandableListView.expandGroup(0);
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
            bundle.putSerializable("SELECTED_INSTRUCTOR", selectedInstructor);
            bundle.putSerializable("SELECTED_OFFICE_HOUR", selectedOfficeHour);
//
//            Fragment fragment = new OfficeHourReserveFragment();
//            fragment.setArguments(bundle);
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.drawer_layout, fragment, OFFICE_HOURS_TAG);
//            fragmentTransaction.commitAllowingStateLoss();
            return true;
        }
    }


    /*
    @Override
    public void onBackPressed() {
        Instructor instructor = (Instructor) getArguments().getSerializable(OFFICE_HOUR_INSTRUCTOR_KEY);
        if (instructor != null) {
            new OfficeHoursSubjectsTask(getActivity()).execute(); // TODO clear transactions
        }
    }
    */


}
