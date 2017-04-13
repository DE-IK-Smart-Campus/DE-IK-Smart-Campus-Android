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
            timetableEvent = new TimetableEvent();
            CalendarSubjectIqElement calendarSubjectIqElement = calendarSubjectsIqRequest.getSubjectEvents().get(0);
            AppointmentTimeIqElement calendarTimeIqElement = calendarSubjectIqElement.getAppointmentTimes().get(0);
            timetableEvent.setTimetableEventDate(calendarTimeIqElement.getWhen());
            timetableEvent.setTimetableEventName(calendarSubjectIqElement.getSubjectName());
            timetableEvent.setTimetableEventDescription(calendarSubjectIqElement.getDescription());
            timetableEvent.setTimetableEventPlace(calendarSubjectIqElement.getWhere());
            timetableEvent.setTimetableEventStartTime(calendarTimeIqElement.getFrom());
            timetableEvent.setTimetableEventEndTime(calendarTimeIqElement.getTo());
            timetableEventsList.add(timetableEvent);
        }

        askTimetableEventPojo.setTimetableEvents(timetableEventsList);

        return askTimetableEventPojo;
    }

}
