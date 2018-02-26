package hu.unideb.smartcampus.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static hu.unideb.smartcampus.database.table.ColumName.TimetableEventColumnName.COLUMN_NAME_TIMETABLE_EVENT_DATE;
import static hu.unideb.smartcampus.database.table.ColumName.TimetableEventColumnName.COLUMN_NAME_TIMETABLE_EVENT_DESCRIPTION;
import static hu.unideb.smartcampus.database.table.ColumName.TimetableEventColumnName.COLUMN_NAME_TIMETABLE_EVENT_END_TIME;
import static hu.unideb.smartcampus.database.table.ColumName.TimetableEventColumnName.COLUMN_NAME_TIMETABLE_EVENT_NAME;
import static hu.unideb.smartcampus.database.table.ColumName.TimetableEventColumnName.COLUMN_NAME_TIMETABLE_EVENT_PLACE;
import static hu.unideb.smartcampus.database.table.ColumName.TimetableEventColumnName.COLUMN_NAME_TIMETABLE_EVENT_START_TIME;
import static hu.unideb.smartcampus.database.table.TableName.TIMETABLE_EVENT_TABLE_NAME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = TIMETABLE_EVENT_TABLE_NAME)
public class TimetableEventEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = COLUMN_NAME_TIMETABLE_EVENT_DATE)
    private Long timetableEventDate;

    @ColumnInfo(name = COLUMN_NAME_TIMETABLE_EVENT_NAME)
    private String timetableEventName;

    @ColumnInfo(name = COLUMN_NAME_TIMETABLE_EVENT_DESCRIPTION)
    private String timetableEventDescription;

    @ColumnInfo(name = COLUMN_NAME_TIMETABLE_EVENT_PLACE)
    private String timetableEventPlace;

    @ColumnInfo(name = COLUMN_NAME_TIMETABLE_EVENT_START_TIME)
    private Long timetableEventStartTime;

    @ColumnInfo(name = COLUMN_NAME_TIMETABLE_EVENT_END_TIME)
    private Long timetableEventEndTime;
}
