package hu.unideb.smartcampus.pojo.calendar;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskTimetableEventPojo implements Serializable {

    private List<TimetableEvent> timetableEvents;
}