package hu.unideb.smartcampus.pojo.calendar;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimetableEvent implements Serializable {

    private int id;
    private Long timetableEventDate;
    private String timetableEventName;
    private String timetableEventDescription;
    private String timetableEventPlace;
    private Long timetableEventStartTime;
    private Long timetableEventEndTime;

}


