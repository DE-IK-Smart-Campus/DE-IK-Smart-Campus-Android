package hu.unideb.smartcampus.database.table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Column name provider.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColumName {

    /**
     * TimetableEvent column name.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class TimetableEventColumnName {

        /**
         * Event date column.
         */
        public static final String COLUMN_NAME_TIMETABLE_EVENT_DATE = "timetable_event_date";

        /**
         * Event date column.
         */
        public static final String COLUMN_NAME_TIMETABLE_EVENT_NAME = "timetable_event_name";

        /**
         * Event description column.
         */
        public static final String COLUMN_NAME_TIMETABLE_EVENT_DESCRIPTION = "timetable_event_description";

        /**
         * Event place column.
         */
        public static final String COLUMN_NAME_TIMETABLE_EVENT_PLACE = "timetable_event_place";

        /**
         * Event start time column.
         */
        public static final String COLUMN_NAME_TIMETABLE_EVENT_START_TIME = "timetable_event_start_time";

        /**
         * Event end time column.
         */
        public static final String COLUMN_NAME_TIMETABLE_EVENT_END_TIME = "timetable_event_end_time";

    }

    /**
     * CustomEvent column name.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CustomEventColumnName {

        /**
         * Event uuid column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_UUID = "custom_event_uuid";

        /**
         * Event name column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_NAME = "custom_event_name";

        /**
         * Event description column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_DESCRIPTION = "custom_event_description";

        /**
         * Event place column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_PLACE = "custom_event_place";

        /**
         * Event start date column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_START_DATE = "custom_event_start_date";

        /**
         * Event start time column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_START_TIME = "custom_event_start_time";

        /**
         * Event end date column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_END_DATE = "custom_event_end_date";

        /**
         * Event end time column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_END_TIME = "custom_event_end_time";

        /**
         * Event repeat column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_REPEAT = "custom_event_repeat";

        /**
         * Event reminder column.
         */
        public static final String COLUMN_NAME_CUSTOM_EVENT_REMINDER = "custom_event_reminder";
    }
}
