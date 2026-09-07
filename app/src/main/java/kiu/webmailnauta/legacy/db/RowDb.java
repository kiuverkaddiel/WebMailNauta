package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface RowDb {
    public static final String TABLE_NAME = "row";

    public interface Columns extends BaseColumns {
        public static final String BATCH_ID = "batch_id";
        public static final String CREATED_AT = "created_at";
        public static final String DATA = "data";
        public static final String DELETED = "deleted";
        public static final String RECEIVED = "received";
        public static final String SENT = "sent";
        public static final String TYPE = "type";
    }

    String getData();

    long getId();

    int getType();
}
