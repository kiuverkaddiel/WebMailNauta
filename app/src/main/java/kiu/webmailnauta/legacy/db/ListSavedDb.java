package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface ListSavedDb {
    public static final String TABLE_NAME = "list_saved";

    public interface Columns extends BaseColumns {
        public static final String STATE = "state";
        public static final String UUID = "uuid";
    }
}
