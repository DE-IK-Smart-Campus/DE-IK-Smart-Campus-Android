package hu.unideb.smartcampus.adapter.consultingHours.dataObjects;

import java.util.Date;

/**
 * Created by Headswitcher on 2017. 02. 27..
 */

public class FromUntilDates {

    Date from;
    Date until;

    public FromUntilDates(Date from, Date until) {
        this.from = from;
        this.until = until;
    }

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

    @Override
    public String toString() {
        return from.getHours() + ":" + from.getMinutes() +"-"+until.getHours() + ":" + until.getMinutes();
    }
}
