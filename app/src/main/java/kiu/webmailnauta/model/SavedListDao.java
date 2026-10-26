package kiu.webmailnauta.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SavedListDao {

    private final AppDataDbHelper dbHelper;

    public SavedListDao(Context context) {
        this.dbHelper = new AppDataDbHelper(context);
    }

    /**
     * Stores the subject/turn pair of a saved message. Re-saving the same
     * pair simply replaces the existing row.
     */
    public void save(String subject, String turn) {
        if (subject == null || subject.isEmpty() || turn == null || turn.isEmpty()) return;

        ContentValues values = new ContentValues();
        values.put(AppDataDbHelper.COLUMN_SUBJECT, subject);
        values.put(AppDataDbHelper.COLUMN_TURN, turn);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.insertWithOnConflict(AppDataDbHelper.TABLE_SAVED_LIST, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    /**
     * Checks whether a message with the given subject has already been
     * saved, regardless of its turn.
     */
    public boolean isSubjectSaved(String subject) {
        if (subject == null || subject.isEmpty()) return false;

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(AppDataDbHelper.TABLE_SAVED_LIST,
                     new String[]{AppDataDbHelper.COLUMN_SUBJECT},
                     AppDataDbHelper.COLUMN_SUBJECT + " = ?",
                     new String[]{subject},
                     null, null, null, "1")) {
            return cursor.getCount() > 0;
        }
    }
}
