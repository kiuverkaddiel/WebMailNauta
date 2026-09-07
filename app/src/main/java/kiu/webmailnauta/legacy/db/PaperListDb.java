package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface PaperListDb {
    public static final String TABLE_NAME = "row_list";
    public static final String TABLE_NAME1 = "row_list1";

    public interface Columns extends BaseColumns {
        public static final String LIST_NUMBER = "list_number";
        public static final String LIST_PLAY = "list_play";
        public static final String LIST_STRING = "list_string";
        public static final String LIST_TURN = "list_turn";
    }
}
