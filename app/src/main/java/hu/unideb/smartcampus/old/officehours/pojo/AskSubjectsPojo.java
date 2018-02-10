package hu.unideb.smartcampus.old.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * This class for the JSON parse  ( with Jackson),
 * when messageTypessss at Office hours is "AskSubjectsProcessMessageResponse"
 * <p>
 * <p>
 * Created by Headswitcher on 2017. 03. 08..
 */

public class AskSubjectsPojo extends BasePojo implements Serializable {

    private List<Subject> subjects;

    public AskSubjectsPojo(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public AskSubjectsPojo() {
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}


