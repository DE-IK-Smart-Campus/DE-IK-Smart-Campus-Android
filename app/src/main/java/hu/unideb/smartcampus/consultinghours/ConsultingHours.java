package hu.unideb.smartcampus.consultinghours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.ConsultingHoursExpandableListAdapter;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Class;
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
        ExpandableListView ClassList = (ExpandableListView) findViewById(R.id.consulting_hours_ExpandableListView);
        List<Teacher> teacherList = new ArrayList<>();
        List<Teacher> teacherList1 = new ArrayList<>();
        List<Teacher> teacherList2 = new ArrayList<>();
        teacherList.add(new Teacher("Várterész Magdolna", null));
        teacherList.add(new Teacher("Kádek Tamás", null));
        teacherList.add(new Teacher("Dr. Horváth Géza", null));
        Class mestint = new Class("A mesterséges intelligencia alapjai", teacherList);
        teacherList1.add(new Teacher("Dr. Gál Zoltán", null));
        teacherList1.add(new Teacher("Dr. Szilágyi Szabolcs", null));
        teacherList1.add(new Teacher("Vas Ádám", null));
        Class halo = new Class("Hálózati architektúrák és protokollok", teacherList1);
        teacherList2.add(new Teacher("Dr. Jeszenszky Péter", null));
        Class internet = new Class("Az internet eszközei és szolgáltatásai", teacherList2);
        List<Class> classList = new ArrayList<>();
        classList.add(mestint);
        classList.add(halo);
        classList.add(internet);
        ExpandableListAdapter expandableListAdapter = new ConsultingHoursExpandableListAdapter(getApplicationContext(), classList);
        ClassList.setAdapter(expandableListAdapter);
    }
}
