package hu.unideb.smartcampus.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "smartcampus.db";

    public static final String TABLE_TIMETABLEEVENT = "timetableevent";
    public static final String TABLE_CUSTOMEVENT = "customevent";
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_INSTRUCTORS = "instructors";
    public static final String TABLE_OFFICE_HOURS = "officehours";

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
    public static final String CUSTOMEVENT_STARTTIME = "customeventStartTime";
    public static final String CUSTOMEVENT_ENDTIME = "customeventEndTime";
    public static final String CUSTOMEVENT_REPEAT = "customeventRepeat";
    public static final String CUSTOMEVENT_REMAINDER = "customeventRemainder";


    public static final String INSTRUCTOR_ID_PK = "INSTRUCTORID";
    public static final String SUBJECTS_NAME_COL = "name";
    public static final String INSTURCORS_NAME_COL = "name";
    public static final String SUBJECT_ID_PK = "SUBJECTID";

    public static final String OFFICE_HOUR_ID_PK = "officehourid";
    public static final String OFFICE_HOUR_FROM = "officehourfrom";
    public static final String OFFICE_HOUR_TO = "officehourto";
    public static final String OFFICE_HOUR_RESERVED_SUM = "reservedsum";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TIMETABLEEVENT_TABLE = "CREATE TABLE " + TABLE_TIMETABLEEVENT + "(" + TIMETABLEEVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TIMETABLEEVENT_DATE + " LONG," + TIMETABLEEVENT_NAME + " TEXT," + TIMETABLEEVENT_DESCRIPTION + " TEXT," + TIMETABLEEVENT_PLACE + " TEXT," + TIMETABLEEVENT_STARTTIME + " LONG," + TIMETABLEEVENT_ENDTIME + " LONG" + ")";
    private static final String CREATE_CUSTOMEVENT_TABLE = "CREATE TABLE " + TABLE_CUSTOMEVENT + "(" + CUSTOMEVENT_UUID + " TEXT," + CUSTOMEVENT_NAME + " TEXT," + CUSTOMEVENT_DESCRIPTION + " TEXT," + CUSTOMEVENT_PLACE + " TEXT," + CUSTOMEVENT_STARTTIME + " LONG," + CUSTOMEVENT_STARTDATE + " LONG," + CUSTOMEVENT_ENDTIME + " LONG," + CUSTOMEVENT_ENDDATE + " LONG," + CUSTOMEVENT_REPEAT + " TEXT," + CUSTOMEVENT_REMAINDER + " TEXT" + ")";


    private static final String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "(" +
            SUBJECT_ID_PK + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SUBJECTS_NAME_COL + " TEXT NOT NULL)";

    private static final String CREATE_INSTRUCTORS_TABLE = "CREATE TABLE " + TABLE_INSTRUCTORS + "(" +
            INSTRUCTOR_ID_PK + " LONG PRIMARY KEY NOT NULL," +
            INSTURCORS_NAME_COL + " TEXT NOT NULL," +
            SUBJECT_ID_PK + " LONG NOT NULL," +
            "FOREIGN KEY(" + SUBJECT_ID_PK + ") REFERENCES " + TABLE_SUBJECTS + "(" + SUBJECT_ID_PK + "))";

    private static final String CREATE_OFFICE_HOURS_TABLE = "CREATE TABLE " + TABLE_OFFICE_HOURS + "(" +
            OFFICE_HOUR_ID_PK + " LONG PRIMARY KEY NOT NULL," +
            OFFICE_HOUR_FROM + " LONG NOT NULL," +
            OFFICE_HOUR_TO + " LONG NOT NULL," +
            OFFICE_HOUR_RESERVED_SUM + " LONG NOT NULL," +
            INSTRUCTOR_ID_PK + " LONG NOT NULL," +
            "FOREIGN KEY(" + INSTRUCTOR_ID_PK + ") REFERENCES " + TABLE_INSTRUCTORS + "(" + INSTRUCTOR_ID_PK + "))";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TIMETABLEEVENT_TABLE);
        sqLiteDatabase.execSQL(CREATE_CUSTOMEVENT_TABLE);
        //sqLiteDatabase.execSQL(CREATE_SUBJECTS_TABLE);
        //sqLiteDatabase.execSQL(CREATE_INSTRUCTORS_TABLE);
        //sqLiteDatabase.execSQL(CREATE_OFFICE_HOURS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLEEVENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMEVENT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTORS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFICE_HOURS);
        onCreate(sqLiteDatabase);
    }
}
