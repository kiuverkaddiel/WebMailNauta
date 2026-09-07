package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface EmailDb {
    public static final String TABLE_NAME = "email";

    public interface Columns extends BaseColumns {
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }
}
