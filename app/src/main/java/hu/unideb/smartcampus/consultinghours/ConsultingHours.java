package hu.unideb.smartcampus.consultinghours;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hu.unideb.smartcampus.LoginActivity;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.ConsultingDatesExpandableListAdapter;
import hu.unideb.smartcampus.adapter.ConsultingHoursExpandableListAdapter;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Class;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.ConsultingHoursObject;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.FromUntilDates;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Teacher;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class ConsultingHours extends AppCompatActivity {
    boolean isItOnChildItem;

    public ConsultingHours() {
        super();
        isItOnChildItem = false;
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        if (isItOnChildItem) {
            Intent intent = new Intent(getApplicationContext(), ConsultingHours.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_hours);

        //Mocked data , we will need some functions here

        //Refactor inc for dates
        final ExpandableListView ClassList = (ExpandableListView) findViewById(R.id.consulting_hours_ExpandableListView);
        List<Teacher> teacherList = new ArrayList<>();
        List<Teacher> teacherList1 = new ArrayList<>();
        List<Teacher> teacherList2 = new ArrayList<>();
        Date date = new Date();
        List<Date> dateList = Arrays.asList(date, date);
        Date _1500 = new Date(1488294000 * 1000);
        Date _1600 = new Date(1488297600 * 1000);
        FromUntilDates fromUntilDates = new FromUntilDates(_1500, _1600);
        ConsultingHoursObject consultingHours = new ConsultingHoursObject(Arrays.asList(fromUntilDates));

        teacherList.add(new Teacher("Várterész Magdolna", consultingHours));
        teacherList.add(new Teacher("Kádek Tamás", consultingHours));
        teacherList.add(new Teacher("Dr. Horváth Géza", consultingHours));
        Class mestint = new Class("A mesterséges intelligencia alapjai", teacherList);
        teacherList1.add(new Teacher("Dr. Gál Zoltán", consultingHours));
        teacherList1.add(new Teacher("Dr. Szilágyi Szabolcs", consultingHours));
        teacherList1.add(new Teacher("Vas Ádám", consultingHours));
        Class halo = new Class("Hálózati architektúrák és protokollok", teacherList1);
        teacherList2.add(new Teacher("Dr. Jeszenszky Péter", consultingHours));
        Class internet = new Class("Az internet eszközei és filtikai", teacherList2);
        List<Class> classList = new ArrayList<>();
        classList.add(mestint);
        classList.add(halo);
        classList.add(internet);
        final ExpandableListAdapter ClassChildTeacherListAdapter = new ConsultingHoursExpandableListAdapter(getApplicationContext(), classList);
        ClassList.setAdapter(ClassChildTeacherListAdapter);

        ClassList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupAt, int childPosition, long id) {
                final Object parentListAdapterGroup = parent.getExpandableListAdapter().getGroup(groupAt);
                if (parentListAdapterGroup instanceof Class) {
                    Class parentClass = (Class) parent.getExpandableListAdapter().getGroup(groupAt);

                    ExpandableListAdapter teacherChildConsultingHoursListAdapter =
                            new ConsultingDatesExpandableListAdapter(getApplicationContext(), Arrays.asList(parentClass.getTeacherList().get(childPosition)));
                    ClassList.setAdapter(teacherChildConsultingHoursListAdapter);
                    isItOnChildItem = true;
                    return true;
                } else {
                    Teacher parentClass = (Teacher) parent.getExpandableListAdapter().getGroup(groupAt);

                    Intent intent = new Intent(getApplicationContext(), ReserveConsultation.class);
                    intent.putExtra("FROMUNTILDATES", parentClass.getConsultingDates().getDateList().get(childPosition).toString());
                    startActivity(intent);
                    return true;
                }
            }
        });


    }

}
