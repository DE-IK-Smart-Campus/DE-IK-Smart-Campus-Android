package hu.unideb.smartcampus.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import hu.unideb.smartcampus.database.dao.CustomEventDao;
import hu.unideb.smartcampus.database.dao.TimetableEventDao;
import hu.unideb.smartcampus.database.entity.CustomEventEntity;
import hu.unideb.smartcampus.database.entity.TimetableEventEntity;

@Database(entities = {TimetableEventEntity.class, CustomEventEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TimetableEventDao timetableEventDao();

    public abstract CustomEventDao customEventDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "smart-campus-db").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
