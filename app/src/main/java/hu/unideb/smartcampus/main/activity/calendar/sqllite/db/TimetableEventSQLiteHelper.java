package hu.unideb.smartcampus.main.activity.calendar.sqllite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class TimetableEventSQLiteHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "TimetableEventDB";
    private static final String TABLE_EVENTS = "events";
    private static final String TimetableEvent_ID = "id";
    private static final String TimetableEvent_date = "timetableDate";
    private static final String TimetableEvent_name = "timetableName";
    private static final String TimetableEvent_description = "timetableDescription";
    private static final String TimetableEvent_place = "timetablePlace";
    private static final String TimetableEvent_startTime = "timetableStartTime";
    private static final String TimetableEvent_endTime = "timetableEndTime";

    private static final String[] COLUMNS = {TimetableEvent_ID,TimetableEvent_date,TimetableEvent_name,TimetableEvent_description,TimetableEvent_place,TimetableEvent_startTime,TimetableEvent_endTime};

    public TimetableEventSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_EVENT_TABLE = "CREATE TABLE events ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "timetableDate LONG, " + "timetableName TEXT, " + "timetableDescription TEXT, " + "timetablePlace TEXT, "+ "timetableStartTime LONG, "  + "timetableEndTime LONG )";
        database.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS events");
        this.onCreate(database);
    }

    public void createEvent(TimetableEvent event){
        SQLiteDatabase timetableEventDB = this.getWritableDatabase();

        ContentValues timetableValues = new ContentValues();
        timetableValues.put(TimetableEvent_date, event.getTimetableEventDate());
        timetableValues.put(TimetableEvent_name, event.getTimetableEventName());
        timetableValues.put(TimetableEvent_description, event.getTimetableEventDescription());
        timetableValues.put(TimetableEvent_place, event.getTimetableEventPlace());
        timetableValues.put(TimetableEvent_startTime, event.getTimetableEventStartTime());
        timetableValues.put(TimetableEvent_endTime, event.getTimetableEventEndTime());

        timetableEventDB.insert(TABLE_EVENTS, null, timetableValues);
        timetableEventDB.close();
    }

    public TimetableEvent readTimetableEvent(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_EVENTS, COLUMNS, "id = ?", new String[] {String.valueOf(id) }, null, null, null, null);

        if(cursor != null)
            cursor.moveToNext();

            TimetableEvent timetableEvent = new TimetableEvent();
            timetableEvent.setId(Integer.parseInt(cursor.getString(0)));
            timetableEvent.setTimetableEventDate(cursor.getLong(1));
            timetableEvent.setTimetableEventName(cursor.getString(2));
            timetableEvent.setTimetableEventDescription(cursor.getString(4));
            timetableEvent.setTimetableEventPlace(cursor.getString(5));
            timetableEvent.setTimetableEventStartTime(cursor.getLong(6));
            timetableEvent.setTimetableEventEndTime(cursor.getLong(7));


        return timetableEvent;

    }

    public List<TimetableEvent> getAllEvents() {
        List<TimetableEvent> timetableEvents = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        TimetableEvent timetableEvent;
        if(cursor.moveToFirst()) {
            do{
                timetableEvent = new TimetableEvent();
                timetableEvent.setId(Integer.parseInt(cursor.getString(0)));
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
        return  timetableEvents;
    }

    public List<TimetableEvent> getDateEvents(Long date) {
        List<TimetableEvent> timetableEvents = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + "timetableDate = " + date;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        TimetableEvent timetableEvent;
        if(cursor.moveToFirst()) {
            do{
                timetableEvent = new TimetableEvent();
                timetableEvent.setId(Integer.parseInt(cursor.getString(0)));
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
        return  timetableEvents;
    }

    public int updateTimetableEvent(TimetableEvent timetableEvent) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues timetableValues = new ContentValues();
        timetableValues.put(TimetableEvent_date, timetableEvent.getTimetableEventDate());
        timetableValues.put(TimetableEvent_name, timetableEvent.getTimetableEventName());
        timetableValues.put(TimetableEvent_description, timetableEvent.getTimetableEventDescription());
        timetableValues.put(TimetableEvent_place, timetableEvent.getTimetableEventPlace());
        timetableValues.put(TimetableEvent_startTime, timetableEvent.getTimetableEventStartTime());
        timetableValues.put(TimetableEvent_endTime, timetableEvent.getTimetableEventEndTime());

        int updateItem = database.update(TABLE_EVENTS, timetableValues, TimetableEvent_ID + " = ?", new String[] {String.valueOf(timetableEvent.getId())});

        database.close();

        return updateItem;
        }

}
