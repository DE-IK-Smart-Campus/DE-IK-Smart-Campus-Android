package hu.unideb.smartcampus.main.activity.attendance.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import hu.unideb.smartcampus.main.activity.officehours.pojo.BasePojo;

public class AskSubjectPojo extends BasePojo implements Parcelable {

    List<Subject> subjectList;

    public AskSubjectPojo() {
    }

    public AskSubjectPojo(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    protected AskSubjectPojo(Parcel in) {
        subjectList = in.createTypedArrayList(Subject.CREATOR);
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(subjectList);
    }

    public static final Creator<AskSubjectPojo> CREATOR = new Creator<AskSubjectPojo>() {
        @Override
        public AskSubjectPojo createFromParcel(Parcel in) {
            return new AskSubjectPojo(in);
        }

        @Override
        public AskSubjectPojo[] newArray(int size) {
            return new AskSubjectPojo[size];
        }
    };
}
