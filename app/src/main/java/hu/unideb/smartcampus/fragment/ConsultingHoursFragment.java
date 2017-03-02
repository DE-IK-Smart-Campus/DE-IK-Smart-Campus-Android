package hu.unideb.smartcampus.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.ConsultingDatesExpandableListAdapter;
import hu.unideb.smartcampus.adapter.ConsultingHoursExpandableListAdapter;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Class;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.ConsultingHoursObject;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.FromUntilDates;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Teacher;
import hu.unideb.smartcampus.consultinghours.ReserveConsultation;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;


public class ConsultingHoursFragment extends Fragment implements OnBackPressedListener {
    boolean isItOnChildItem;

    public ConsultingHoursFragment() {
        isItOnChildItem = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consultinghours, container, false);

        //Mocked data , we will need some functions here

        //Ref*actor inc for dates , ugly as ...
        final ExpandableListView ClassList = (ExpandableListView) view.findViewById(R.id.consulting_hours_ExpandableListView);
        List<Teacher> teacherList = new ArrayList<>();
        List<Teacher> teacherList1 = new ArrayList<>();
        List<Teacher> teacherList2 = new ArrayList<>();
        Date date = new Date();
        GregorianCalendar gcalendar = new GregorianCalendar();
        gcalendar.set(2017, 02, 28, 15, 00);
        GregorianCalendar gcalendar2 = new GregorianCalendar();
        gcalendar2.set(2017, 02, 28, 16, 00);
        GregorianCalendar gcalendar3 = new GregorianCalendar();
        gcalendar.set(2017, 02, 29, 17, 00);
        GregorianCalendar gcalendar4 = new GregorianCalendar();
        gcalendar2.set(2017, 02, 29, 18, 00);
        FromUntilDates fromUntilDates = new FromUntilDates(gcalendar.getTime(), gcalendar2.getTime());
        FromUntilDates fromUntilDates2 = new FromUntilDates(gcalendar.getTime(), gcalendar2.getTime());
        final ConsultingHoursObject consultingHours = new ConsultingHoursObject(Arrays.asList(fromUntilDates));

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
        final ExpandableListAdapter ClassChildTeacherListAdapter = new ConsultingHoursExpandableListAdapter(getContext(), classList);
        ClassList.setAdapter(ClassChildTeacherListAdapter);

        ClassList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupAt, int childPosition, long id) {
                final Object parentListAdapterGroup = parent.getExpandableListAdapter().getGroup(groupAt);
                if (parentListAdapterGroup instanceof Class) {
                    Class parentClass = (Class) parent.getExpandableListAdapter().getGroup(groupAt);

                    ExpandableListAdapter teacherChildConsultingHoursListAdapter =
                            new ConsultingDatesExpandableListAdapter(getContext(), Arrays.asList(parentClass.getTeacherList().get(childPosition)));
                    ClassList.setAdapter(teacherChildConsultingHoursListAdapter);
                    ClassList.expandGroup(0);
                    isItOnChildItem = true;
                    return true;
                } else {
                    Teacher parentClass = (Teacher) parent.getExpandableListAdapter().getGroup(groupAt);
                    Intent intent = new Intent(getContext(), ReserveConsultation.class);

                    //Will change after mock / 1.8
                    FromUntilDates dates = parentClass.getConsultingDates().getDateList().get(childPosition);
                    FromUntilDates fromUntilDates = new FromUntilDates(dates.getFrom(), dates.getUntil());
                    intent.putExtra(getString(R.string.EXTRA_FROM_UNTIL_DATES), fromUntilDates);
                    startActivity(intent);
                    return true;
                }
            }
        });
        return view;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = new ConsultingHoursFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, "teszt");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
