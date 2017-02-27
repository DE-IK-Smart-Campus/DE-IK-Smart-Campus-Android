package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import java.util.Date;
import java.util.List;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */
public class Teacher {

    //private Id;
    private String name;
    private List<Date> consultingDates;

    public Teacher(String name, List<Date> consultingDates) {
        this.name = name;
        this.consultingDates = consultingDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Date> getConsultingDates() {
        return consultingDates;
    }

    public void setConsultingDates(List<Date> consultingDates) {
        this.consultingDates = consultingDates;
    }
}
