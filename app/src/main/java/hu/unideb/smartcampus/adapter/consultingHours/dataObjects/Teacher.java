package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */
public class Teacher {

    //private Id;
    private String name;
    private ConsultingHoursObject consultingHours;

    public Teacher(String name, ConsultingHoursObject consultingHours) {
        this.name = name;
        this.consultingHours = consultingHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConsultingHoursObject getConsultingHours() {
        return consultingHours;
    }

    public void setConsultingHours(ConsultingHoursObject consultingHours) {
        this.consultingHours = consultingHours;
    }
}
