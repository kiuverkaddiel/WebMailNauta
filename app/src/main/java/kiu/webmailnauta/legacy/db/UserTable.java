package kiu.webmailnauta.legacy.db;

import android.provider.BaseColumns;

public class UserTable extends DefaultTable {
    public static final String tableName = "user";

    public interface Columns extends BaseColumns {
        String password = "password";
        String user = "user";
    }

    public UserTable() {
        super("user", new String[]{"_id:integer  primary key ", "user:text ", "password:text "});
    }
}
