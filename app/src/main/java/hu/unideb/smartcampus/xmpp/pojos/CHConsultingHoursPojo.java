package hu.unideb.smartcampus.xmpp.pojos;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Headswitcher on 2017. 03. 10..
 */

public class CHConsultingHoursPojo implements Parcelable{

    private Long consultingHourId;
    private CHFromToDatesPojo fromToDates;
    private Long reservedSum;

    public CHConsultingHoursPojo() {
    }

    public CHConsultingHoursPojo(Long consultingHourId, CHFromToDatesPojo fromToDates, Long reservedSum) {
        this.consultingHourId = consultingHourId;
        this.fromToDates = fromToDates;
        this.reservedSum = reservedSum;
    }

    protected CHConsultingHoursPojo(Parcel in) {
        consultingHourId = in.readLong();
        fromToDates = in.readParcelable(CHFromToDatesPojo.class.getClassLoader());
        reservedSum = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(consultingHourId);
        dest.writeParcelable(fromToDates, flags);
        dest.writeLong(reservedSum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CHConsultingHoursPojo> CREATOR = new Creator<CHConsultingHoursPojo>() {
        @Override
        public CHConsultingHoursPojo createFromParcel(Parcel in) {
            return new CHConsultingHoursPojo(in);
        }

        @Override
        public CHConsultingHoursPojo[] newArray(int size) {
            return new CHConsultingHoursPojo[size];
        }
    };

    public Long getConsultingHourId() {
        return consultingHourId;
    }

    public void setConsultingHourId(Long consultingHourId) {
        this.consultingHourId = consultingHourId;
    }

    public CHFromToDatesPojo getFromToDates() {
        return fromToDates;
    }

    public void setFromToDates(CHFromToDatesPojo fromToDates) {
        this.fromToDates = fromToDates;
    }

    public Long getReservedSum() {
        return reservedSum;
    }

    public void setReservedSum(Long reservedSum) {
        this.reservedSum = reservedSum;
    }
}
