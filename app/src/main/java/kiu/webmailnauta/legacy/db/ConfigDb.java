package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface ConfigDb {
    public static final String TABLE_NAME = "config";

    public interface Columns extends BaseColumns {
        public static final String KEY = "key";
        public static final String VALUE = "value";
    }
}
