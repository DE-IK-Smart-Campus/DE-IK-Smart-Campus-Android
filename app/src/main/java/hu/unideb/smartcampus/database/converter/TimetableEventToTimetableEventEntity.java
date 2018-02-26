package hu.unideb.smartcampus.database.converter;

import hu.unideb.smartcampus.database.entity.TimetableEventEntity;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

public class TimetableEventToTimetableEventEntity {

    public static TimetableEventEntity convert(TimetableEvent timetableEvent) {
        return TimetableEventEntity.builder()
                .id(timetableEvent.getId())
                .timetableEventDate(timetableEvent.getTimetableEventDate())
                .timetableEventName(timetableEvent.getTimetableEventName())
                .timetableEventDescription(timetableEvent.getTimetableEventDescription())
                .timetableEventPlace(timetableEvent.getTimetableEventPlace())
                .timetableEventStartTime(timetableEvent.getTimetableEventStartTime())
                .timetableEventEndTime(timetableEvent.getTimetableEventEndTime())
                .build();
    }
}
