package hu.unideb.smartcampus.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hu.unideb.smartcampus.database.entity.CustomEventEntity;

import static hu.unideb.smartcampus.database.table.TableName.CUSTOM_EVENT_TABLE_NAME;

@Dao
public interface CustomEventDao {

    @Query("SELECT * FROM " + CUSTOM_EVENT_TABLE_NAME)
    List<CustomEventEntity> getAllCustomEvent();

    @Insert
    void insertAll(CustomEventEntity... customEventEntities);

    @Delete
    void delete(CustomEventEntity customEventEntity);
}