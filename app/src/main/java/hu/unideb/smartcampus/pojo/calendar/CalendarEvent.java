package hu.unideb.smartcampus.pojo.calendar;

import java.io.Serializable;

import hu.unideb.smartcampus.pojo.calendar.type.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEvent implements Serializable {

    private String eventName;
    private String eventPlace;
    private Long eventStartTime;
    private Long eventEndTime;
    private EventType eventType;
    private TimetableEvent timetableEvent;
    private CustomEvent customEvent;
}
