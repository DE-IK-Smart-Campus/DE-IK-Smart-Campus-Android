package hu.unideb.smartcampus.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import hu.unideb.smartcampus.sqlite.models.TimetableEvent;

public class DatebaseHelper extends SQLiteOpenHelper {

    private static final int DATEBASE_VERSION = 1;

    // DB name
    private static final String DATEBASE_NAME = "smartcampusDB";

    //TABLE names
    private static final String TABLE_TIMETABLEEVENT = "timetableevent";
    private static final String TABLE_CUSTOMEVENT = "customevent";

    //timetable vent colums
    private static final String TimetableEvent_ID = "id";
    private static final String TimetableEvent_date = "timetableDate";
    private static final String TimetableEvent_name = "timetableName";
    private static final String TimetableEvent_description = "timetableDescription";
    private static final String TimetableEvent_place = "timetablePlace";
    private static final String TimetableEvent_startTime = "timetableStartTime";
    private static final String TimetableEvent_endTime = "timetableEndTime";

    //customevent colums

    private static final String CREATE_TABLE_TIMETABLEEVENT = "CREATE TABLE " + TABLE_TIMETABLEEVENT + "(" + TimetableEvent_ID + " INTEGER PRIMARY KEY," + TimetableEvent_date + " LONG," + TimetableEvent_name + " TEXT," + TimetableEvent_description + " TEXT," + TimetableEvent_place + " TEXT," + TimetableEvent_startTime + " LONG," + TimetableEvent_endTime + " LONG" + ")";

    public DatebaseHelper(Context context){
        super(context, DATEBASE_NAME, null, DATEBASE_VERSION);
    }

    public void createTimetableEvent(TimetableEvent event) {
        SQLiteDatabase timetableEventDB = this.getWritableDatabase();

        ContentValues timetableValues = new ContentValues();
        timetableValues.put(TimetableEvent_date, event.getTimetableEventDate());
        timetableValues.put(TimetableEvent_name, event.getTimetableEventName());
        timetableValues.put(TimetableEvent_description, event.getTimetableEventDescription());
        timetableValues.put(TimetableEvent_place, event.getTimetableEventPlace());
        timetableValues.put(TimetableEvent_startTime, event.getTimetableEventStartTime());
        timetableValues.put(TimetableEvent_endTime, event.getTimetableEventEndTime());

        timetableEventDB.insert(TABLE_TIMETABLEEVENT, null, timetableValues);
        timetableEventDB.close();
    }

    public List<TimetableEvent> getTimetableEventDate(Long date) {
        List<TimetableEvent> timetableEvents = new LinkedList<>();
        String query = "SELECT * FROM " + TABLE_TIMETABLEEVENT + " WHERE " + "timetableDate = " + date;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        TimetableEvent timetableEvent;
        if (cursor.moveToFirst()) {
            do {
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
        return timetableEvents;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_TIMETABLEEVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLEEVENT);
        onCreate(sqLiteDatabase);
    }
}
