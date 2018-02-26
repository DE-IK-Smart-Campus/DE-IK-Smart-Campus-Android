package hu.unideb.smartcampus.database.utils;

import hu.unideb.smartcampus.database.AppDatabase;
import hu.unideb.smartcampus.database.converter.CustomEventToCustomEventEntity;
import hu.unideb.smartcampus.database.converter.TimetableEventToTimetableEventEntity;
import hu.unideb.smartcampus.pojo.calendar.CustomEvent;
import hu.unideb.smartcampus.pojo.calendar.TimetableEvent;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getSimpleName();

    private static TimetableEvent addTimetableEvent(final AppDatabase appDatabase, TimetableEvent timetableEvent) {
        appDatabase.timetableEventDao().insertAll(TimetableEventToTimetableEventEntity.convert(timetableEvent));
        return timetableEvent;
    }

    private static CustomEvent addCustomEvent(final AppDatabase appDatabase, CustomEvent customEvent){
        appDatabase.customEventDao().insertAll(CustomEventToCustomEventEntity.convert(customEvent));
        return customEvent;
    }

    private static void deleteCustomEvent(final AppDatabase appDatabase, CustomEvent customEvent){
        appDatabase.customEventDao().delete(CustomEventToCustomEventEntity.convert(customEvent));
    }

}
