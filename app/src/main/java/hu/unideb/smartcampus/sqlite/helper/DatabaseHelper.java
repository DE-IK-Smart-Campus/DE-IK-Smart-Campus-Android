package hu.unideb.smartcampus.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.sqlite.model.TimetableEvent;
import hu.unideb.smartcampus.xmpp.Connection;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "smartcampus.db" + Connection.getInstance().getUserJID();

    public static final String TABLE_TIMETABLEEVENT = "timetableevent";
    public static final String TABLE_CUSTOMEVENT = "customevent";

    public static final String TIMETABLEEVENT_ID = "timetableeventID";
    public static final String TIMETABLEEVENT_DATE = "timetableeventDate";
    public static final String TIMETABLEEVENT_NAME = "timetableeventName";
    public static final String TIMETABLEEVENT_DESCRIPTION = "timetableeventDescription";
    public static final String TIMETABLEEVENT_PLACE = "timetableeventPlace";
    public static final String TIMETABLEEVENT_STARTTIME = "timetableeventStartTime";
    public static final String TIMETABLEEVENT_ENDTIME = "timetableeventEndTime";

    public static final String CUSTOMEVENT_UUID = "customeventUUID";
    public static final String CUSTOMEVENT_NAME = "customeventName";
    public static final String CUSTOMEVENT_DESCRIPTION = "customeventDescription";
    public static final String CUSTOMEVENT_PLACE = "customeventPlace";
    public static final String CUSTOMEVENT_STARTDATE = "customeventStartDate";
    public static final String CUSTOMEVENT_ENDDATE = "customeventEndDate";
    public static final String CUSTOMEVENT_STARTTIME= "customeventStartTime";
    public static final String CUSTOMEVENT_ENDTIME = "customeventEndTime";
    public static final String CUSTOMEVENT_REPEAT = "customeventRepeat";
    public static final String CUSTOMEVENT_REMAINDER = "customeventRemainder";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private static final String CREATE_TIMETABLEEVENT_TABLE = "CREATE TABLE " + TABLE_TIMETABLEEVENT + "(" + TIMETABLEEVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TIMETABLEEVENT_DATE + " LONG," + TIMETABLEEVENT_NAME + " TEXT," + TIMETABLEEVENT_DESCRIPTION + " TEXT," + TIMETABLEEVENT_PLACE + " TEXT," + TIMETABLEEVENT_STARTTIME + " LONG," + TIMETABLEEVENT_ENDTIME + " LONG" + ")";
    private static final String CREATE_CUSTOMEVENT_TABLE = "CREATE TABLE " + TABLE_CUSTOMEVENT + "(" + CUSTOMEVENT_UUID + " TEXT," + CUSTOMEVENT_NAME + " TEXT," + CUSTOMEVENT_DESCRIPTION + " TEXT," + CUSTOMEVENT_PLACE + " TEXT," + CUSTOMEVENT_STARTTIME + " LONG," + CUSTOMEVENT_STARTDATE + " LONG," + CUSTOMEVENT_ENDTIME + " LONG," + CUSTOMEVENT_ENDDATE + " LONG," + CUSTOMEVENT_REPEAT + " TEXT," + CUSTOMEVENT_REMAINDER + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TIMETABLEEVENT_TABLE);
        sqLiteDatabase.execSQL(CREATE_CUSTOMEVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLEEVENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMEVENT);
        onCreate(sqLiteDatabase);
    }
}
