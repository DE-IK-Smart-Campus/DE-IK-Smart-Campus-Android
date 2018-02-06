package hu.unideb.smartcampus.old.attendance.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class SubjectDateExpandableListAdaper extends BaseExpandableListAdapter {
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

//    private List<Subject> subjectsList;
//    private Context context;
//
//    public SubjectDateExpandableListAdaper(List<Subject> subjectsList, Context context) {
//        this.subjectsList = subjectsList;
//        this.context = context;
//    }
//
//    @Override
//    public int getGroupCount() {
//        return subjectsList.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return subjectsList.get(groupPosition).getSubjectDates().size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return subjectsList.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return subjectsList.get(groupPosition).getSubjectDates().get(childPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        String headerTitle = subjectsList.get(groupPosition).getName();
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.list_subject, null);
//        }
//        TextView subjectNameTextView = (TextView) convertView.findViewById(R.id.subjectName);
//        subjectNameTextView.setTypeface(null, Typeface.BOLD);
//        subjectNameTextView.setText(headerTitle);
//        return convertView;
//    }
//
//    @Override
//    public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
//        final Long dateL = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getDate() * 1000;
//        final Long t = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getDateId();
//        final boolean t2 = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getYesOrNo();
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.list_subject_date, null);
//        }
//        final CheckBox dateList = (CheckBox) convertView.findViewById(R.id.subjectDate);
////        dateL.shortValue();
//        dateList.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateL));
//
//
//        dateList.setChecked(t2);
//
//        dateList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (dateList.isChecked()) {
//                    try {
//                        ChangeAttendanceIqRequest changeAttendanceIqRequest = new ChangeAttendanceIqRequest();
//                        changeAttendanceIqRequest.setStudent(xmppboshConnection.getUser().getLocalpartOrThrow().toString());
//                        changeAttendanceIqRequest.setAppointmentId(t);
//                        changeAttendanceIqRequest.setPresent(true);
//                        changeAttendanceIqRequest.setType(IQ.Type.set);
//                        changeAttendanceIqRequest.setTo(JidCreate.from(ADMINJID));
//                        changeAttendanceIqRequest.setFrom(xmppboshConnection.getUser());
//                        final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(changeAttendanceIqRequest);
//                        stanzaCollectorAndSend.nextResultOrThrow(5000);
//                        dateList.setChecked(changeAttendanceIqRequest.getPresent());
//                    } catch (SmackException.NoResponseException | SmackException.NotConnectedException | InterruptedException | XMPPException.XMPPErrorException | XmppStringprepException e) {
//                        e.printStackTrace();
//                    }
//                } else if (!dateList.isChecked()) {
//                    try {
//                        ChangeAttendanceIqRequest changeAttendanceIqRequest = new ChangeAttendanceIqRequest();
//                        changeAttendanceIqRequest.setStudent(xmppboshConnection.getUser().getLocalpartOrThrow().toString());
//                        changeAttendanceIqRequest.setAppointmentId(t);
//                        changeAttendanceIqRequest.setPresent(false);
//                        changeAttendanceIqRequest.setType(IQ.Type.set);
//                        changeAttendanceIqRequest.setTo(JidCreate.from(ADMINJID));
//                        changeAttendanceIqRequest.setFrom(xmppboshConnection.getUser());
//                        final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(changeAttendanceIqRequest);
//                        stanzaCollectorAndSend.nextResultOrThrow(5000);
//                    } catch (SmackException.NoResponseException | SmackException.NotConnectedException | InterruptedException | XMPPException.XMPPErrorException | XmppStringprepException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        return convertView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }
}
