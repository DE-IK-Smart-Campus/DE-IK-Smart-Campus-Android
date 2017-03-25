package hu.unideb.smartcampus.main.activity.classattendance.pojo;

import java.util.List;

public class Subject {

    private String name;
    private List<SubjectDate> subjectDates;

    public Subject() {
    }

    public Subject(String name, List<SubjectDate> subjectDates) {
        this.name = name;
        this.subjectDates = subjectDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectDate> getSubjectDates() {
        return subjectDates;
    }

    public void setSubjectDates(List<SubjectDate> subjectDates) {
        this.subjectDates = subjectDates;
    }
}
