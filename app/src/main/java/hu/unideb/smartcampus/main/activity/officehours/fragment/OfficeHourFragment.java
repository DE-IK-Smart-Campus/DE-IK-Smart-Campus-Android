package hu.unideb.smartcampus.main.activity.officehours.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.officehours.adapter.InstructorOfficeHourExpandableListAdapter;
import hu.unideb.smartcampus.main.activity.officehours.adapter.SubjectInstructorExpandableListAdapter;
import hu.unideb.smartcampus.main.activity.officehours.json.MessageTypeInstructorIdUserId;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Instructor;
import hu.unideb.smartcampus.main.activity.officehours.pojo.OfficeHour;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Subject;

import static hu.unideb.smartcampus.activity.MainActivity_SmartCampus.CURRENT_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTOR;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_FROM_UNTIL_DATES;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.INSTRUCTORPOS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.OFFICE_HOURS_TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SELECTED_OFFICE_HOUR_ID;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.STATUSOFCONSULTINGHOURS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SUBJECTPOS;

/**
 * One of the main fatures for Smart campus is the Office hours.
 * This class responsible for showing view
 */
public class OfficeHourFragment extends Fragment implements OnBackPressedListener {

    /**
     * Will create view for OfficeHourFragment witch depends on the STATUSOFCONSULTINGHOURS.
     *
     * @see hu.unideb.smartcampus.main.activity.officehours.handler.OfficeHourHandler
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulting_hour, container, false);

        ExpandableListView expandableListViewList = (ExpandableListView) view.findViewById(R.id.consulting_hours_ExpandableListView);

        if (StringUtils.equals(getArguments().getString(STATUSOFCONSULTINGHOURS), ASKSUBJECTS)) {
            AskSubjectsPojo askSubjectsPojo = getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO);
            final ExpandableListAdapter ClassChildTeacherListAdapter;
            if (askSubjectsPojo != null) {
                ClassChildTeacherListAdapter = new SubjectInstructorExpandableListAdapter(getContext(), askSubjectsPojo.getSubjects());
                expandableListViewList.setAdapter(ClassChildTeacherListAdapter);
                expandableListViewList.setOnChildClickListener(new OnChildClickListenerOnStatusAskSubjects());
            } else {
                throw new NullPointerException("getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO IS NULL");
            }
        }

        if (StringUtils.equals(getArguments().getString(STATUSOFCONSULTINGHOURS), ASKINSTRUCTOR)) {
            AskSubjectsPojo askSubjectsPojo = getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO);
            int instructorPos = getArguments().getInt(INSTRUCTORPOS);
            int subjectPos = getArguments().getInt(SUBJECTPOS);
            final ExpandableListAdapter ClassChildTeacherListAdapter;
            if (askSubjectsPojo != null) {
                ClassChildTeacherListAdapter = new InstructorOfficeHourExpandableListAdapter(getContext(),
                        Collections.singletonList(
                                askSubjectsPojo.getSubjects().get(subjectPos).getInstructors().get(instructorPos)));

                expandableListViewList.setAdapter(ClassChildTeacherListAdapter);
                expandableListViewList.setOnChildClickListener(new OnChildClickListenerOnStatusAskInstructorCH());
                expandableListViewList.expandGroup(0);
            } else {
                throw new NullPointerException("getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO) IS NULL");
            }
        }
        return view;
    }

    /**
     * Implements ExpandableListView.OnChildClickListener this will happen when the user clicks any Subject's teacher.
     */
    private class OnChildClickListenerOnStatusAskSubjects implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Bundle bundle = new Bundle();
            bundle.putString(STATUSOFCONSULTINGHOURS, ASKINSTRUCTOR);
            Subject parentListAdapterGroup = (Subject) parent.getExpandableListAdapter().getGroup(groupPosition);
            bundle.putInt(INSTRUCTORPOS, childPosition);
            bundle.putInt(SUBJECTPOS, groupPosition);

            MessageTypeInstructorIdUserId messageTypeInstructorIdUserId
                    = new MessageTypeInstructorIdUserId(ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE, parentListAdapterGroup.getInstructors().get(childPosition).getInstructorId().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            String request = null;
            try {
                request = objectMapper.writeValueAsString(messageTypeInstructorIdUserId);
       //         Connection.getInstance().createLoadingDialog(request, getFragmentManager(), bundle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    /**
     * Implements ExpandableListView.OnChildClickListener this will happen when the user clicks any Teacher's office hour.
     */
    private class OnChildClickListenerOnStatusAskInstructorCH implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Instructor parentListAdapterGroup = (Instructor) parent.getExpandableListAdapter().getGroup(groupPosition);
            final OfficeHour officeHour = parentListAdapterGroup.getConsultingHoursList().get(childPosition);

            //We add the information insted of a new one, so the reserver can use onBack without a problem
            //Bundle bundle = new Bundle();
            Bundle bundle = getArguments();
            bundle.putParcelable(EXTRA_FROM_UNTIL_DATES, officeHour.getFromToDates());
            bundle.putLong(SELECTED_OFFICE_HOUR_ID, officeHour.getConsultingHourId());
            Fragment fragment = new OfficeHourReserveFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
            fragmentTransaction.commitAllowingStateLoss();
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (StringUtils.equals(getArguments().getString(STATUSOFCONSULTINGHOURS), ASKSUBJECTS)) {
            Fragment fragment = new OfficeHourFragment();
            fragment.setArguments(getArguments());
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
