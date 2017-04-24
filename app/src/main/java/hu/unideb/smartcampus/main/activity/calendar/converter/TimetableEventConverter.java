package hu.unideb.smartcampus.main.activity.calendar.converter;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.main.activity.calendar.pojo.AskTimetableEventPojo;
import hu.unideb.smartcampus.shared.iq.request.CalendarSubjectsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.AppointmentTimeIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.CalendarSubjectIqElement;
import hu.unideb.smartcampus.sqlite.model.TimetableEvent;

public class TimetableEventConverter {

    public static AskTimetableEventPojo convertToAskTimeTableEventPojo(CalendarSubjectsIqRequest calendarSubjectsIqRequest) {

        AskTimetableEventPojo askTimetableEventPojo = new AskTimetableEventPojo();
        TimetableEvent timetableEvent;

        List<TimetableEvent> timetableEventsList = new ArrayList<>();
        for(int i = 0; i<calendarSubjectsIqRequest.getSubjectEvents().size(); i++) {
            CalendarSubjectIqElement calendarSubjectIqElement = calendarSubjectsIqRequest.getSubjectEvents().get(i);

            for(int j = 0;  j< calendarSubjectIqElement.getAppointmentTimes().size(); j++){

                timetableEvent = new TimetableEvent();

                AppointmentTimeIqElement calendarTimeIqElement = calendarSubjectIqElement.getAppointmentTimes().get(j);
                timetableEvent.setTimetableEventDate(calendarTimeIqElement.getWhen());
                timetableEvent.setTimetableEventName(calendarSubjectIqElement.getSubjectName());
                if(calendarSubjectIqElement.getDescription().equals("E")) {
                    timetableEvent.setTimetableEventDescription("Előadás");
                } else if (calendarSubjectIqElement.getDescription().equals("L")) {
                    timetableEvent.setTimetableEventDescription("Labor");
                } else if (calendarSubjectIqElement.getDescription().equals("G")) {
                    timetableEvent.setTimetableEventDescription("Gyakorlat");
                } else if(calendarSubjectIqElement.getDescription().equals("O")) {
                    timetableEvent.setTimetableEventDescription("Egyéb");
                }
                timetableEvent.setTimetableEventPlace(calendarSubjectIqElement.getWhere());
                timetableEvent.setTimetableEventStartTime(calendarTimeIqElement.getFrom());
                timetableEvent.setTimetableEventEndTime(calendarTimeIqElement.getTo());
                timetableEventsList.add(timetableEvent);
            }
        }
        askTimetableEventPojo.setTimetableEvents(timetableEventsList);

        return askTimetableEventPojo;
    }

}
