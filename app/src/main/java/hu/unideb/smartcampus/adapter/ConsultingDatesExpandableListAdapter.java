package hu.unideb.smartcampus.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Teacher;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class ConsultingDatesExpandableListAdapter extends BaseExpandableListAdapter {

    List<Teacher> teacherList;
    Context context;

    public ConsultingDatesExpandableListAdapter(Context context, List<Teacher> teacherList) {
        this.teacherList = teacherList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return teacherList.size();
    }

    @Override
    public int getChildrenCount(int classAt) {
        return teacherList.get(classAt).getConsultingDates().size();
    }

    @Override
    public Object getGroup(int at) {
        return teacherList.get(at);
    }

    @Override
    public Object getChild(int teacherAt, int consultingHoursAt) {
        return teacherList.get(teacherAt).getConsultingDates().get(consultingHoursAt);
    }

    @Override
    public long getGroupId(int teacherAt) {
        return teacherAt;
    }

    @Override
    public long getChildId(int teacherAt, int consultingHoursAt) {
        return consultingHoursAt;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int teacherAt, boolean isExpanded, View view, ViewGroup parent) {
        String headerTitle = teacherList.get(teacherAt).getName();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_teacher, null);
        }
        TextView teacherNameTextView = (TextView) view
                .findViewById(R.id.teacherListItemId);
        teacherNameTextView.setTypeface(null, Typeface.BOLD);
        teacherNameTextView.setText(headerTitle);

        return view;

    }

    @Override
    public View getChildView(int teacherAt, int consultingHoursAt, boolean isExpanded, View view, ViewGroup viewGroup) {
        final String dateDisplayName = teacherList.get(teacherAt).getConsultingDates().get(consultingHoursAt).toString();
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
