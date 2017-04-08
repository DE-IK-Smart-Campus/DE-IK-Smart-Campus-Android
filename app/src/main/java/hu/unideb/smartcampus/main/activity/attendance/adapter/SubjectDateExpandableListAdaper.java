package hu.unideb.smartcampus.main.activity.attendance.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.attendance.pojo.Subject;

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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Long dateL = subjectsList.get(groupPosition).getSubjectDates().get(childPosition).getDate();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_subject_date, null);
        }

        CheckBox dateList = (CheckBox) convertView.findViewById(R.id.subjectDate);
        dateList.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateL));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
