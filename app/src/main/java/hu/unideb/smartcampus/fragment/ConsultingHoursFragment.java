package hu.unideb.smartcampus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;

import java.util.Arrays;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.CHExpandableListAdapter;
import hu.unideb.smartcampus.adapter.CHInstructorExpandableListAdapter;
import hu.unideb.smartcampus.consultinghours.ReserveConsultation;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.xmpp.Connection;
import hu.unideb.smartcampus.xmpp.pojos.CHConsultingHoursPojo;
import hu.unideb.smartcampus.xmpp.pojos.CHInstructorPojo;
import hu.unideb.smartcampus.xmpp.pojos.CHJsonPojo;
import hu.unideb.smartcampus.xmpp.pojos.CHSubjectPojo;
import hu.unideb.smartcampus.xmpp.pojos.MessageTypeInstructorIdUserId;

import static hu.unideb.smartcampus.xmpp.listeners.ConsultingHoursHandler.ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE;


public class ConsultingHoursFragment extends Fragment implements OnBackPressedListener {

    public class OnChildClickListenerOnStatusAskSubjects implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            Bundle bundle = new Bundle();
            bundle.putString("STATUSOFCONSULTINGHOURS", "ASKINSTRUCTOR");
            CHSubjectPojo parentListAdapterGroup = (CHSubjectPojo) parent.getExpandableListAdapter().getGroup(groupPosition);
            bundle.putParcelable("INSTRUCTOR", parentListAdapterGroup.getInstructors().get(childPosition));
            bundle.putInt("INSTRUCTORPOS", childPosition);
            bundle.putInt("SUBJECTPOS", groupPosition);

            MessageTypeInstructorIdUserId messageTypeInstructorIdUserId
                    = new MessageTypeInstructorIdUserId(ASKINSTRUCTORCONSULTINGHOURSPROCESSMESSAGE, parentListAdapterGroup.getInstructors().get(childPosition).getInstructorId().toString(), Connection.getInstance().getUserJID());
            ObjectMapper objectMapper = new ObjectMapper();
            String request = null;
            try {
                request = objectMapper.writeValueAsString(messageTypeInstructorIdUserId);
                Connection.getInstance().createLoadingDialog(request, getFragmentManager(), bundle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public class OnChildClickListenerOnStatusAskInstructorCH implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            CHInstructorPojo parentListAdapterGroup = (CHInstructorPojo) parent.getExpandableListAdapter().getGroup(groupPosition);
            Intent intent = new Intent(getContext(), ReserveConsultation.class);
            final CHConsultingHoursPojo chConsultingHoursPojo = parentListAdapterGroup.getConsultingHoursList().get(childPosition);
            intent.putExtra(getString(R.string.extra_from_until_dates), chConsultingHoursPojo.getFromToDates());
            intent.putExtra("CONSULTINGHOURID", chConsultingHoursPojo.getConsultingHourId());
            startActivity(intent);
            return true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultinghours, container, false);
        ExpandableListView ClassList = (ExpandableListView) view.findViewById(R.id.consulting_hours_ExpandableListView);

        if (getArguments().getString("STATUSOFCONSULTINGHOURS").equalsIgnoreCase("ASKSUBJECTS")) {
            CHJsonPojo chClassAndInstructorPojo = getArguments().getParcelable("EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO");
            final ExpandableListAdapter ClassChildTeacherListAdapter = new CHExpandableListAdapter(getContext(), chClassAndInstructorPojo.getSubjects());
            ClassList.setAdapter(ClassChildTeacherListAdapter);
            ClassList.setOnChildClickListener(new OnChildClickListenerOnStatusAskSubjects());
        }

        if (getArguments().getString("STATUSOFCONSULTINGHOURS").equalsIgnoreCase("ASKINSTRUCTOR")) {
            CHJsonPojo chClassAndInstructorPojo = getArguments().getParcelable("EXTRA_CH_SUBJECT_AND_INSTRUCTOR_POJO");
            int instructorPos = getArguments().getInt("INSTRUCTORPOS");
            int subjectPos = getArguments().getInt("SUBJECTPOS");
            final ExpandableListAdapter ClassChildTeacherListAdapter = new CHInstructorExpandableListAdapter(getContext(),
                    Arrays.asList(
                            chClassAndInstructorPojo.getSubjects().get(subjectPos).getInstructors().get(instructorPos)));
            ClassList.setAdapter(ClassChildTeacherListAdapter);
            ClassList.setOnChildClickListener(new OnChildClickListenerOnStatusAskInstructorCH());
        }

        return view;
    }

    @Override
    public void onBackPressed() {

    }
}
