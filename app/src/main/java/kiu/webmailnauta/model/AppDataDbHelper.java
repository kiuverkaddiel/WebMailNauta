package kiu.webmailnauta.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDataDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AppData";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SAVED_LIST = "saved_list";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_TURN = "turn";

    public AppDataDbHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SAVED_LIST + " (" +
                COLUMN_SUBJECT + " TEXT NOT NULL, " +
                COLUMN_TURN + " TEXT NOT NULL, " +
                "PRIMARY KEY (" + COLUMN_SUBJECT + ", " + COLUMN_TURN + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_LIST);
        onCreate(db);
    }
}
