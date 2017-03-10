package hu.unideb.smartcampus.xmpp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 08..
 */

public class CHJsonPojo implements Parcelable {

    private String messageType;
    private List<CHSubjectPojo> subjects;

    public CHJsonPojo(String messageType, List<CHSubjectPojo> subjects) {
        this.messageType = messageType;
        this.subjects = subjects;
    }

    public CHJsonPojo() {
    }

    protected CHJsonPojo(Parcel in) {
        messageType = in.readString();
        in.readTypedList(subjects, CHSubjectPojo.CREATOR);
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

    public static final Creator<CHJsonPojo> CREATOR = new Creator<CHJsonPojo>() {
        @Override
        public CHJsonPojo createFromParcel(Parcel in) {
            return new CHJsonPojo(in);
        }

        @Override
        public CHJsonPojo[] newArray(int size) {
            return new CHJsonPojo[size];
        }
    };

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<CHSubjectPojo> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<CHSubjectPojo> subjects) {
        this.subjects = subjects;
    }
}


