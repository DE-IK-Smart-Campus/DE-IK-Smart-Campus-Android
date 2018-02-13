package hu.unideb.smartcampus.pojo.officehours;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pojo class
 * <p>
 * The teachers will have office hour imported from the University website or the teacher will
 * add it through the app.
 * <p>
 * The students can reserve a date this is one of the main features in the app.
 * The object have a {@code Long consultingHourId , FromToDatesInLong fromToDatesInLong; , Long reservedSum; }
 *
 * @see FromToDatesInLong
 * @see //addwith app //TODO
 * @see Parcelable
 * <p>
 * Created by Headswitcher on 2017. 03. 10..
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeHour implements Serializable {

    private Long consultingHourId;
    private FromToDatesInLong fromToDates;
    private Long reservedSum;
}
