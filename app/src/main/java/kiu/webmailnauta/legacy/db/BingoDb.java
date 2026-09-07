package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface BingoDb {
    public static final String TABLE_NAME = "bingo";

    public interface Columns extends BaseColumns {
        public static final String CENTENE = "centene";
        public static final String CORRIDO1 = "corrido1";
        public static final String CORRIDO2 = "corrido2";
        public static final String FIJO = "fijo";
    }
}
