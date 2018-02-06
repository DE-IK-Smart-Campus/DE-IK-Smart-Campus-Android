package hu.unideb.smartcampus.old.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Pojo class
 * <p>
 * This class will have two "dates" ({@code Long from , Long to}) for the Office hours
 * <p>
 * Created by Headswitcher on 2017. 03. 10..
 */

public class FromToDatesInLong implements Parcelable {

    private Long from;
    private Long to;

    public FromToDatesInLong() {
    }

    public FromToDatesInLong(Long from, Long to) {
        this.from = from;
        this.to = to;
    }

    protected FromToDatesInLong(Parcel in) {
        from = in.readLong();
        to = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(from);
        dest.writeLong(to);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FromToDatesInLong> CREATOR = new Creator<FromToDatesInLong>() {
        @Override
        public FromToDatesInLong createFromParcel(Parcel in) {
            return new FromToDatesInLong(in);
        }

        @Override
        public FromToDatesInLong[] newArray(int size) {
            return new FromToDatesInLong[size];
        }
    };

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }
}
