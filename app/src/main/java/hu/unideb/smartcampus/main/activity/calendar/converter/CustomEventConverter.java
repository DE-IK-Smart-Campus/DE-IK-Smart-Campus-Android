package hu.unideb.smartcampus.main.activity.calendar.converter;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.main.activity.calendar.pojo.AskCustomEventPojo;
import hu.unideb.smartcampus.shared.iq.request.ListCustomEventIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.CustomEventIqElement;
import hu.unideb.smartcampus.sqlite.model.CustomEvent;

public class CustomEventConverter {

    public static AskCustomEventPojo convertToAskCustomEventPojo(ListCustomEventIqRequest listCustomEventIqRequest) {

        AskCustomEventPojo askCustomEventPojo = new AskCustomEventPojo();
        CustomEvent customEvent;

        List<CustomEvent> customEvents = new ArrayList<>();
        for (int i = 0; i < listCustomEventIqRequest.getCustomEvents().size(); i++) {
            customEvent = new CustomEvent();
            CustomEventIqElement customEventIqElement = listCustomEventIqRequest.getCustomEvents().get(i);

            customEvent.setUuid(customEventIqElement.getGuid());
            customEvent.setEventName(customEventIqElement.getEventName());
            customEvent.setEventDescription(customEventIqElement.getEventDescription());
            customEvent.setEventPlace(customEventIqElement.getEventPlace());
            customEvent.setEventStartDate(customEventIqElement.getEventWhen());
            customEvent.setEventEndDate(customEventIqElement.getEventEnd());
            customEvent.setEventStartTime(customEventIqElement.getEventStart());
            customEvent.setEventEndTime(customEventIqElement.getEventEnd());
            customEvent.setEvenetRepeat(customEventIqElement.getEventRepeat());
            customEvent.setEventReminder(customEventIqElement.getReminder());

            customEvents.add(customEvent);
        }

        askCustomEventPojo.setCustomEvents(customEvents);
        return askCustomEventPojo;

    }

}
