package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public interface ComputeDb {
    public static final String TABLE_NAME = "cumpute";

    public interface Columns extends BaseColumns {
        public static final String BINGO = "bingo";
        public static final String BRUTO = "bruto";
        public static final String BRUTO_BOTE = "bruto_bote";
        public static final String BRUTO_TOTAL = "bruto_total";
        public static final String LIMPIO = "limpio";
        public static final String LIMPIO_BOTE = "limpio_bote";
        public static final String LIMPIO_TOTAL = "limpio_total";
        public static final String LOSE = "lose";
        public static final String LOSE_BOTE = "lose_bote";
        public static final String LOSE_TOTAL = "lose_total";
        public static final String PASS = "pass";
        public static final String PASS_BOTE = "pass_bote";
        public static final String PRIZE = "prize";
        public static final String PRIZE_BOTE = "prize_bote";
        public static final String PRIZE_TOTAL = "prize_total";
        public static final String TURN = "turn";
        public static final String WIN = "win";
        public static final String WIN_BOTE = "win_bote";
        public static final String WIN_TOTAL = "win_total";
    }
}
