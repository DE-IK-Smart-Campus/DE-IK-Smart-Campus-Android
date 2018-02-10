package hu.unideb.smartcampus.old.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * This class for the JSON parse  ( with Jackson),
 * when messageType at Office hours is "AskInstructorConsultingHoursProcessMessageResponse"
 * <p>
 * <p>
 * Created by Headswitcher on 2017. 03. 10..
 */

public class AskInstructorOfficeHourPojo extends BasePojo implements Serializable {

    private List<Instructor> instructorList;

    public AskInstructorOfficeHourPojo(List<Instructor> instructorList) {
        this.instructorList = instructorList;
    }

    public AskInstructorOfficeHourPojo() {
    }

    public List<Instructor> getInstructorList() {
        return instructorList;
    }

    public void setInstructorList(List<Instructor> instructorList) {
        this.instructorList = instructorList;
    }
}
