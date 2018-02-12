package hu.unideb.smartcampus.old.sqlite.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.old.officehours.pojo.Instructor;
import hu.unideb.smartcampus.old.officehours.pojo.Subject;
import hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

import static hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper.CUSTOMEVENT_STARTDATE;
import static hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper.TABLE_CUSTOMEVENT;
import static hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper.TABLE_INSTRUCTORS;
import static hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper.TABLE_SUBJECTS;
import static hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper.TABLE_TIMETABLEEVENT;
import static hu.unideb.smartcampus.old.sqlite.helper.DatabaseHelper.TIMETABLEEVENT_DATE;

public class DatabaseManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context c) {
        context = c;
    }

    public DatabaseManager open() {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertSubject(String subjectName) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECTS_NAME_COL, subjectName);
        final long insert = sqLiteDatabase.insert(TABLE_SUBJECTS, null, contentValues);
        return insert;
    }

    public void insertInstructor(String subjectName, String instructorName) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        final Integer subjectId = getSubjectByName(subjectName);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.INSTURCORS_NAME_COL, instructorName);
        contentValues.put(DatabaseHelper.SUBJECT_ID_PK, subjectId);
        sqLiteDatabase.insert(TABLE_INSTRUCTORS, null, contentValues);
    }

    public Integer getSubjectByName(String subjectName) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String[] cols = {
                DatabaseHelper.SUBJECT_ID_PK
        };
        String where = DatabaseHelper.SUBJECTS_NAME_COL + " = ?";
        String whereArgs[] = {subjectName};

        Cursor cursor = sqLiteDatabase.query(
                TABLE_SUBJECTS,
                cols,
                where,
                whereArgs,
                null,
                null,
                null
        );

        Integer subjectId = null;
        while (cursor.moveToNext()) {
            subjectId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.SUBJECT_ID_PK));
        }
        cursor.close();
        return subjectId;
    }

    public void insertTimetableEvent(TimetableEvent timetableEvent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMETABLEEVENT_DATE, timetableEvent.getTimetableEventDate());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_NAME, timetableEvent.getTimetableEventName());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_DESCRIPTION, timetableEvent.getTimetableEventDescription());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_PLACE, timetableEvent.getTimetableEventPlace());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_STARTTIME, timetableEvent.getTimetableEventStartTime());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_ENDTIME, timetableEvent.getTimetableEventEndTime());
        database.insert(TABLE_TIMETABLEEVENT, null, contentValues);
    }

    public void insertCustomEvent(CustomEvent customEvent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CUSTOMEVENT_UUID, customEvent.getUuid());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_NAME, customEvent.getEventName());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_DESCRIPTION, customEvent.getEventDescription());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_PLACE, customEvent.getEventPlace());
        contentValues.put(CUSTOMEVENT_STARTDATE, customEvent.getEventStartDate());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_STARTTIME, customEvent.getEventStartTime());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_ENDDATE, customEvent.getEventEndDate());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_ENDTIME, customEvent.getEventEndTime());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_REPEAT, customEvent.getEvenetRepeat());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_REMAINDER, customEvent.getEventReminder());
        database.insert(TABLE_CUSTOMEVENT, null, contentValues);
    }

    public void setAllSubjectAndInstructor(List<Subject> subjectList) {
        for (Subject subject : subjectList) {
            insertSubject(subject.getName());
            final Integer subjectId = getSubjectByName(subject.getName());
            List<Instructor> instructorList = subject.getInstructors();
            for (Instructor instructor : instructorList) {
                insertInstructor(subjectId, instructor);
            }
        }
    }

    private void insertInstructor(int subjectId, Instructor instructor) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.INSTRUCTOR_ID_PK, instructor.getInstructorId());
        contentValues.put(DatabaseHelper.INSTURCORS_NAME_COL, instructor.getName());
        contentValues.put(DatabaseHelper.SUBJECT_ID_PK, subjectId);
        sqLiteDatabase.insert(TABLE_INSTRUCTORS, null, contentValues);
    }

    public List<Subject> getAllSubjectAndInstructor() {
        final List<Subject> allSubject = getAllSubject();
        for (Subject subject : allSubject) {
            List<Instructor> instructorsBySubjectId = getInstructorsBySubjectId(subject.getId());
            subject.setInstructors(instructorsBySubjectId);
        }
        return allSubject;
    }

    public List<Instructor> getInstructorsBySubjectId(Integer subjectId) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String[] cols = {
                DatabaseHelper.INSTRUCTOR_ID_PK,
                DatabaseHelper.INSTURCORS_NAME_COL

        };
        String where = DatabaseHelper.SUBJECT_ID_PK + " = ?";
        String whereArgs[] = {subjectId.toString()};

        Cursor cursor = sqLiteDatabase.query(
                TABLE_INSTRUCTORS,
                cols,
                where,
                whereArgs,
                null,
                null,
                null
        );

        List<Instructor> instructorList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Instructor instructor = new Instructor();
            instructor.setInstructorId(cursor.getLong(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.INSTRUCTOR_ID_PK)));
            instructor.setName(cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.INSTURCORS_NAME_COL)));
            instructorList.add(instructor);
        }
        cursor.close();
        return instructorList;
    }

    public List<TimetableEvent> getAllTimetableEvent() {
        List<TimetableEvent> timetableEvents = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TIMETABLEEVENT;


        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TimetableEvent timetableEvent = new TimetableEvent();
                timetableEvent.setId(cursor.getInt(0));
                timetableEvent.setTimetableEventDate(cursor.getLong(1));
                timetableEvent.setTimetableEventName(cursor.getString(2));
                timetableEvent.setTimetableEventDescription(cursor.getString(3));
                timetableEvent.setTimetableEventPlace(cursor.getString(4));
                timetableEvent.setTimetableEventStartTime(cursor.getLong(5));
                timetableEvent.setTimetableEventEndTime(cursor.getLong(6));

                timetableEvents.add(timetableEvent);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return timetableEvents;
    }

    public List<Subject> getAllSubject() {
        String selectQuery = "SELECT * FROM " + TABLE_SUBJECTS;
        Cursor cursor = database.rawQuery(selectQuery, null);
        List<Subject> subjectList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                final Subject subject = new Subject();
                subject.setId(cursor.getInt(0));
                subject.setName(cursor.getString(1));
                subjectList.add(subject);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return subjectList;
    }

    public List<Instructor> getAllInstructor() {
        String selectQuery = "SELECT * FROM " + TABLE_INSTRUCTORS;
        Cursor cursor = database.rawQuery(selectQuery, null);
        List<Instructor> instructorList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Instructor instructor = new Instructor();
                instructor.setName(cursor.getString(1));
                instructorList.add(instructor);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return instructorList;
    }

    public List<CustomEvent> getAllCustomEvent() {
        List<CustomEvent> customEvents = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMEVENT;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CustomEvent customEvent = new CustomEvent();
                customEvent.setUuid(cursor.getString(0));
                customEvent.setEventName(cursor.getString(1));
                customEvent.setEventDescription(cursor.getString(2));
                customEvent.setEventPlace(cursor.getString(3));
                customEvent.setEventStartDate(cursor.getLong(4));
                customEvent.setEventStartTime(cursor.getLong(5));
                customEvent.setEventEndDate(cursor.getLong(6));
                customEvent.setEventEndTime(cursor.getLong(7));
                customEvent.setEvenetRepeat(cursor.getString(8));
                customEvent.setEventReminder(cursor.getString(9));

                customEvents.add(customEvent);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return customEvents;
    }

    public List<TimetableEvent> getTimetableEventDate(Long selectedDate) {
        List<TimetableEvent> resultTimetableEvents = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TIMETABLEEVENT + " WHERE " + TIMETABLEEVENT_DATE + " = " + selectedDate;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TimetableEvent timetableEvent = new TimetableEvent();
                timetableEvent.setId(cursor.getInt(0));
                timetableEvent.setTimetableEventDate(cursor.getLong(1));
                timetableEvent.setTimetableEventName(cursor.getString(2));
                timetableEvent.setTimetableEventDescription(cursor.getString(3));
                timetableEvent.setTimetableEventPlace(cursor.getString(4));
                timetableEvent.setTimetableEventStartTime(cursor.getLong(5));
                timetableEvent.setTimetableEventEndTime(cursor.getLong(6));

                resultTimetableEvents.add(timetableEvent);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return resultTimetableEvents;
    }

    public List<CustomEvent> getCustomEventDate(Long selectedDate) {
        List<CustomEvent> customEventList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMEVENT + " WHERE " + CUSTOMEVENT_STARTDATE + " = " + selectedDate;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                CustomEvent customEvent = new CustomEvent();
                customEvent.setUuid(cursor.getString(0));
                customEvent.setEventName(cursor.getString(1));
                customEvent.setEventDescription(cursor.getString(2));
                customEvent.setEventPlace(cursor.getString(3));
                customEvent.setEventStartDate(cursor.getLong(4));
                customEvent.setEventStartTime(cursor.getLong(5));
                customEvent.setEventEndDate(cursor.getLong(6));
                customEvent.setEventEndTime(cursor.getLong(7));
                customEvent.setEvenetRepeat(cursor.getString(8));
                customEvent.setEventReminder(cursor.getString(9));

                customEventList.add(customEvent);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return customEventList;
    }

    public void deleteTable() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CUSTOMEVENT,null,null);
        sqLiteDatabase.delete(TABLE_TIMETABLEEVENT,null,null);
        sqLiteDatabase.close();
    }

}
