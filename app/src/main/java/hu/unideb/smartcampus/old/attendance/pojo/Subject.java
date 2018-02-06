package hu.unideb.smartcampus.old.attendance.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Subject implements Parcelable {

    private String name;
    private List<SubjectDate> subjectDates;

    public Subject() {
    }

    public Subject(String name, List<SubjectDate> subjectDates) {
        this.name = name;
        this.subjectDates = subjectDates;
    }

    protected Subject(Parcel in) {
        name = in.readString();
        subjectDates = in.createTypedArrayList(SubjectDate.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(subjectDates);
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectDate> getSubjectDates() {
        return subjectDates;
    }

    public void setSubjectDates(List<SubjectDate> subjectDates) {
        this.subjectDates = subjectDates;
    }
}
