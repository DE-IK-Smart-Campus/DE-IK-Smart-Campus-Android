package hu.unideb.smartcampus.main.activity.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Pojo class
 * <p>
 * The university will have instructors this the pojo class for it.
 * <p>
 * The Instructor will have an {@code Long instructorId , String name} and a list of {@code consultingHoursList}
 *
 * @see OfficeHour
 * @see Parcelable
 * <p>
 * Created by Headswitcher on 2017. 03. 08..
 */

public class Instructor implements Parcelable {
    private Long instructorId;
    private String name;
    private List<OfficeHour> consultingHoursList;

    public Instructor() {
    }

    public Instructor(Long instructorId, String name, List<OfficeHour> consultingHoursList) {
        this.instructorId = instructorId;
        this.name = name;
        this.consultingHoursList = consultingHoursList;
    }

    protected Instructor(Parcel in) {
        instructorId = in.readLong();
        name = in.readString();
        consultingHoursList = in.createTypedArrayList(OfficeHour.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(instructorId);
        dest.writeString(name);
        dest.writeTypedList(consultingHoursList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
        @Override
        public Instructor createFromParcel(Parcel in) {
            return new Instructor(in);
        }

        @Override
        public Instructor[] newArray(int size) {
            return new Instructor[size];
        }
    };

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OfficeHour> getConsultingHoursList() {
        return consultingHoursList;
    }

    public void setConsultingHoursList(List<OfficeHour> consultingHoursList) {
        this.consultingHoursList = consultingHoursList;
    }
}
