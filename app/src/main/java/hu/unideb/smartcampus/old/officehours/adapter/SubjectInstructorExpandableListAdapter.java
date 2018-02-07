package hu.unideb.smartcampus.old.officehours.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.officehours.pojo.Subject;

public class SubjectInstructorExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Subject> subjectList;
    private Context context;

    public SubjectInstructorExpandableListAdapter(Context context, List<Subject> subjectList) {
        this.subjectList = subjectList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return subjectList.size();
    }

    @Override
    public int getChildrenCount(int classAt) {
        return subjectList.get(classAt).getInstructors().size();
    }

    @Override
    public Object getGroup(int at) {
        return subjectList.get(at);
    }

    @Override
    public Object getChild(int classAt, int teacherAt) {
        return subjectList.get(classAt).getInstructors().get(teacherAt);
    }

    @Override
    public long getGroupId(int classAt) {
        return classAt;
    }

    @Override
    public long getChildId(int classAt, int childAt) {
        return childAt;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int classAt, boolean isExpanded, View view, ViewGroup parent) {
//        String headerTitle = subjectList.get(classAt).getName();
//        if (view == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = layoutInflater.inflate(R.layout.list_class, null);
//        }
//        TextView classTextView = (TextView) view
//                .findViewById(R.id.classListId);
//        classTextView.setText(headerTitle);

        return view;

    }

    @Override
    public View getChildView(int classAt, int teacherAt, boolean isExpanded, View view, ViewGroup viewGroup) {
//        final String teacherName = subjectList.get(classAt).getInstructors().get(teacherAt).getName();
//
//        if (view == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this.context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = infalInflater.inflate(R.layout.list_instructor, null);
//        }
//
//        TextView teacherTextView = (TextView) view
//                .findViewById(R.id.teacherListItemId);
//
//        teacherTextView.setText(teacherName);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
