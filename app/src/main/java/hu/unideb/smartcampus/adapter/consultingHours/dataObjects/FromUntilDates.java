package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Headswitcher on 2017. 02. 27..
 */

public class FromUntilDates implements Parcelable {

    private Date from;
    private Date until;

    public FromUntilDates(Date from, Date until) {
        this.from = from;
        this.until = until;
    }

    protected FromUntilDates(Parcel in) {
        from = new Date(in.readLong());
        until = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(from.getTime());
        out.writeLong(until.getTime());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FromUntilDates> CREATOR = new Creator<FromUntilDates>() {
        @Override
        public FromUntilDates createFromParcel(Parcel in) {
            return new FromUntilDates(in);
        }

        @Override
        public FromUntilDates[] newArray(int size) {
            return new FromUntilDates[size];
        }
    };

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }


    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

}
