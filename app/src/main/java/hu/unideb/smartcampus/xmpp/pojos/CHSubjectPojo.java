package hu.unideb.smartcampus.xmpp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 08..
 */


public class CHSubjectPojo implements Parcelable {
    private String name;
    private List<CHInstructorPojo> instructors;

    public CHSubjectPojo() {
    }

    public CHSubjectPojo(String name, List<CHInstructorPojo> instructors) {
        this.name = name;
        this.instructors = instructors;
    }

    protected CHSubjectPojo(Parcel in) {
        name = in.readString();
        in.readTypedList(instructors, CHInstructorPojo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(instructors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CHSubjectPojo> CREATOR = new Creator<CHSubjectPojo>() {
        @Override
        public CHSubjectPojo createFromParcel(Parcel in) {
            return new CHSubjectPojo(in);
        }

        @Override
        public CHSubjectPojo[] newArray(int size) {
            return new CHSubjectPojo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CHInstructorPojo> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<CHInstructorPojo> instructors) {
        this.instructors = instructors;
    }
}

