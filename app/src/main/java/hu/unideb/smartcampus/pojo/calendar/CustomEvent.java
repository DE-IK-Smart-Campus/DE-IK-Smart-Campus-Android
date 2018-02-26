package hu.unideb.smartcampus.pojo.calendar;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomEvent implements Serializable{

    private long id;
    private String uuid;
    private String eventName;
    private String eventDescription;
    private String eventPlace;
    private Long eventStartDate;
    private Long eventStartTime;
    private Long eventEndDate;
    private Long eventEndTime;
    private String eventRepeat;
    private String eventReminder;
}
