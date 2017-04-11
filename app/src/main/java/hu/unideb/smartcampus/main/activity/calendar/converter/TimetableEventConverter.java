package hu.unideb.smartcampus.main.activity.calendar.converter;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.main.activity.calendar.pojo.AskTimetableEventPojo;
import hu.unideb.smartcampus.sqlite.models.TimetableEvent;
import hu.unideb.smartcampus.shared.iq.request.CalendarSubjectsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.AppointmentTimeIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.CalendarSubjectIqElement;

public class TimetableEventConverter {

    public static AskTimetableEventPojo convertToAskTimeTableEventPojo(CalendarSubjectsIqRequest calendarSubjectsIqRequest) {

        AskTimetableEventPojo askTimetableEventPojo = new AskTimetableEventPojo();
        TimetableEvent timetableEvent;

        List<TimetableEvent> timetableEventsList = new ArrayList<>();

        for(int i = 0; i<calendarSubjectsIqRequest.getSubjectEvents().size(); i++) {
            timetableEvent = new TimetableEvent();
            CalendarSubjectIqElement calendarSubjectIqElement = calendarSubjectsIqRequest.getSubjectEvents().get(0);
            AppointmentTimeIqElement calendarT = calendarSubjectIqElement.getAppointmentTimes().get(0);
            timetableEvent.setTimetableEventDate(calendarT.getWhen());
            timetableEvent.setTimetableEventName(calendarSubjectIqElement.getSubjectName());
            timetableEvent.setTimetableEventDescription(calendarSubjectIqElement.getDescription());
            timetableEvent.setTimetableEventPlace(calendarSubjectIqElement.getWhere());
            timetableEvent.setTimetableEventStartTime(calendarT.getFrom());
            timetableEvent.setTimetableEventEndTime(calendarT.getTo());
            timetableEventsList.add(timetableEvent);
        }

        askTimetableEventPojo.setTimetableEvents(timetableEventsList);

        return askTimetableEventPojo;
    }

}
