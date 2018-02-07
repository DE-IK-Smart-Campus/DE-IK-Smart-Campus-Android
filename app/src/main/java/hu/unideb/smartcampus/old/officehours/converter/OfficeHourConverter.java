package hu.unideb.smartcampus.old.officehours.converter;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.old.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.old.officehours.pojo.FromToDatesInLong;
import hu.unideb.smartcampus.old.officehours.pojo.Instructor;
import hu.unideb.smartcampus.old.officehours.pojo.OfficeHour;
import hu.unideb.smartcampus.old.officehours.pojo.Subject;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.ConsultingDateIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.FromToDateIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.InstructorIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.SubjectIqElement;

/**
 * Created by Headswitcher on 2017. 03. 27..
 * //TODO
 */

public class OfficeHourConverter {

    public static Instructor convertToAskInstructorOfficeHourPojo(InstructorConsultingDatesIqRequest instructorConsultingDatesIqRequest) {

        Instructor instructor = new Instructor();
        instructor.setName(""); // TODO
        instructor.setInstructorId(Long.valueOf(instructorConsultingDatesIqRequest.getInstructorId()));

        List<OfficeHour> officeHourList = new ArrayList<>();

        for (ConsultingDateIqElement consultingDateIqElement : instructorConsultingDatesIqRequest.getConsultingDates()) {
            FromToDateIqElement fromToDates = consultingDateIqElement.getFromToDates();
            FromToDatesInLong fromToDatesInLong = new FromToDatesInLong(fromToDates.getFrom(), fromToDates.getTo());
            officeHourList.add(new OfficeHour(consultingDateIqElement.getConsultingDateId(), fromToDatesInLong, 0L));
        }

        instructor.setOfficeHourList(officeHourList);

        return instructor;
    }

    public static AskSubjectsPojo convertToAskSubjectsProcessMessagePojo(SubjectsIqRequest subjectsIqRequest) {

        AskSubjectsPojo askSubjectsPojo = new AskSubjectsPojo();
        List<Instructor> instructors = new ArrayList<>();
        List<Subject> subjects = new ArrayList<>();

        Instructor instructor;
        Subject subject;

        for (int i = 0; i < subjectsIqRequest.getSubjects().size(); i++) {
            SubjectIqElement subjectIqElement = subjectsIqRequest.getSubjects().get(i);
            subject = new Subject();
            if (subjectIqElement.getInstructors() != null) {
                for (int j = 0; j < subjectIqElement.getInstructors().size(); j++) {
                    InstructorIqElement instructorIqElement = subjectIqElement.getInstructors().get(j);
                    instructor = new Instructor();
                    instructor.setName(instructorIqElement.getName());
                    instructor.setInstructorId(instructorIqElement.getInstructorId());
                    instructors.add(instructor);
                }
            }
            subject.setInstructors(instructors);
            subject.setName(subjectIqElement.getSubjectName());
            subjects.add(subject);
            instructors = new ArrayList<>();
        }
        askSubjectsPojo.setSubjects(subjects);
        return askSubjectsPojo;
    }
}
