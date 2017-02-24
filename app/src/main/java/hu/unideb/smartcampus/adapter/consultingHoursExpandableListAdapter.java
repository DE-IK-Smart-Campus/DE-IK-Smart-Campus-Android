package hu.unideb.smartcampus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

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
    public View getGroupView(int classAt, boolean b, View view, ViewGroup viewGroup) {
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
}
