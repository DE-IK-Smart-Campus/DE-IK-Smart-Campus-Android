package hu.unideb.smartcampus.old.attendance.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class SubjectDate implements Parcelable {

    private Long dateId;
    private Long date;
    private Boolean yesOrNo;

    public SubjectDate() {
    }

    public SubjectDate(Long dateId,Long date, Boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
        this.dateId = dateId;
        this.date = date;
    }

    protected SubjectDate(Parcel in) {
        dateId = in.readLong();
        date = in.readLong();
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public Boolean getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(Boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(dateId);
        parcel.writeLong(date);
    }

    public static final Creator<SubjectDate> CREATOR = new Creator<SubjectDate>() {
        @Override
        public SubjectDate createFromParcel(Parcel in) {
            return new SubjectDate(in);
        }

        @Override
        public SubjectDate[] newArray(int size) {
            return new SubjectDate[size];
        }
    };
}
