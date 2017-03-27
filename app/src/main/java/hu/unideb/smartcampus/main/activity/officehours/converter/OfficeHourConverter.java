package hu.unideb.smartcampus.main.activity.officehours.converter;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.main.activity.officehours.pojo.AskInstructorOfficeHourPojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Instructor;
import hu.unideb.smartcampus.main.activity.officehours.pojo.Subject;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.element.InstructorIqElement;
import hu.unideb.smartcampus.shared.iq.request.element.SubjectIqElement;

/**
 * Created by Headswitcher on 2017. 03. 27..
 * //TODO
 */

public class OfficeHourConverter {

    public static AskInstructorOfficeHourPojo convertToAskSubjectsProcessMessagePojo(InstructorConsultingDatesIqRequest instructorConsultingDatesIqRequest) {
    /*
        Instructor instructor = new Instructor();

        instructorConsultingDatesIqRequest.getInstructorId();
        instructorConsultingDatesIqRequest.getConsultingDates();
    */
        return null;

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
            for (int j = 0; j < subjectIqElement.getInstructors().size(); j++) {
                InstructorIqElement instructorIqElement = subjectIqElement.getInstructors().get(j);
                instructor = new Instructor();
                instructor.setName(instructorIqElement.getName());
                instructor.setInstructorId(instructorIqElement.getInstructorId());
                instructors.add(instructor);
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
