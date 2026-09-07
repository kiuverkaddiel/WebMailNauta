package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface PlayListDb {
    public static final String TABLE_NAME = "play_list";

    public interface Columns extends BaseColumns {
        public static final String PLAY = "play";
        public static final String VALUE = "value";
    }
}
