package hu.unideb.smartcampus.old.officehours.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.Collections;

import hu.unideb.smartcampus.old.officehours.adapter.InstructorOfficeHourExpandableListAdapter;
import hu.unideb.smartcampus.old.officehours.adapter.SubjectInstructorExpandableListAdapter;
import hu.unideb.smartcampus.old.officehours.handler.OfficeHourHandler;
import hu.unideb.smartcampus.old.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.xmpp.Connection;



/**
 * One of the features in Smartcampus is the Office hours.
 * This class responsible for showing view and OfficeHourHandler is the controller.
 */
public class OfficeHourFragment extends Fragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
//            savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_office_hour, container, false);

//        ExpandableListView expandableListViewList = (ExpandableListView) view.findViewById(R.id.consulting_hours_ExpandableListView);

//        final OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();

//        if (officeHourHandler.getStatus().equals(OfficeHourHandler.Status.ASKSUBJECTS)) {
//            AskSubjectsPojo askSubjectsPojo = officeHourHandler.getAskSubjectsPojo();
//            final ExpandableListAdapter ClassChildTeacherListAdapter =
//                    new SubjectInstructorExpandableListAdapter(getContext(), askSubjectsPojo.getSubjects());
//            expandableListViewList.setAdapter(ClassChildTeacherListAdapter);
//            expandableListViewList.setOnChildClickListener(new OnChildClickListenerOnStatusAskSubjects());
//        }
//
//        if (officeHourHandler.getStatus().equals(OfficeHourHandler.Status.ASKINSTRUCTOROFFICEHOURS)) {
//            final ExpandableListAdapter ClassChildTeacherListAdapter;
//            ClassChildTeacherListAdapter = new InstructorOfficeHourExpandableListAdapter(getContext(),
//                    Collections.singletonList(
//                            officeHourHandler.getSelectedInstructor()));
//            expandableListViewList.setAdapter(ClassChildTeacherListAdapter);
//            expandableListViewList.setOnChildClickListener(new OnChildClickListenerOnStatusAskInstructorCH());
//            expandableListViewList.expandGroup(0);
//        }
//        LoadingDialogFragment fragmentByTag = (LoadingDialogFragment) getFragmentManager().findFragmentByTag(DIALOG_TAG);
//        fragmentByTag.nDialog.dismiss();
//        return view;
//    }

    /**
     * Implements ExpandableListView.OnChildClickListener this will happen when the user clicks any Subject's teacher.
     */
//    private class OnChildClickListenerOnStatusAskSubjects implements ExpandableListView.OnChildClickListener {
//        @Override
//        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//            Connection.getInstance().createLoadingDialogFragment(getFragmentManager(), new Bundle());
//            final Subject parentListAdapterGroup = (Subject) parent.getExpandableListAdapter().getGroup(groupPosition);
//            final int childPositionToNewThread = childPosition;
//            new Thread(new Runnable() {
//                public void run() {
//                    final OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();
//                    officeHourHandler.askInstructorOfficehours(getFragmentManager(), parentListAdapterGroup.getInstructors().get(childPositionToNewThread));
//                }
//            }).start();
//            return true;
//        }
//    }

    /**
     * Implements ExpandableListView.OnChildClickListener this will happen when the user clicks any Teacher's office hour.
     */
//    private class OnChildClickListenerOnStatusAskInstructorCH implements ExpandableListView.OnChildClickListener {
//
//        @Override
//        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//            final OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();
//            Instructor selectedInstructor = (Instructor) parent.getExpandableListAdapter().getGroup(groupPosition);
//            final OfficeHour selectedOfficeHour = selectedInstructor.getOfficeHourList().get(childPosition);
//            officeHourHandler.changeToOfficeReserveActivity(selectedOfficeHour);
//            return true;
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        final OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();
//        if (officeHourHandler.getStatus().equals(OfficeHourHandler.Status.ASKINSTRUCTOROFFICEHOURS)) {
//            officeHourHandler.onBackPressed();
//            officeHourHandler.changeToOfficeFragmentView(new Bundle());
//        }
//    }
}
