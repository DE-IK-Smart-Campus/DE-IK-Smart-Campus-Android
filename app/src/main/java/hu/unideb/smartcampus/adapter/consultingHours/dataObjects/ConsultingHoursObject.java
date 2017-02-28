package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 02. 27..
 */
public class ConsultingHoursObject {

    private List<FromUntilDates> dateList;

    public ConsultingHoursObject(List<FromUntilDates> dateList) {
        this.dateList = dateList;
    }

    public List<FromUntilDates> getDateList() {
        return dateList;
    }

    public void setDateList(List<FromUntilDates> dateList) {
        this.dateList = dateList;
    }
}