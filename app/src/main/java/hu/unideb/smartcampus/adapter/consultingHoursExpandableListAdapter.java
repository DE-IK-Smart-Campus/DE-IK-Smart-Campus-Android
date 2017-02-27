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
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Class;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class ConsultingHoursExpandableListAdapter extends BaseExpandableListAdapter {

    List<Class> classList;
    Context context;

    public ConsultingHoursExpandableListAdapter(Context context, List<Class> classList) {
        this.classList = classList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return classList.size();
    }

    @Override
    public int getChildrenCount(int classAt) {
        return classList.get(classAt).getTeacherList().size();
    }

    @Override
    public Object getGroup(int at) {
        return classList.get(at);
    }

    @Override
    public Object getChild(int classAt, int teacherAt) {
        return classList.get(classAt).getTeacherList().get(teacherAt);
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
        String headerTitle = classList.get(classAt).getName();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_class, null);
        }
        TextView classTextView = (TextView) view
                .findViewById(R.id.classListId);
        classTextView.setTypeface(null, Typeface.BOLD);
        classTextView.setText(headerTitle);

        return view;

    }

    @Override
    public View getChildView(int classAt, int teacherAt, boolean isExpanded, View view, ViewGroup viewGroup) {
        final String teacherName = classList.get(classAt).getTeacherList().get(teacherAt).getName();

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_teacher, null);
        }

        TextView teacherTextView = (TextView) view
                .findViewById(R.id.teacherListItemId);

        teacherTextView.setText(teacherName);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
