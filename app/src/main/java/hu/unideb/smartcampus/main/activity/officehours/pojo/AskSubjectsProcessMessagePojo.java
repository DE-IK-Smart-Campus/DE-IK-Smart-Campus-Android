package hu.unideb.smartcampus.main.activity.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <p>
 * This class for the JSON parse  ( with Jackson),
 * when messageType at Office hours is "AskSubjectsProcessMessageResponse"
 * <p>
 * <p>
 * Created by Headswitcher on 2017. 03. 08..
 */

public class AskSubjectsProcessMessagePojo implements Parcelable {

    private String messageType;
    private List<Subject> subjects;

    public AskSubjectsProcessMessagePojo(String messageType, List<Subject> subjects) {
        this.messageType = messageType;
        this.subjects = subjects;
    }

    public AskSubjectsProcessMessagePojo() {
    }

    protected AskSubjectsProcessMessagePojo(Parcel in) {
        messageType = in.readString();
        in.readTypedList(subjects, Subject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageType);
        dest.writeTypedList(subjects);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AskSubjectsProcessMessagePojo> CREATOR = new Creator<AskSubjectsProcessMessagePojo>() {
        @Override
        public AskSubjectsProcessMessagePojo createFromParcel(Parcel in) {
            return new AskSubjectsProcessMessagePojo(in);
        }

        @Override
        public AskSubjectsProcessMessagePojo[] newArray(int size) {
            return new AskSubjectsProcessMessagePojo[size];
        }
    };

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}


