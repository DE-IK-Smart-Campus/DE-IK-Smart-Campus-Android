package hu.unideb.smartcampus.main.activity.officehours.fragment;

import android.content.Intent;
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
import org.jivesoftware.smack.SmackException;

import java.util.Collections;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.officehours.activity.ReserveOfficeHourActivity;
import hu.unideb.smartcampus.main.activity.officehours.adapter.InstructorOfficeHourExpandableListAdapter;
import hu.unideb.smartcampus.main.activity.officehours.adapter.SubjectInstructorExpandableListAdapter;
import hu.unideb.smartcampus.main.activity.officehours.json.MessageTypeInstructorIdUserId;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsProcessMessagePojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Instructor;
import hu.unideb.smartcampus.main.activity.officehours.pojo.OfficeHour;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Subject;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTOR;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.ASKSUBJECTS;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_FROM_UNTIL_DATES;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.INSTRUCTORPOS;
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
        View view = inflater.inflate(R.layout.fragment_consultinghours, container, false);

        ExpandableListView ClassList = (ExpandableListView) view.findViewById(R.id.consulting_hours_ExpandableListView);

        if (StringUtils.equals(getArguments().getString(STATUSOFCONSULTINGHOURS), ASKSUBJECTS)) {
            AskSubjectsProcessMessagePojo askSubjectsProcessMessagePojo = getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO);
            final ExpandableListAdapter ClassChildTeacherListAdapter;
            if (askSubjectsProcessMessagePojo != null) {
                ClassChildTeacherListAdapter = new SubjectInstructorExpandableListAdapter(getContext(), askSubjectsProcessMessagePojo.getSubjects());
                ClassList.setAdapter(ClassChildTeacherListAdapter);
                ClassList.setOnChildClickListener(new OnChildClickListenerOnStatusAskSubjects());
            } else {
                throw new NullPointerException("getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO IS NULL");
            }
        }

        if (StringUtils.equals(getArguments().getString(STATUSOFCONSULTINGHOURS), ASKINSTRUCTOR)) {
            AskSubjectsProcessMessagePojo askSubjectsProcessMessagePojo = getArguments().getParcelable(EXTRA_ASK_SUBJECTS_PROCESS_MESSAGE_POJO);
            int instructorPos = getArguments().getInt(INSTRUCTORPOS);
            int subjectPos = getArguments().getInt(SUBJECTPOS);
            final ExpandableListAdapter ClassChildTeacherListAdapter;
            if (askSubjectsProcessMessagePojo != null) {
                ClassChildTeacherListAdapter = new InstructorOfficeHourExpandableListAdapter(getContext(),
                        Collections.singletonList(
                                askSubjectsProcessMessagePojo.getSubjects().get(subjectPos).getInstructors().get(instructorPos)));

                ClassList.setAdapter(ClassChildTeacherListAdapter);
                ClassList.setOnChildClickListener(new OnChildClickListenerOnStatusAskInstructorCH());
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
            //bundle.putParcelable("INSTRUCTOR", parentListAdapterGroup.getInstructors().get(childPosition));
            bundle.putInt(INSTRUCTORPOS, childPosition);
            bundle.putInt(SUBJECTPOS, groupPosition);

            MessageTypeInstructorIdUserId messageTypeInstructorIdUserId
                    = new MessageTypeInstructorIdUserId(ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE, parentListAdapterGroup.getInstructors().get(childPosition).getInstructorId().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String request = objectMapper.writeValueAsString(messageTypeInstructorIdUserId);
                Connection.getInstance().createLoadingDialog(request, getFragmentManager(), bundle);
            } catch (JsonProcessingException | SmackException.NotConnectedException e) {
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

            Intent intent = new Intent(getContext(), ReserveOfficeHourActivity.class);
            intent.putExtra(EXTRA_FROM_UNTIL_DATES, officeHour.getFromToDates());
            intent.putExtra(SELECTED_OFFICE_HOUR_ID, officeHour.getConsultingHourId());
            final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(OfficeHourFragment.this);
            fragmentTransaction.commit();

            startActivity(intent);
            return true;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
