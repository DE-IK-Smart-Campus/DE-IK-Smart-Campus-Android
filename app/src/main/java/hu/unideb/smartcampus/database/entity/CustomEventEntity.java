package hu.unideb.smartcampus.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_DESCRIPTION;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_END_DATE;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_END_TIME;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_NAME;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_PLACE;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_REMINDER;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_REPEAT;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_START_DATE;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_START_TIME;
import static hu.unideb.smartcampus.database.table.ColumName.CustomEventColumnName.COLUMN_NAME_CUSTOM_EVENT_UUID;
import static hu.unideb.smartcampus.database.table.TableName.CUSTOM_EVENT_TABLE_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = CUSTOM_EVENT_TABLE_NAME)
public class CustomEventEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_UUID)
    private String uuid;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_NAME)
    private String eventName;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_DESCRIPTION)
    private String eventDescription;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_PLACE)
    private String eventPlace;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_START_DATE)
    private Long eventStartDate;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_START_TIME)
    private Long eventStartTime;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_END_DATE)
    private Long eventEndDate;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_END_TIME)
    private Long eventEndTime;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_REPEAT)
    private String eventRepeat;

    @ColumnInfo(name = COLUMN_NAME_CUSTOM_EVENT_REMINDER)
    private String eventReminder;
}
