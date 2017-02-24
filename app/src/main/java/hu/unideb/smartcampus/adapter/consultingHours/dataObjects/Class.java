package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class Class {

    private List<Teacher> teacherList;

    public Class() {
        teacherList = new ArrayList<>();
    }

    public Class(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

}
