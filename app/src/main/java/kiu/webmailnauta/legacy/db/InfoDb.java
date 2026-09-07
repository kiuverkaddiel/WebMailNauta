package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface InfoDb {
    public static final String TABLE_NAME = "info";

    public interface Columns extends BaseColumns {
        public static final String FIELD = "field";
        public static final String VALUE = "value";
    }
}
