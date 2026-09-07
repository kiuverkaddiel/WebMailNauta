package kiu.webmailnauta.legacy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kiu.webmailnauta.legacy.Bingo;
import kiu.webmailnauta.legacy.Config;
import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.ConstantsParserRowList;
import kiu.webmailnauta.legacy.PaperList;
import kiu.webmailnauta.legacy.Row;
import kiu.webmailnauta.legacy.utils.MyCipher;
import kiu.webmailnauta.legacy.utils.MyTime;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String COMPILED_VERSION = "160115-12:00";
    public static final String DATABASE_NAME = "test.db";
    public static final int DATABASE_VERSION = 16;
    public static final String FIELD_COMPILED_VERSION = "compiled_version";
    public static final String FIELD_DB_VERSION = "db_version";
    public static final String FIELD_INSTALLED_DATE = "installed_date";
    public static final String FIELD_UPDATED_DATE = "updated_date";
    private final String CREATE_TABLE = "CREATE TABLE ";
    private final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT, ";
    private final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY, ";
    private final String LONG = " LONG ";
    private final String INTEGER = " INTEGER ";
    private final String TEXT = " TEXT ";
    private final String NOT_NULL = " NOT NULL";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 16);
    }

    public void onCreate(SQLiteDatabase db) {

        StringBuilder query = new StringBuilder();

        query.append(CREATE_TABLE).append(RowDb.TABLE_NAME).append(" (")
                .append(RowDb.Columns._ID).append(INTEGER_PRIMARY_KEY_AUTOINCREMENT)
                .append(RowDb.Columns.CREATED_AT).append(LONG).append(NOT_NULL).append(", ")
                .append(RowDb.Columns.BATCH_ID).append(TEXT).append(", ")
                .append(RowDb.Columns.DATA).append(TEXT).append(NOT_NULL).append(", ")
                .append(RowDb.Columns.TYPE).append(INTEGER).append(NOT_NULL).append(", ")
                .append(RowDb.Columns.DELETED).append(TEXT).append(", ")
                .append(RowDb.Columns.RECEIVED).append(TEXT).append(", ")
                .append(RowDb.Columns.SENT).append(TEXT)
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(PaperListDb.TABLE_NAME).append(" (")
                .append(UserTable.Columns._ID).append(" LONG PRIMARY KEY, ")
                .append(PaperListDb.Columns.LIST_STRING).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(PaperListDb.TABLE_NAME1).append(" (")
                .append(PaperListDb.Columns._ID).append(" LONG PRIMARY KEY, ")
                .append(PaperListDb.Columns.LIST_PLAY).append(" TEXT NOT NULL, ")
                .append(PaperListDb.Columns.LIST_TURN).append(" TEXT NOT NULL, ")
                .append(PaperListDb.Columns.LIST_NUMBER).append(" TEXT NOT NULL, ")
                .append(PaperListDb.Columns.LIST_STRING).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(BingoDb.TABLE_NAME).append(" (")
                .append(BingoDb.Columns._ID).append(" LONG PRIMARY KEY, ")
                .append(BingoDb.Columns.CENTENE).append(" TEXT, ")
                .append(BingoDb.Columns.FIJO).append(" TEXT NOT NULL, ")
                .append(BingoDb.Columns.CORRIDO1).append(" TEXT NOT NULL, ")
                .append(BingoDb.Columns.CORRIDO2).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(ConfigDb.TABLE_NAME).append(" (")
                .append(ConfigDb.Columns._ID).append(" INTEGER PRIMARY KEY, ")
                .append(ConfigDb.Columns.VALUE).append(" TEXT NOT NULL, ")
                .append(ConfigDb.Columns.KEY).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(PlayListDb.TABLE_NAME).append(" (")
                .append(PlayListDb.Columns._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(PlayListDb.Columns.PLAY).append(" TEXT NOT NULL, ")
                .append(PlayListDb.Columns.VALUE).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(MarkSaveDb.TABLE_NAME).append(" (")
                .append(MarkSaveDb.Columns._ID).append(" LONG PRIMARY KEY")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(InfoDb.TABLE_NAME).append(" (")
                .append(InfoDb.Columns._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(InfoDb.Columns.FIELD).append(" TEXT NOT NULL, ")
                .append(InfoDb.Columns.VALUE).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(EmailDb.TABLE_NAME).append(" (")
                .append(EmailDb.Columns.EMAIL).append(" TEXT NOT NULL, ")
                .append(EmailDb.Columns.PASSWORD).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(ListSavedDb.TABLE_NAME).append(" (")
                .append(ListSavedDb.Columns._ID).append(" LONG NOT NULL, ")
                .append(ListSavedDb.Columns.STATE).append(" TEXT NOT NULL, ")
                .append(ListSavedDb.Columns.UUID).append(" TEXT")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(ListSentLogDb.TABLE_NAME).append(" (")
                .append(UserTable.Columns._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ListSentLogDb.Column.TURN).append(" TEXT NOT NULL, ")
                .append(ListSentLogDb.Column.TIME).append(" TEXT NOT NULL, ")
                .append(ListSentLogDb.Column.LOG).append(" TEXT NOT NULL")
                .append(");");
        db.execSQL(query.toString());

        query = new StringBuilder();
        query.append("CREATE TABLE ").append(ComputeDb.TABLE_NAME).append(" (")
                .append(ComputeDb.Columns.TURN).append(" TEXT NOT NULL, ")
                .append(ComputeDb.Columns.BINGO).append(" TEXT NOT NULL, ")
                .append(ComputeDb.Columns.BRUTO).append(" TEXT NOT NULL, ")
                .append(ComputeDb.Columns.LIMPIO).append(" TEXT NOT NULL, ")
                .append(ComputeDb.Columns.PRIZE).append(" TEXT, ")
                .append(ComputeDb.Columns.WIN).append(" TEXT, ")
                .append(ComputeDb.Columns.LOSE).append(" TEXT, ")
                .append(ComputeDb.Columns.PASS).append(" TEXT, ")
                .append(ComputeDb.Columns.BRUTO_BOTE).append(" TEXT, ")
                .append(ComputeDb.Columns.LIMPIO_BOTE).append(" TEXT, ")
                .append(ComputeDb.Columns.PRIZE_BOTE).append(" TEXT, ")
                .append(ComputeDb.Columns.WIN_BOTE).append(" TEXT, ")
                .append(ComputeDb.Columns.LOSE_BOTE).append(" TEXT, ")
                .append(ComputeDb.Columns.PASS_BOTE).append(" TEXT, ")
                .append(ComputeDb.Columns.BRUTO_TOTAL).append(" TEXT, ")
                .append(ComputeDb.Columns.LIMPIO_TOTAL).append(" TEXT, ")
                .append(ComputeDb.Columns.PRIZE_TOTAL).append(" TEXT, ")
                .append(ComputeDb.Columns.WIN_TOTAL).append(" TEXT, ")
                .append(ComputeDb.Columns.LOSE_TOTAL).append(" TEXT")
                .append(");");
        db.execSQL(query.toString());

        db.execSQL(new SqliteHelper(new UserTable()).getCreateSql());

        MyTime.setCalendar(System.currentTimeMillis());
        String installedDate = MyTime.generateShortDate();
        MyTime.releaseCalendar();

        ContentValues cv = new ContentValues();
        cv.put(InfoDb.Columns.FIELD, FIELD_INSTALLED_DATE);
        cv.put(InfoDb.Columns.VALUE, installedDate);
        db.insert(InfoDb.TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(InfoDb.Columns.FIELD, FIELD_COMPILED_VERSION);
        cv.put(InfoDb.Columns.VALUE, COMPILED_VERSION);
        db.insert(InfoDb.TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(InfoDb.Columns.FIELD, FIELD_DB_VERSION);
        cv.put(InfoDb.Columns.VALUE, "v16");
        db.insert(InfoDb.TABLE_NAME, null, cv);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new StringBuilder();
        db.execSQL("DROP TABLE IF EXISTS cumpute");
        db.execSQL("DROP TABLE IF EXISTS mail_log");
        db.execSQL("CREATE TABLE " + ComputeDb.TABLE_NAME + " (" + ComputeDb.Columns.TURN + " TEXT NOT NULL, " + "bingo" + " TEXT NOT NULL, " + ComputeDb.Columns.BRUTO + " TEXT NOT NULL, " + ComputeDb.Columns.LIMPIO + " TEXT NOT NULL, " + ComputeDb.Columns.PRIZE + " TEXT, " + ComputeDb.Columns.WIN + " TEXT, " + ComputeDb.Columns.LOSE + " TEXT, " + ComputeDb.Columns.PASS + " TEXT, " + ComputeDb.Columns.BRUTO_BOTE + " TEXT, " + ComputeDb.Columns.LIMPIO_BOTE + " TEXT, " + ComputeDb.Columns.PRIZE_BOTE + " TEXT, " + ComputeDb.Columns.WIN_BOTE + " TEXT, " + ComputeDb.Columns.LOSE_BOTE + " TEXT, " + ComputeDb.Columns.PASS_BOTE + " TEXT, " + ComputeDb.Columns.BRUTO_TOTAL + " TEXT, " + ComputeDb.Columns.LIMPIO_TOTAL + " TEXT, " + ComputeDb.Columns.PRIZE_TOTAL + " TEXT, " + ComputeDb.Columns.WIN_TOTAL + " TEXT, " + ComputeDb.Columns.LOSE_TOTAL + " TEXT" + ");");
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE ");
        query.append(ListSentLogDb.TABLE_NAME);
        query.append(" (");
        query.append(UserTable.Columns._ID);
        query.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        query.append(ListSentLogDb.Column.TURN);
        query.append(" TEXT NOT NULL, ");
        query.append(ListSentLogDb.Column.TIME);
        query.append(" TEXT NOT NULL, ");
        query.append(ListSentLogDb.Column.LOG);
        query.append(" TEXT NOT NULL");
        query.append(");");
        db.execSQL(query.toString());
    }

    public boolean saveUser(String user, String password) {
        if (existUser()) {
            return updateUser(user, password);
        }
        return insertUser(user, password);
    }

    private boolean existUser() {
        return getUser() != null;
    }

    public ContentValues getUser() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"user", "password"}, "_id = 1", (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            ContentValues values = new ContentValues();
            values.put("user", cursor.getString(0));
            values.put("password", cursor.getString(1));
            db.close();
            cursor.close();
            return values;
        }
    }

    private boolean updateUser(String user, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("password", password);
        int result = db.update("user", values, "_id = 1", (String[]) null);
        db.close();
        return result > 0;
    }

    private boolean insertUser(String user, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.Columns._ID, 1);
        values.put("user", user);
        values.put("password", password);
        long id = db.insert("user", (String) null, values);
        db.close();
        if (id != -1) {
            return true;
        }
        return false;
    }

    public String[] getStringArrayPay(String turn, String[] select) {
        String where = "{1} = '{2}'".replace("{1}", ComputeDb.Columns.TURN).replace("{2}", turn);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(ComputeDb.TABLE_NAME, select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return null;
        } else {
            String[] pay = new String[select.length];
            cursor.moveToFirst();
            for (int i = 0; i < select.length; i++) {
                pay[i] = cursor.getString(i);
            }
            db.close();
            cursor.close();
            return pay;
        }
    }

    public String[] getStringArrayPay(String turn) {
        return getStringArrayPay(turn, new String[]{ComputeDb.Columns.BRUTO, ComputeDb.Columns.LIMPIO, ComputeDb.Columns.PRIZE, ComputeDb.Columns.WIN, ComputeDb.Columns.LOSE});
    }

    public String[] getStringArrayPayBote(String turn) {
        return getStringArrayPay(turn, new String[]{ComputeDb.Columns.BRUTO_BOTE, ComputeDb.Columns.LIMPIO_BOTE, ComputeDb.Columns.PRIZE_BOTE, ComputeDb.Columns.WIN_BOTE, ComputeDb.Columns.LOSE_BOTE, ComputeDb.Columns.BRUTO_TOTAL, ComputeDb.Columns.LIMPIO_TOTAL, ComputeDb.Columns.PRIZE_TOTAL, ComputeDb.Columns.WIN_TOTAL, ComputeDb.Columns.LOSE_TOTAL});
    }

    public String getStringPass(String turn, String columnPass) {
        String where = "{1} = '{2}'".replace("{1}", ComputeDb.Columns.TURN).replace("{2}", turn);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(ComputeDb.TABLE_NAME, new String[]{columnPass}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            String pay = cursor.getString(0);
            db.close();
            cursor.close();
            return pay;
        }
    }

    public String getStringPass(String turn) {
        return getStringPass(turn, ComputeDb.Columns.PASS);
    }

    public String getStringPassBote(String turn) {
        return getStringPass(turn, ComputeDb.Columns.PASS_BOTE);
    }

    public String getComputeBingo(String turn) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = db;
        Cursor cursor = sQLiteDatabase.query(ComputeDb.TABLE_NAME, new String[]{"bingo"}, "{1} = '{2}'".replace("{1}", ComputeDb.Columns.TURN).replace("{2}", turn), (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            String bingo = cursor.getString(0);
            db.close();
            cursor.close();
            return bingo;
        }
    }

    public boolean existCompute(String turn) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase sQLiteDatabase = db;
        Cursor cursor = sQLiteDatabase.query(ComputeDb.TABLE_NAME, new String[]{"*"}, "{1} = '{2}'".replace("{1}", ComputeDb.Columns.TURN).replace("{2}", turn), (String[]) null, (String) null, (String) null, (String) null);
        boolean exist = false;
        if (cursor == null) {
            db.close();
            return false;
        }
        if (cursor.getCount() > 0) {
            exist = true;
        }
        db.close();
        cursor.close();
        return exist;
    }

    public void insertPayString(String turn, String bingo, String pass, String passBote, String[] arrayStringPay, String[] arrayStringPayBote) {
        ContentValues vr = new ContentValues();
        vr.put(ComputeDb.Columns.TURN, turn);
        vr.put("bingo", bingo);
        vr.put(ComputeDb.Columns.PASS, pass);
        vr.put(ComputeDb.Columns.PASS_BOTE, passBote);
        vr.put(ComputeDb.Columns.BRUTO, arrayStringPay[0]);
        vr.put(ComputeDb.Columns.LIMPIO, arrayStringPay[1]);
        vr.put(ComputeDb.Columns.PRIZE, arrayStringPay[2]);
        vr.put(ComputeDb.Columns.WIN, arrayStringPay[3]);
        vr.put(ComputeDb.Columns.LOSE, arrayStringPay[4]);
        if (arrayStringPayBote != null) {
            vr.put(ComputeDb.Columns.BRUTO_BOTE, arrayStringPayBote[0]);
            vr.put(ComputeDb.Columns.LIMPIO_BOTE, arrayStringPayBote[1]);
            vr.put(ComputeDb.Columns.PRIZE_BOTE, arrayStringPayBote[2]);
            vr.put(ComputeDb.Columns.WIN_BOTE, arrayStringPayBote[3]);
            vr.put(ComputeDb.Columns.LOSE_BOTE, arrayStringPayBote[4]);
            vr.put(ComputeDb.Columns.BRUTO_TOTAL, arrayStringPayBote[5]);
            vr.put(ComputeDb.Columns.LIMPIO_TOTAL, arrayStringPayBote[6]);
            vr.put(ComputeDb.Columns.PRIZE_TOTAL, arrayStringPayBote[7]);
            vr.put(ComputeDb.Columns.WIN_TOTAL, arrayStringPayBote[8]);
            vr.put(ComputeDb.Columns.LOSE_TOTAL, arrayStringPayBote[9]);
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ComputeDb.TABLE_NAME, (String) null, vr);
        db.close();
    }

    public void updatePayString(String turn, String bingo, String pass, String passBote, String[] arrayStringPay, String[] arrayStringPayBote) {
        String where = "{1} = '{2}'".replace("{1}", ComputeDb.Columns.TURN).replace("{2}", turn);
        ContentValues vr = new ContentValues();
        vr.put("bingo", bingo);
        vr.put(ComputeDb.Columns.PASS, pass);
        vr.put(ComputeDb.Columns.PASS_BOTE, passBote);
        vr.put(ComputeDb.Columns.BRUTO, arrayStringPay[0]);
        vr.put(ComputeDb.Columns.LIMPIO, arrayStringPay[1]);
        vr.put(ComputeDb.Columns.PRIZE, arrayStringPay[2]);
        vr.put(ComputeDb.Columns.WIN, arrayStringPay[3]);
        vr.put(ComputeDb.Columns.LOSE, arrayStringPay[4]);
        if (arrayStringPayBote != null) {
            vr.put(ComputeDb.Columns.BRUTO_BOTE, arrayStringPayBote[0]);
            vr.put(ComputeDb.Columns.LIMPIO_BOTE, arrayStringPayBote[1]);
            vr.put(ComputeDb.Columns.PRIZE_BOTE, arrayStringPayBote[2]);
            vr.put(ComputeDb.Columns.WIN_BOTE, arrayStringPayBote[3]);
            vr.put(ComputeDb.Columns.LOSE_BOTE, arrayStringPayBote[4]);
            vr.put(ComputeDb.Columns.BRUTO_TOTAL, arrayStringPayBote[5]);
            vr.put(ComputeDb.Columns.LIMPIO_TOTAL, arrayStringPayBote[6]);
            vr.put(ComputeDb.Columns.PRIZE_TOTAL, arrayStringPayBote[7]);
            vr.put(ComputeDb.Columns.WIN_TOTAL, arrayStringPayBote[8]);
            vr.put(ComputeDb.Columns.LOSE_TOTAL, arrayStringPayBote[9]);
        }
        SQLiteDatabase db = getWritableDatabase();
        db.update(ComputeDb.TABLE_NAME, vr, where, (String[]) null);
        db.close();
    }

    public void insertListSaved(String state, String uuid, long id) {
        ContentValues vr = new ContentValues();
        vr.put(UserTable.Columns._ID, id);
        vr.put(ListSavedDb.Columns.STATE, state);
        vr.put(ListSavedDb.Columns.UUID, uuid);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ListSavedDb.TABLE_NAME, (String) null, vr);
        db.close();
    }

    public void updateState(String state, long id) {
        ContentValues vr = new ContentValues();
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        vr.put(ListSavedDb.Columns.STATE, state);
        SQLiteDatabase db = getWritableDatabase();
        db.update(ListSavedDb.TABLE_NAME, vr, where, (String[]) null);
        db.close();
    }

    public String getEmailLogs(String turn) {
        String[] select = {ListSentLogDb.Column.TIME, ListSentLogDb.Column.LOG};
        String where = "{1} = '{2}'".replace("{1}", ListSentLogDb.Column.TURN).replace("{2}", turn);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(ListSentLogDb.TABLE_NAME, select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return Constants.NULL_STRING;
        } else if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return Constants.NULL_STRING;
        } else {
            String log = Constants.NULL_STRING;
            while (cursor.moveToNext()) {
                StringBuilder sb = new StringBuilder();
                sb.append(log);
                sb.append(cursor.getString(0));
                sb.append(cursor.getString(1));
                sb.append(!cursor.isLast() ? "\n" : Constants.NULL_STRING);
                log = sb.toString();
            }
            db.close();
            cursor.close();
            return log;
        }
    }

    public void setEmailLog(String turn, String time, String log) {
        ContentValues vr = new ContentValues();
        vr.put(ListSentLogDb.Column.TURN, turn);
        vr.put(ListSentLogDb.Column.TIME, time);
        vr.put(ListSentLogDb.Column.LOG, log);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(ListSentLogDb.TABLE_NAME, (String) null, vr);
        db.close();
    }

    public String getListUuid(long id) {
        String[] select = {ListSavedDb.Columns.UUID};
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(ListSavedDb.TABLE_NAME, select, where, null, null, null, null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return null;
        } else {
            cursor.moveToFirst();
            String uuid = cursor.getString(0);
            db.close();
            cursor.close();
            return uuid;
        }
    }

    public String getListState(long id) {
        String[] select = {ListSavedDb.Columns.STATE};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(ListSavedDb.TABLE_NAME, select, "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id)), (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return "no_found";
        } else if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return "no_found";
        } else {
            cursor.moveToFirst();
            String state = cursor.getString(0);
            db.close();
            cursor.close();
            return state;
        }
    }

    public void insertEmail(String email, String password) {
        ContentValues vr = new ContentValues();
        vr.put("email", email);
        vr.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("email", (String) null, vr);
        db.close();
    }

    public void updateEmail(String email, String password) {
        ContentValues vr = new ContentValues();
        vr.put("email", email);
        vr.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.update("email", vr, (String) null, (String[]) null);
        db.close();
    }

    public String[] emailAndPassword() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("email", new String[]{"email", "password"}, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            String[] emailPassword = {cursor.getString(0), cursor.getString(1)};
            db.close();
            cursor.close();
            return emailPassword;
        }
    }

    public HashMap<String, String> getAppInfo() {
        String[] select = {InfoDb.Columns.FIELD, "value"};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(InfoDb.TABLE_NAME, select, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            HashMap<String, String> result = new HashMap<>();
            while (cursor.moveToNext()) {
                result.put(cursor.getString(0), cursor.getString(1));
            }
            db.close();
            cursor.close();
            return result;
        }
    }

    public void dropAllTable(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE + RowDb.TABLE_NAME);
        db.execSQL(DROP_TABLE + PaperListDb.TABLE_NAME);
        db.execSQL(DROP_TABLE + PaperListDb.TABLE_NAME1);
        db.execSQL(DROP_TABLE + BingoDb.TABLE_NAME);
        db.execSQL(DROP_TABLE + ConfigDb.TABLE_NAME);
        db.execSQL(DROP_TABLE + PlayListDb.TABLE_NAME);
    }

    public long insertRow(Row row, boolean received, Boolean sent) {
        ContentValues vr = new ContentValues();
        vr.put(RowDb.Columns.CREATED_AT, Long.valueOf(System.currentTimeMillis()));
        vr.put(RowDb.Columns.BATCH_ID, Constants.NULL_STRING);
        vr.put(RowDb.Columns.DATA, row.getData());
        vr.put(RowDb.Columns.TYPE, Integer.valueOf(row.getType()));
        vr.put(RowDb.Columns.RECEIVED, String.valueOf(received));
        vr.put(RowDb.Columns.SENT, String.valueOf(sent));
        vr.put(RowDb.Columns.DELETED, "false");
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert("row", (String) null, vr);
        db.close();
        return id;
    }

    public void deleteRow(Row row) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(row.getId()));
        SQLiteDatabase db = getWritableDatabase();
        db.delete("row", where, (String[]) null);
        db.close();
    }

    public int deleteRow(List<Long> ids) {
        int rowsDeleted = 0;
        for (Long longValue : ids) {
            rowsDeleted += deleteRow(longValue.longValue());
        }
        return rowsDeleted;
    }

    public int deleteRow(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete("row", "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id)), (String[]) null);
        db.close();
        return rowsDeleted;
    }

    public int deletePaperList(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(PaperListDb.TABLE_NAME, "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id)), (String[]) null);
        db.close();
        return rowsDeleted;
    }

    public void deleteAllRow() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("row", (String) null, (String[]) null);
        db.close();
    }

    public void markAsSavedByChemise(PaperList pl) {
        ContentValues vr = new ContentValues();
        vr.put(UserTable.Columns._ID, pl.getId());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(MarkSaveDb.TABLE_NAME, (String) null, vr);
        db.delete("row", (String) null, (String[]) null);
        db.close();
    }

    public boolean isMarkAsSavedByChemise(PaperList pl) {
        String[] select = {UserTable.Columns._ID};
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", pl.getId().toString());
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(MarkSaveDb.TABLE_NAME, select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return false;
        }
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0) {
            return true;
        }
        return false;
    }

    public List<ContentValues> getAllRows() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID, RowDb.Columns.CREATED_AT, RowDb.Columns.BATCH_ID, RowDb.Columns.DATA, RowDb.Columns.TYPE}, (String) null, (String[]) null, (String) null, (String) null, "{1} DESC".replace("{1}", UserTable.Columns._ID));
        List<ContentValues> rowList = new ArrayList<>();
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            while (cursor.moveToNext()) {
                ContentValues cv = new ContentValues();
                cv.put(UserTable.Columns._ID, Long.valueOf(cursor.getLong(0)));
                cv.put(RowDb.Columns.CREATED_AT, Long.valueOf(cursor.getLong(1)));
                cv.put(RowDb.Columns.BATCH_ID, Long.valueOf(cursor.getLong(2)));
                cv.put(RowDb.Columns.DATA, cursor.getString(3));
                cv.put(RowDb.Columns.TYPE, Integer.valueOf(cursor.getInt(4)));
                rowList.add(cv);
            }
            db.close();
            cursor.close();
            return rowList;
        }
    }

    public List<ContentValues> getAllRowsNotDel() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID, RowDb.Columns.CREATED_AT, RowDb.Columns.BATCH_ID, RowDb.Columns.DATA, RowDb.Columns.TYPE}, "{1} != '{2}'".replace("{1}", RowDb.Columns.DELETED).replace("{2}", Boolean.TRUE.toString()), (String[]) null, (String) null, (String) null, "_id DESC");
        List<ContentValues> rowList = new ArrayList<>();
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            while (cursor.moveToNext()) {
                ContentValues cv = new ContentValues();
                cv.put(UserTable.Columns._ID, Long.valueOf(cursor.getLong(0)));
                cv.put(RowDb.Columns.CREATED_AT, Long.valueOf(cursor.getLong(1)));
                cv.put(RowDb.Columns.BATCH_ID, Long.valueOf(cursor.getLong(2)));
                cv.put(RowDb.Columns.DATA, cursor.getString(3));
                cv.put(RowDb.Columns.TYPE, Integer.valueOf(cursor.getInt(4)));
                rowList.add(cv);
            }
            db.close();
            cursor.close();
            return rowList;
        }
    }

    public void insertList(PaperList list) {
        ContentValues vr = new ContentValues();
        vr.put(UserTable.Columns._ID, list.getId());
        vr.put(PaperListDb.Columns.LIST_STRING, MyCipher.Encrypt(list.getHeaders() + ConstantsParserRowList.ROW_SEPARATOR + getAllRowsForSave()));
        SQLiteDatabase db = getWritableDatabase();

        db.insert(PaperListDb.TABLE_NAME,  null, vr);
        db.delete("row", null,  null);
        db.close();
    }

    public boolean changeListId(long oldId, long newId) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(oldId));
        String[] select = {PaperListDb.Columns.LIST_STRING};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return false;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return false;
        } else {
            cursor.moveToFirst();
            String listEncrypted = cursor.getString(0);
            if (listEncrypted == null || listEncrypted.isEmpty()) {
                return false;
            }
            String listEncrypted2 = MyCipher.Encrypt(MyCipher.Decrypt(listEncrypted).replace(String.valueOf(oldId), String.valueOf(newId)));
            ContentValues vr = new ContentValues();
            vr.put(UserTable.Columns._ID, Long.valueOf(newId));
            vr.put(PaperListDb.Columns.LIST_STRING, listEncrypted2);
            if (db.insert(PaperListDb.TABLE_NAME, (String) null, vr) == -1) {
                return false;
            }
            deletePaperList(oldId);
            return true;
        }
    }

    public List<Long> getPaperList() {
        String[] select = {UserTable.Columns._ID};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, select, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            List<Long> paperList = new ArrayList<>();
            cursor.moveToLast();
            do {
                paperList.add(Long.valueOf(cursor.getLong(0)));
            } while (cursor.moveToPrevious());
            db.close();
            cursor.close();
            return paperList;
        }
    }

    public PaperList getLastPaperList() {
        String[] select = {PaperListDb.Columns.LIST_STRING};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, select, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToLast();
            PaperList paperList = null;
            try {
                paperList = new PaperList(MyCipher.Decrypt(cursor.getString(0)));
            } catch (Exception e) {
            }
            db.close();
            cursor.close();
            return paperList;
        }
    }

    public String getPaperListMD5() {
        List<Long> list = getPaperList();
        if (list == null) {
            return null;
        }
        StringBuilder list_string = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            list_string.append(list.get(i));
        }
        return MyCipher.MD5(list_string.toString());
    }

    public PaperList getPaperListById(Long id) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", id.toString());
        String[] select = {PaperListDb.Columns.LIST_STRING};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            try {
                String decryptList = MyCipher.Decrypt(cursor.getString(0));
                PaperList paperList = new PaperList(decryptList);
                db.close();
                cursor.close();
                return paperList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                db.close();
                cursor.close();
            }
        }
    }

    public String getStringListById(Long id) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", id.toString());
        String[] select = {PaperListDb.Columns.LIST_STRING};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            String list = cursor.getString(0);
            db.close();
            cursor.close();
            return list;
        }
    }

    public PaperList getPaperListByTurn(String current_turn) {
        String[] select = {UserTable.Columns._ID, PaperListDb.Columns.LIST_STRING};
        String order = "{1} DESC".replace("{1}", UserTable.Columns._ID);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, select, (String) null, (String[]) null, (String) null, (String) null, order);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            PaperList list = null;
            while (true) {
                if (!cursor.moveToNext()) {
                    break;
                }
                MyTime.setCalendar(Long.valueOf(cursor.getLong(0)));
                if (current_turn.equals(MyTime.generateTurn())) {
                    try {
                        list = new PaperList(MyCipher.Decrypt(cursor.getString(1)));
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            MyTime.releaseCalendar();
            db.close();
            cursor.close();
            return list;
        }
    }

    public void deletePaperListById(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PaperListDb.TABLE_NAME, "_id = " + id, (String[]) null);
        db.close();
        updateConfigValue(Config.CHECK_LIST, getPaperListMD5());
    }

    public boolean existPaperList(String turn, String play, String numberList) {
        String where = "{1} = '{2}' and {3} = '{4}' and {5} = {6}".replace("{1}", PaperListDb.Columns.LIST_TURN).replace("{2}", turn).replace("{3}", PaperListDb.Columns.LIST_PLAY).replace("{4}", play).replace("{5}", "list_number").replace("{6}", numberList);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PaperListDb.TABLE_NAME, new String[]{UserTable.Columns._ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return false;
        }
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if (count > 0) {
            return true;
        }
        return false;
    }

    public void insertBingo(Bingo bingo) {
        ContentValues vr = new ContentValues();
        vr.put(UserTable.Columns._ID, bingo.getId());
        vr.put("centene", bingo.getCentene());
        vr.put("fijo", bingo.getFijo());
        vr.put("corrido1", bingo.getCorrido1());
        vr.put("corrido2", bingo.getCorrido2());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("bingo", (String) null, vr);
        db.close();
    }

    public void updateBingo(Bingo bingo) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", bingo.getId().toString());
        ContentValues vr = new ContentValues();
        vr.put(UserTable.Columns._ID, bingo.getId());
        vr.put("centene", bingo.getCentene());
        vr.put("fijo", bingo.getFijo());
        vr.put("corrido1", bingo.getCorrido1());
        vr.put("corrido2", bingo.getCorrido2());
        SQLiteDatabase db = getWritableDatabase();
        db.update("bingo", vr, where, (String[]) null);
        db.close();
    }

    public Bingo getBingoById(Long id) {
        String[] select = {UserTable.Columns._ID, "centene", "fijo", "corrido1", "corrido2"};
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("bingo", select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            Bingo bingo = new Bingo(Long.valueOf(cursor.getLong(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            db.close();
            cursor.close();
            return bingo;
        }
    }

    public void deleteBingById(Long id) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        SQLiteDatabase db = getWritableDatabase();
        db.delete("bingo", where, (String[]) null);
        db.close();
    }

    public HashMap<String, String> getConfigValues() {
        String[] select = {ConfigDb.Columns.KEY, "value"};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("config", select, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        }
        HashMap<String, String> config_fields = new HashMap<>();
        if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return config_fields;
        }
        while (cursor.moveToNext()) {
            config_fields.put(cursor.getString(0), MyCipher.Decrypt(cursor.getString(1)));
        }
        db.close();
        cursor.close();
        return config_fields;
    }

    public void updateConfigValue(String key, String value) {
        String where = "{1} = '{2}'".replace("{1}", ConfigDb.Columns.KEY).replace("{2}", key);
        ContentValues vr = new ContentValues();
        vr.put(ConfigDb.Columns.KEY, key);
        vr.put("value", MyCipher.Encrypt(value));
        SQLiteDatabase db = getWritableDatabase();
        if (db.update("config", vr, where, (String[]) null) == 0) {
            db.insert("config", (String) null, vr);
        }
        db.close();
    }

    public String getReceivedRowsForSave() {
        String[] select = {UserTable.Columns._ID, RowDb.Columns.CREATED_AT, RowDb.Columns.DATA, RowDb.Columns.TYPE};
        String where = "{1} = '{2}'".replace("{1}", RowDb.Columns.RECEIVED).replace("{2}", "true");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            String rows = Constants.NULL_STRING;
            while (cursor.moveToNext()) {
                rows = rows + cursor.getString(0);
                for (int i = 1; i < select.length; i++) {
                    rows = rows + ',' + cursor.getString(i);
                }
                if (cursor.isLast()) {
                    rows = rows + ConstantsParserRowList.ROW_SEPARATOR;
                }
            }
            cursor.close();
            db.close();
            return rows;
        }
    }

    public String getAllRowsForSave() {
        String[] select = {UserTable.Columns._ID, RowDb.Columns.CREATED_AT, RowDb.Columns.DATA, RowDb.Columns.TYPE};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(RowDb.TABLE_NAME, select, null, null, null, null, null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            StringBuilder rows = new StringBuilder(Constants.NULL_STRING);
            while (cursor.moveToNext()) {
                rows.append(cursor.getString(0));
                for (int i = 1; i < select.length; i++) {
                    rows.append(ConstantsParserRowList.COLUMN_ROW_SEPARATOR).append(cursor.getString(i));
                }
                if (!cursor.isLast()) {
                    rows.append(ConstantsParserRowList.ROW_SEPARATOR);
                }
            }
            cursor.close();
            db.close();
            return rows.toString();
        }
    }

    public String getRowsNotSentForAdd() {
        int type;
        String where = "{1} = '{2}' and {3} = '{4}'".replace("{1}", RowDb.Columns.SENT).replace("{2}", "false").replace("{3}", RowDb.Columns.DELETED).replace("{4}", "false");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID, RowDb.Columns.DATA, RowDb.Columns.TYPE}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            String rowIdsList = Constants.NULL_STRING;
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String data = cursor.getString(1);
                switch (cursor.getInt(2)) {
                    case Constants.BALL:
                        type = 1;
                        break;
                    case Constants.PARLLET:
                        type = 2;
                        break;
                    case Constants.CENTENE:
                        type = 3;
                        break;
                    case Constants.BALL_BOTE:
                        type = 4;
                        break;
                    case Constants.PARLLET_BOTE:
                        type = 5;
                        break;
                    case Constants.CENTENE_BOTE:
                        type = 6;
                        break;
                    default:
                        type = 0;
                        break;
                }
                rowIdsList = rowIdsList + "ADD " + id + " " + data + " " + type + "\n";
            }
            cursor.close();
            db.close();
            return rowIdsList;
        }
    }

    public String getRowsSentForAdd(String uuid) {
        int type;
        String where = "send = 'true' AND delete = 'false' AND batch_id = 'uuid'".replace("send", RowDb.Columns.SENT).replace("delete", RowDb.Columns.DELETED).replace(RowDb.Columns.BATCH_ID, RowDb.Columns.BATCH_ID).replace(ListSavedDb.Columns.UUID, uuid);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID, RowDb.Columns.DATA, RowDb.Columns.TYPE}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            String rowIdsList = Constants.NULL_STRING;
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String data = cursor.getString(1);
                switch (cursor.getInt(2)) {
                    case Constants.BALL:
                        type = 1;
                        break;
                    case Constants.PARLLET:
                        type = 2;
                        break;
                    case Constants.CENTENE:
                        type = 3;
                        break;
                    case Constants.BALL_BOTE:
                        type = 4;
                        break;
                    case Constants.PARLLET_BOTE:
                        type = 5;
                        break;
                    case Constants.CENTENE_BOTE:
                        type = 6;
                        break;
                    default:
                        type = 0;
                        break;
                }
                rowIdsList = rowIdsList + "ADD " + id + " " + data + " " + type + "\n";
            }
            cursor.close();
            db.close();
            return rowIdsList;
        }
    }

    public boolean isAnyRowSent() {
        String where = "{1} = '{2}'".replace("{1}", RowDb.Columns.SENT).replace("{2}", "true");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID, RowDb.Columns.DATA, RowDb.Columns.TYPE}, where, (String[]) null, (String) null, (String) null, (String) null);
        boolean isAnySent = false;
        if (cursor == null) {
            db.close();
            return false;
        }
        if (cursor.getCount() > 0) {
            isAnySent = true;
        }
        db.close();
        cursor.close();
        return isAnySent;
    }

    public int shootsMake() {
        String where = "{1} = '{2}' and {3} != ''".replace("{1}", RowDb.Columns.SENT).replace("{2}", "true").replace("{3}", RowDb.Columns.BATCH_ID);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{RowDb.Columns.BATCH_ID}, where, (String[]) null, RowDb.Columns.BATCH_ID, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return 0;
        }
        int countShoots = cursor.getCount();
        db.close();
        cursor.close();
        return countShoots;
    }

    public String getRowsNotSentForDelete() {
        String where = "{1} = '{2}' and {3} = '{4}'".replace("{1}", RowDb.Columns.SENT).replace("{2}", "false").replace("{3}", RowDb.Columns.DELETED).replace("{4}", "true");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            String rowIdsList = Constants.NULL_STRING;
            while (cursor.moveToNext()) {
                rowIdsList = rowIdsList + "DEL " + cursor.getString(0) + "\n";
            }
            cursor.close();
            db.close();
            return rowIdsList;
        }
    }

    public String getRowsSentForDelete(String uuid) {
        String where = "{1} = '{2}' and {3} = '{4}' and {5} = '{6}'".replace("{1}", RowDb.Columns.SENT).replace("{2}", "true").replace("{3}", RowDb.Columns.DELETED).replace("{4}", "true").replace("{5}", RowDb.Columns.BATCH_ID).replace("{6}", uuid);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            String rowIdsList = Constants.NULL_STRING;
            while (cursor.moveToNext()) {
                rowIdsList = rowIdsList + "DEL " + cursor.getString(0) + "\n";
            }
            cursor.close();
            db.close();
            return rowIdsList;
        }
    }

    public List<Long> getRowsForDel(String batch) {
        String where = "{1} = '{2}' and {3} = '{4}'".replace("{1}", RowDb.Columns.BATCH_ID).replace("{2}", batch).replace("{3}", RowDb.Columns.DELETED).replace("{4}", "true");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            List<Long> rowsForDel = new ArrayList<>();
            while (cursor.moveToNext()) {
                rowsForDel.add(Long.valueOf(cursor.getLong(0)));
            }
            cursor.close();
            db.close();
            return rowsForDel;
        }
    }

    public int setReceivedRows(String batch) {
        String where = "{1} = '{2}' and {3} = '{4}'".replace("{1}", RowDb.Columns.BATCH_ID).replace("{2}", batch).replace("{3}", RowDb.Columns.DELETED).replace("{4}", "false");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return 0;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return 0;
        } else {
            int countUpdateRows = 0;
            while (cursor.moveToNext()) {
                countUpdateRows += setReceivedRow(cursor.getLong(0), Boolean.TRUE.toString());
            }
            cursor.close();
            db.close();
            return countUpdateRows;
        }
    }

    public Set<String> getBatchSentNotReceived() {
        String where = "{1} = '{2}' and {3} = '{4}'".replace("{1}", RowDb.Columns.SENT).replace("{2}", "true").replace("{3}", RowDb.Columns.RECEIVED).replace("{4}", "false");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{RowDb.Columns.BATCH_ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            Set<String> batch = new HashSet<>();
            while (cursor.moveToNext()) {
                batch.add(cursor.getString(0));
            }
            cursor.close();
            db.close();
            return batch;
        }
    }

    public boolean isAnyRowReceived() {
        String where = "{1} = '{2}'".replace("{1}", RowDb.Columns.RECEIVED).replace("{2}", "true");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{RowDb.Columns.BATCH_ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        boolean anyReceived = false;
        if (cursor == null) {
            db.close();
            return false;
        }
        if (cursor.getCount() > 0) {
            anyReceived = true;
        }
        cursor.close();
        db.close();
        return anyReceived;
    }

    public List<Long> getRowsForDeleteNotSent() {
        String where = "{1} = '{2}' and {3} = '{4}'".replace("{1}", RowDb.Columns.SENT).replace("{2}", "false").replace("{3}", RowDb.Columns.DELETED).replace("{4}", "true");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{UserTable.Columns._ID}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            List<Long> ids = new ArrayList<>();
            while (cursor.moveToNext()) {
                ids.add(Long.valueOf(cursor.getLong(0)));
            }
            cursor.close();
            db.close();
            return ids;
        }
    }

    public int getRowTypeById(long id) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{RowDb.Columns.TYPE}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return -1;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return -1;
        } else {
            cursor.moveToFirst();
            int type = cursor.getInt(0);
            cursor.close();
            db.close();
            return type;
        }
    }

    public String getRowStringById(long id) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        String[] select = {UserTable.Columns._ID, RowDb.Columns.CREATED_AT, RowDb.Columns.DATA, RowDb.Columns.TYPE};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", select, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return null;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            String rows = cursor.getString(0);
            for (int i = 1; i < select.length; i++) {
                rows = rows + ',' + cursor.getString(i);
            }
            cursor.close();
            db.close();
            return rows;
        }
    }

    public int setDeletedRow(List<Long> ids) {
        int rowsUpdated = 0;
        for (Long longValue : ids) {
            rowsUpdated += setDeletedRow(longValue.longValue());
        }
        return rowsUpdated;
    }

    public int setDeletedRow(String batchId, List<Long> ids) {
        int rowsUpdated = 0;
        ContentValues cv = new ContentValues();
        cv.put(RowDb.Columns.BATCH_ID, batchId);
        cv.put(RowDb.Columns.SENT, Boolean.TRUE.toString());
        cv.put(RowDb.Columns.RECEIVED, Boolean.FALSE.toString());
        cv.put(RowDb.Columns.DELETED, Boolean.TRUE.toString());
        for (Long longValue : ids) {
            rowsUpdated += setValuesRow(cv, longValue.longValue());
        }
        return rowsUpdated;
    }

    public int setSentRow(String batchId, List<Long> ids) {
        int rowsUpdated = 0;
        ContentValues cv = new ContentValues();
        cv.put(RowDb.Columns.BATCH_ID, batchId);
        cv.put(RowDb.Columns.SENT, Boolean.TRUE.toString());
        cv.put(RowDb.Columns.RECEIVED, Boolean.FALSE.toString());
        for (Long longValue : ids) {
            rowsUpdated += setValuesRow(cv, longValue.longValue());
        }
        return rowsUpdated;
    }

    public int setSentRow(long id, String value) {
        return setValueRow(RowDb.Columns.SENT, value, id);
    }

    public int setReceivedRow(List<Long> ids, String value) {
        int rowsUpdated = 0;
        for (Long longValue : ids) {
            rowsUpdated += setReceivedRow(longValue.longValue(), value);
        }
        return rowsUpdated;
    }

    public int setReceivedRow(long id, String value) {
        return setValueRow(RowDb.Columns.RECEIVED, value, id);
    }

    public int setDeletedRow(long id) {
        ContentValues cv = new ContentValues();
        cv.put(RowDb.Columns.DELETED, Boolean.TRUE.toString());
        cv.put(RowDb.Columns.SENT, Boolean.FALSE.toString());
        return setValuesRow(cv, id);
    }

    private int setValueRow(String key, String value, long id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues sentValue = new ContentValues();
        sentValue.put(key, value);
        int rowsUpdated = db.update("row", sentValue, "_id=" + id, (String[]) null);
        db.close();
        return rowsUpdated;
    }

    private int setValuesRow(ContentValues values, long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsUpdated = db.update("row", values, "_id=" + id, (String[]) null);
        db.close();
        return rowsUpdated;
    }

    public boolean isRowSent(long id) {
        String where = "{1} = {2}".replace("{1}", UserTable.Columns._ID).replace("{2}", String.valueOf(id));
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("row", new String[]{RowDb.Columns.SENT}, where, (String[]) null, (String) null, (String) null, (String) null);
        if (cursor == null) {
            db.close();
            return false;
        } else if (cursor.getCount() == 0) {
            db.close();
            cursor.close();
            return false;
        } else {
            cursor.moveToFirst();
            boolean isSent = Boolean.valueOf(cursor.getString(0)).booleanValue();
            cursor.close();
            db.close();
            return isSent;
        }
    }

    public Cursor getCursorByRowType(int rowType) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                UserTable.Columns._ID,
                RowDb.Columns.DATA,
                RowDb.Columns.SENT,
                RowDb.Columns.RECEIVED,
                RowDb.Columns.DELETED,
                RowDb.Columns.TYPE
        };

        String selection = "{1} = {2}"
                .replace("{1}", RowDb.Columns.TYPE)
                .replace("{2}", String.valueOf(rowType));

        return db.query(
                RowDb.TABLE_NAME,
                columns,
                selection,
                null, null, null,
                "{1} DESC".replace("{1}", UserTable.Columns._ID));
    }
}
