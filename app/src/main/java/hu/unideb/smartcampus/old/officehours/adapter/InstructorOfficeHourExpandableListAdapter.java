package hu.unideb.smartcampus.old.officehours.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.officehours.pojo.FromToDatesInLong;
import hu.unideb.smartcampus.old.officehours.pojo.Instructor;

public class InstructorOfficeHourExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Instructor> instructor;
    private Context context;

    public InstructorOfficeHourExpandableListAdapter(Context context, List<Instructor> instructor) {
        this.instructor = instructor;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return instructor.size();
    }

    @Override
    public int getChildrenCount(int classAt) {
        return instructor.get(classAt).getOfficeHourList().size();
    }

    @Override
    public Object getGroup(int at) {
        return instructor.get(at);
    }

    @Override
    public Object getChild(int instructorAt, int consultingHoursAt) {
        return instructor.get(instructorAt).getOfficeHourList().get(consultingHoursAt);
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
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = layoutInflater.inflate(R.layout.list_instructor, null);
        }
//        TextView teacherNameTextView = (TextView) view
//                .findViewById(R.id.teacherListItemId);
//        teacherNameTextView.setTypeface(null, Typeface.BOLD);
//        teacherNameTextView.setText(headerTitle);

        return view;

    }

    @Override
    public View getChildView(int instructorAt, int consultingHoursAt, boolean isExpanded, View view, ViewGroup viewGroup) {

        final FromToDatesInLong dates = instructor.get(instructorAt).getOfficeHourList().get(consultingHoursAt).getFromToDates();
        Date d = new Date(dates.getFrom());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String sDate = sdf.format(d);
        StringBuilder builder = new StringBuilder();
        builder.append(sDate).
                append(" ").
                append(DateFormat.getTimeInstance(DateFormat.SHORT).format(dates.getFrom() * 1000)).
                append("  -  ").
                append(DateFormat.getTimeInstance(DateFormat.SHORT).format(dates.getTo() * 1000));
        final String dateDisplayName = builder.toString();
        if (view == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this.context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = infalInflater.inflate(R.layout.list_consultinghours, null);
        }
//
//        TextView consultingHoursTextView = (TextView) view
//                .findViewById(R.id.consultingHoursListItemId);

//        consultingHoursTextView.setText(dateDisplayName);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
