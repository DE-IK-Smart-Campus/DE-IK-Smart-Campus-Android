package hu.unideb.smartcampus.main.activity.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


/**
 * <p>
 * This class for the JSON parse  ( with Jackson),
 * when messageTypessss at Office hours is "AskSubjectsProcessMessageResponse"
 * <p>
 * <p>
 * Created by Headswitcher on 2017. 03. 08..
 */

public class AskSubjectsPojo extends BasePojo implements Parcelable {

    private List<Subject> subjects;

    public AskSubjectsPojo() {
    }

    public AskSubjectsPojo(List<Subject> subjects) {
        this.subjects = subjects;
    }

    protected AskSubjectsPojo(Parcel in) {
        subjects = in.createTypedArrayList(Subject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(subjects);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AskSubjectsPojo> CREATOR = new Creator<AskSubjectsPojo>() {
        @Override
        public AskSubjectsPojo createFromParcel(Parcel in) {
            return new AskSubjectsPojo(in);
        }

        @Override
        public AskSubjectsPojo[] newArray(int size) {
            return new AskSubjectsPojo[size];
        }
    };

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}


