package hu.unideb.smartcampus.xmpp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 08..
 */

public class CHInstructorPojo implements Parcelable {
    private Long instructorId;
    private String name;
    private List<CHConsultingHoursPojo> consultingHoursList;

    public CHInstructorPojo() {
    }

    public CHInstructorPojo(Long instructorId, String name, List<CHConsultingHoursPojo> consultingHoursList) {
        this.instructorId = instructorId;
        this.name = name;
        this.consultingHoursList = consultingHoursList;
    }

    protected CHInstructorPojo(Parcel in) {
        instructorId = in.readLong();
        name = in.readString();
        consultingHoursList = in.createTypedArrayList(CHConsultingHoursPojo.CREATOR);
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

    public static final Creator<CHInstructorPojo> CREATOR = new Creator<CHInstructorPojo>() {
        @Override
        public CHInstructorPojo createFromParcel(Parcel in) {
            return new CHInstructorPojo(in);
        }

        @Override
        public CHInstructorPojo[] newArray(int size) {
            return new CHInstructorPojo[size];
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

    public List<CHConsultingHoursPojo> getConsultingHoursList() {
        return consultingHoursList;
    }

    public void setConsultingHoursList(List<CHConsultingHoursPojo> consultingHoursList) {
        this.consultingHoursList = consultingHoursList;
    }
}
