package hu.unideb.smartcampus.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.unideb.smartcampus.database.entity.TimetableEventEntity;

import static hu.unideb.smartcampus.database.table.TableName.TIMETABLE_EVENT_TABLE_NAME;

@Dao
public interface TimetableEventDao {

    @Query("SELECT * FROM " + TIMETABLE_EVENT_TABLE_NAME)
    List<TimetableEventEntity> getAllTimetableEvent();

    @Insert
    void insertAll(TimetableEventEntity... timetableEventEntities);
}
