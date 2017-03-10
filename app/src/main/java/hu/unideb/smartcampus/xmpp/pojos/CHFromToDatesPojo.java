package hu.unideb.smartcampus.xmpp.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Headswitcher on 2017. 03. 10..
 */

public class CHFromToDatesPojo implements Parcelable {

    private Long from;
    private Long to;

    public CHFromToDatesPojo() {
    }

    public CHFromToDatesPojo(Long from, Long to) {
        this.from = from;
        this.to = to;
    }

    protected CHFromToDatesPojo(Parcel in) {
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

    public static final Creator<CHFromToDatesPojo> CREATOR = new Creator<CHFromToDatesPojo>() {
        @Override
        public CHFromToDatesPojo createFromParcel(Parcel in) {
            return new CHFromToDatesPojo(in);
        }

        @Override
        public CHFromToDatesPojo[] newArray(int size) {
            return new CHFromToDatesPojo[size];
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
