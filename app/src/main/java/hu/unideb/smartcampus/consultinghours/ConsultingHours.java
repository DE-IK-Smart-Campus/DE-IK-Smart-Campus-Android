package hu.unideb.smartcampus.consultinghours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.ConsultingDatesExpandableListAdapter;
import hu.unideb.smartcampus.adapter.ConsultingHoursExpandableListAdapter;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Class;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.ConsultingHoursObject;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Teacher;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class ConsultingHours extends AppCompatActivity {

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
        List<Date> dateList = Arrays.asList(date);
        ConsultingHoursObject consultingHours = new ConsultingHoursObject(dateList);

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
            public boolean onChildClick(ExpandableListView parent, View v, int classAt, int childPosition, long id) {
                Log.v("Itt","ez");

               Class parentClass = (Class) parent.getExpandableListAdapter().getGroup(classAt);
                //refactor
                ExpandableListAdapter teacherChildConsultingHoursListAdapter =
                        new ConsultingDatesExpandableListAdapter(getApplicationContext(),parentClass.getTeacherList());
                ClassList.setAdapter(teacherChildConsultingHoursListAdapter);
                return true;
            }
        });

    }
}
