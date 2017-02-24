package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class Class {

    private String name;
    private List<Teacher> teacherList;

    public Class(String name, List<Teacher> teacherList) {
        this.name = name;
        this.teacherList = teacherList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
