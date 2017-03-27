package hu.unideb.smartcampus.main.activity.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * This class for the JSON parse  ( with Jackson),
 * when messageType at Office hours is "AskInstructorConsultingHoursProcessMessageResponse"
 * <p>
 * <p>
 * Created by Headswitcher on 2017. 03. 10..
 */

public class AskInstructorOfficeHourPojo implements Parcelable {

    private List<OfficeHour> officeHours;


    public AskInstructorOfficeHourPojo() {
    }

    public AskInstructorOfficeHourPojo(List<OfficeHour> officeHours) {
        this.officeHours = officeHours;
    }

    protected AskInstructorOfficeHourPojo(Parcel in) {
        officeHours = in.createTypedArrayList(OfficeHour.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(officeHours);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AskInstructorOfficeHourPojo> CREATOR = new Creator<AskInstructorOfficeHourPojo>() {
        @Override
        public AskInstructorOfficeHourPojo createFromParcel(Parcel in) {
            return new AskInstructorOfficeHourPojo(in);
        }

        @Override
        public AskInstructorOfficeHourPojo[] newArray(int size) {
            return new AskInstructorOfficeHourPojo[size];
        }
    };

    public List<OfficeHour> getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(List<OfficeHour> officeHours) {
        this.officeHours = officeHours;
    }
}
