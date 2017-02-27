package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */
public class Teacher {

    //private Id;
    private String name;
    private ConsultingHoursObject consultingDates;

    public Teacher(String name, ConsultingHoursObject consultingDates) {
        this.name = name;
        this.consultingDates = consultingDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConsultingHoursObject getConsultingDates() {
        return consultingDates;
    }

    public void setConsultingDates(ConsultingHoursObject consultingDates) {
        this.consultingDates = consultingDates;
    }
}
