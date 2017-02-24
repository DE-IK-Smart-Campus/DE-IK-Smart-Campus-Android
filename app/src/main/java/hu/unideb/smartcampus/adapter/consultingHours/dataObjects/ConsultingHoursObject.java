package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import java.util.Date;
import java.util.List;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */
public class ConsultingHoursObject {

    List<Date> dateList;

    public ConsultingHoursObject(List<Date> dateList) {
        this.dateList = dateList;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }
}
