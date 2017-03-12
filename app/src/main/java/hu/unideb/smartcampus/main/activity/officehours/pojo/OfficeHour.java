package hu.unideb.smartcampus.main.activity.officehours.pojo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Pojo class
 * <p>
 * The teachers will have office hour imported from the University website or the teacher will
 * add it through the app.
 * <p>
 * The students can reserve a date this is one of the main features in the app.
 * The object have a {@code Long consultingHourId , FromToDatesInLong fromToDatesInLong; , Long reservedSum; }
 *
 * @see hu.unideb.smartcampus.main.activity.officehours.activity.ReserveOfficeHourActivity
 * @see FromToDatesInLong
 * @see hu.unideb.smartcampus.main.activity.officehours.fragment.OfficeHourFragment
 * @see //addwith app //TODO
 * @see Parcelable
 * <p>
 * Created by Headswitcher on 2017. 03. 10..
 */

public class OfficeHour implements Parcelable {

    private Long consultingHourId;
    private FromToDatesInLong fromToDates;
    private Long reservedSum;

    public OfficeHour() {
    }

    public OfficeHour(Long consultingHourId, FromToDatesInLong fromToDates, Long reservedSum) {
        this.consultingHourId = consultingHourId;
        this.fromToDates = fromToDates;
        this.reservedSum = reservedSum;
    }

    protected OfficeHour(Parcel in) {
        consultingHourId = in.readLong();
        fromToDates = in.readParcelable(FromToDatesInLong.class.getClassLoader());
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

    public static final Creator<OfficeHour> CREATOR = new Creator<OfficeHour>() {
        @Override
        public OfficeHour createFromParcel(Parcel in) {
            return new OfficeHour(in);
        }

        @Override
        public OfficeHour[] newArray(int size) {
            return new OfficeHour[size];
        }
    };

    public Long getConsultingHourId() {
        return consultingHourId;
    }

    public void setConsultingHourId(Long consultingHourId) {
        this.consultingHourId = consultingHourId;
    }

    public FromToDatesInLong getFromToDates() {
        return fromToDates;
    }

    public void setFromToDates(FromToDatesInLong fromToDates) {
        this.fromToDates = fromToDates;
    }

    public Long getReservedSum() {
        return reservedSum;
    }

    public void setReservedSum(Long reservedSum) {
        this.reservedSum = reservedSum;
    }
}
