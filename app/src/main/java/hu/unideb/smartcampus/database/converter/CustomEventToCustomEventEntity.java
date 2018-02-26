package hu.unideb.smartcampus.database.converter;

import hu.unideb.smartcampus.database.entity.CustomEventEntity;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;

public class CustomEventToCustomEventEntity {

    public static CustomEventEntity convert(CustomEvent customEvent) {
        return CustomEventEntity.builder()
                .id(customEvent.getId())
                .uuid(customEvent.getUuid())
                .eventName(customEvent.getEventName())
                .eventDescription(customEvent.getEventDescription())
                .eventPlace(customEvent.getEventPlace())
                .eventStartDate(customEvent.getEventStartDate())
                .eventStartTime(customEvent.getEventStartTime())
                .eventEndDate(customEvent.getEventEndDate())
                .eventEndTime(customEvent.getEventEndTime())
                .eventRepeat(customEvent.getEventRepeat())
                .eventReminder(customEvent.getEventReminder())
                .build();
    }
}
