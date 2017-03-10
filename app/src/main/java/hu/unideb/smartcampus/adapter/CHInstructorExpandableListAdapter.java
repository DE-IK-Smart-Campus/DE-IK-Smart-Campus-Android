package hu.unideb.smartcampus.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.xmpp.pojos.CHFromToDatesPojo;
import hu.unideb.smartcampus.xmpp.pojos.CHInstructorPojo;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class CHInstructorExpandableListAdapter extends BaseExpandableListAdapter {

    List<CHInstructorPojo> instructor;
    Context context;

    public CHInstructorExpandableListAdapter(Context context, List<CHInstructorPojo> instructor) {
        this.instructor = instructor;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return instructor.size();
    }

    @Override
    public int getChildrenCount(int classAt) {
        return instructor.get(classAt).getConsultingHoursList().size();
    }

    @Override
    public Object getGroup(int at) {
        return instructor.get(at);
    }

    @Override
    public Object getChild(int instructorAt, int consultingHoursAt) {
        return instructor.get(instructorAt).getConsultingHoursList().get(consultingHoursAt);
    }

    @Override
    public long getGroupId(int instructorAt) {
        return instructorAt;
    }

    @Override
    public long getChildId(int instructorAt, int consultingHoursAt) {
        return consultingHoursAt;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int teacherAt, boolean isExpanded, View view, ViewGroup parent) {
        String headerTitle = instructor.get(teacherAt).getName();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_instructor, null);
        }
        TextView teacherNameTextView = (TextView) view
                .findViewById(R.id.teacherListItemId);
        teacherNameTextView.setTypeface(null, Typeface.BOLD);
        teacherNameTextView.setText(headerTitle);

        return view;

    }

    @Override
    public View getChildView(int instructorAt, int consultingHoursAt, boolean isExpanded, View view, ViewGroup viewGroup) {

        //will change with 1.8 TODO
        final CHFromToDatesPojo dates = instructor.get(instructorAt).getConsultingHoursList().get(consultingHoursAt).getFromToDates();
        final String dateDisplayName = DateFormat.getTimeInstance(DateFormat.SHORT).format(dates.getFrom())
                + "  -  "
                + DateFormat.getTimeInstance(DateFormat.SHORT).format(dates.getTo());
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_consultinghours, null);
        }

        TextView consultingHoursTextView = (TextView) view
                .findViewById(R.id.consultingHoursListItemId);

        consultingHoursTextView.setText(dateDisplayName);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
