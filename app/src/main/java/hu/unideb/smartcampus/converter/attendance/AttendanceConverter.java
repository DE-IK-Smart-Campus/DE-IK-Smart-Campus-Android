package hu.unideb.smartcampus.converter.attendance;

import hu.unideb.smartcampus.pojo.attendance.AskAttendancetPojo;

public class AttendanceConverter {

//    public static AskAttendancetPojo convertToAskSubjectPojo(ListUserAttendanceIqRequest listUserAttendanceIqRequest){
//
//        return new AskAttendancetPojo();
//    }

//    public static AskAttendancetPojo convertToAskSubjectPojo(ListUserAttendanceIqRequest listUserAttendanceIqRequest) {
//        AskAttendancetPojo askSubjectPojo = new AskAttendancetPojo();
//        List<SubjectDate> subjectDateList = new ArrayList<>();
//        List<Subject> subjects = new ArrayList<>();
//
//        Subject subject;
//        SubjectDate subjectDate;
//
//        for (int i = 0; i < listUserAttendanceIqRequest.getSubjectEvents().size(); i++) {
//            CalendarSubjectIqElement calendarSubjectIqElement = listUserAttendanceIqRequest.getSubjectEvents().get(i);
//            subject = new Subject();
//            if (calendarSubjectIqElement.getAppointmentTimes() != null) {
//                for (int j = 0; j < calendarSubjectIqElement.getAppointmentTimes().size(); j++) {
//                    subjectDate = new SubjectDate();
//                    AppointmentTimeIqElement appointmentTimeIqElement = calendarSubjectIqElement.getAppointmentTimes().get(j);
//                    subjectDate.setDateId(appointmentTimeIqElement.getId());
//                    subjectDate.setDate(appointmentTimeIqElement.getWhen());
//                    subjectDate.setYesOrNo(appointmentTimeIqElement.isPresent());
//                    if(calendarSubjectIqElement.getDescription().equals("G")) {
//                        subject.setName(calendarSubjectIqElement.getSubjectName() + "\n Gyakorlat");
//                    } else if (calendarSubjectIqElement.getDescription().equals("O")) {
//                        subject.setName(calendarSubjectIqElement.getSubjectName() + "\n Egyéb");
//                    } else if (calendarSubjectIqElement.getDescription().equals("L")) {
//                        subject.setName(calendarSubjectIqElement.getSubjectName() + "\n Labor");
//                    } else if (calendarSubjectIqElement.getDescription().equals("E")) {
//                        subject.setName(calendarSubjectIqElement.getSubjectName() + "\n Elmélet");
//                    }
//                    subject.setSubjectDates(subjectDateList);
//                    subjectDateList.add(subjectDate);
//                }
//            }
//            subjects.add(subject);
//            subjectDateList = new ArrayList<>();
//        }
//        askSubjectPojo.setSubjectList(subjects);
//        return askSubjectPojo;
//
//    }

}
