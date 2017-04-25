package hu.unideb.smartcampus.main.activity.attendance.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.text.DateFormat;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.attendance.pojo.Subject;
import hu.unideb.smartcampus.shared.iq.request.ChangeAttendanceIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class SubjectDateExpandableListAdaper extends BaseExpandableListAdapter {

    private List<Subject> subjectsList;
    private Context context;

    public SubjectDateExpandableListAdaper(List<Subject> subjectsList, Context context) {
        this.subjectsList = subjectsList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return subjectsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subjectsList.get(groupPosition).getSubjectDates().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return subjectsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subjectsList.get(groupPosition).getSubjectDates().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = subjectsList.get(groupPosition).getName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_subject, null);
        }
        TextView subjectNameTextView = (TextView) convertView.findViewById(R.id.subjectName);
        subjectNameTextView.setTypeface(null, Typeface.BOLD);
        subjectNameTextView.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
        final Long dateL = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getDate() * 1000;
        final Long t = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getDateId();
        final boolean t2 = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getYesOrNo();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_subject_date, null);
        }
        final CheckBox dateList = (CheckBox) convertView.findViewById(R.id.subjectDate);
//        dateL.shortValue();
        dateList.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateL));


        dateList.setChecked(t2);

        dateList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (dateList.isChecked()) {
                    try {
                        ChangeAttendanceIqRequest changeAttendanceIqRequest = new ChangeAttendanceIqRequest();
                        changeAttendanceIqRequest.setStudent(xmppboshConnection.getUser().getLocalpartOrThrow().toString());
                        changeAttendanceIqRequest.setAppointmentId(t);
                        changeAttendanceIqRequest.setPresent(true);
                        changeAttendanceIqRequest.setType(IQ.Type.set);
                        changeAttendanceIqRequest.setTo(JidCreate.from(ADMINJID));
                        changeAttendanceIqRequest.setFrom(xmppboshConnection.getUser());
                        final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(changeAttendanceIqRequest);
                        stanzaCollectorAndSend.nextResultOrThrow(5000);
                        dateList.setChecked(changeAttendanceIqRequest.getPresent());
                    } catch (SmackException.NoResponseException | SmackException.NotConnectedException | InterruptedException | XMPPException.XMPPErrorException | XmppStringprepException e) {
                        e.printStackTrace();
                    }
                } else if (!dateList.isChecked()) {
                    try {
                        ChangeAttendanceIqRequest changeAttendanceIqRequest = new ChangeAttendanceIqRequest();
                        changeAttendanceIqRequest.setStudent(xmppboshConnection.getUser().getLocalpartOrThrow().toString());
                        changeAttendanceIqRequest.setAppointmentId(t);
                        changeAttendanceIqRequest.setPresent(false);
                        changeAttendanceIqRequest.setType(IQ.Type.set);
                        changeAttendanceIqRequest.setTo(JidCreate.from(ADMINJID));
                        changeAttendanceIqRequest.setFrom(xmppboshConnection.getUser());
                        final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(changeAttendanceIqRequest);
                        stanzaCollectorAndSend.nextResultOrThrow(5000);
                    } catch (SmackException.NoResponseException | SmackException.NotConnectedException | InterruptedException | XMPPException.XMPPErrorException | XmppStringprepException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
