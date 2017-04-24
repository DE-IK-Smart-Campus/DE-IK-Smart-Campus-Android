package hu.unideb.smartcampus.sqlite.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.shared.iq.request.element.StudentIqElement;
import hu.unideb.smartcampus.sqlite.helper.DatabaseHelper;
import hu.unideb.smartcampus.sqlite.model.CustomEvent;
import hu.unideb.smartcampus.sqlite.model.TimetableEvent;

import static hu.unideb.smartcampus.sqlite.helper.DatabaseHelper.CUSTOMEVENT_STARTDATE;
import static hu.unideb.smartcampus.sqlite.helper.DatabaseHelper.TABLE_CUSTOMEVENT;
import static hu.unideb.smartcampus.sqlite.helper.DatabaseHelper.TABLE_TIMETABLEEVENT;
import static hu.unideb.smartcampus.sqlite.helper.DatabaseHelper.TIMETABLEEVENT_DATE;

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

    public void close(){
        dbHelper.close();
    }

    public void insertTimetableEvent(TimetableEvent timetableEvent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_DATE, timetableEvent.getTimetableEventDate());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_NAME,timetableEvent.getTimetableEventName());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_DESCRIPTION, timetableEvent.getTimetableEventDescription());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_PLACE,timetableEvent.getTimetableEventPlace());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_STARTTIME,timetableEvent.getTimetableEventStartTime());
        contentValues.put(DatabaseHelper.TIMETABLEEVENT_ENDTIME,timetableEvent.getTimetableEventEndTime());
        database.insert(TABLE_TIMETABLEEVENT,null,contentValues);
    }

    public void insertCustomEvent(CustomEvent customEvent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CUSTOMEVENT_UUID, customEvent.getUuid());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_NAME, customEvent.getEventName());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_DESCRIPTION, customEvent.getEventDescription());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_PLACE, customEvent.getEventPlace());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_STARTDATE, customEvent.getEventStartDate());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_STARTTIME, customEvent.getEventStartTime());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_ENDDATE, customEvent.getEventEndDate());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_ENDTIME, customEvent.getEventEndTime());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_REPEAT, customEvent.getEvenetRepeat());
        contentValues.put(DatabaseHelper.CUSTOMEVENT_REMAINDER, customEvent.getEventReminder());
        database.insert(TABLE_CUSTOMEVENT,null,contentValues);
    }

    public List<TimetableEvent> getAllTimetableEvent(){
        List<TimetableEvent> timetableEvents = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TIMETABLEEVENT;


        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do{
                TimetableEvent timetableEvent = new TimetableEvent();
                timetableEvent.setId(0);
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

    public List<CustomEvent> getAllCustomEvent(){
        List<CustomEvent> customEvents = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMEVENT;

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

                customEvents.add(customEvent);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return customEvents;
    }

    public List<TimetableEvent> getTimetableEventDate(Long selectedDate) {
        List<TimetableEvent> resultTimetableEvents = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_TIMETABLEEVENT + " WHERE " + TIMETABLEEVENT_DATE + " = " + selectedDate;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do{
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

        return  resultTimetableEvents;
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
}
