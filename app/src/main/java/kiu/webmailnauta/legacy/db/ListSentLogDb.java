package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface ListSentLogDb {
    public static final String TABLE_NAME = "mail_log";

    public interface Column extends BaseColumns {
        public static final String LOG = "log_column";
        public static final String TIME = "time_column";
        public static final String TURN = "turn_column";
    }
}
