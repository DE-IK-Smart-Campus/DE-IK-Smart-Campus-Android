package hu.unideb.smartcampus.database.table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Table name provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TableName {

    /**
     * TimetableEvent name.
     */
    public static final String TIMETABLE_EVENT_TABLE_NAME = "timetable_event_table";

    /**
     * CustomEvent name.
     */
    public static final String CUSTOM_EVENT_TABLE_NAME = "custom_event_table";

}
