package hu.unideb.smartcampus.old.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Pojo class
 * <p>
 * The university will have instructors this the pojo class for it.
 * <p>
 * The Instructor will have an {@code Long instructorId , String name} and a list of {@code officeHourList}
 *
 * @see OfficeHour
 * @see Parcelable
 * <p>
 * Created by Headswitcher on 2017. 03. 08..
 */

public class Instructor extends BasePojo implements Parcelable {
    private Long instructorId;
    private String name;
    private List<OfficeHour> officeHourList;

    public Instructor() {
    }

    public Instructor(Long instructorId, String name, List<OfficeHour> officeHourList) {
        this.instructorId = instructorId;
        this.name = name;
        this.officeHourList = officeHourList;
    }

    protected Instructor(Parcel in) {
        instructorId = in.readLong();
        name = in.readString();
        officeHourList = in.createTypedArrayList(OfficeHour.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(instructorId);
        dest.writeString(name);
        dest.writeTypedList(officeHourList);
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

    public List<OfficeHour> getOfficeHourList() {
        return officeHourList;
    }

    public void setOfficeHourList(List<OfficeHour> officeHourList) {
        this.officeHourList = officeHourList;
    }
}
