package kiu.webmailnauta.legacy.db;

import kiu.webmailnauta.legacy.Constants;

public class SqliteHelper {
    private final String CREATE_TABLE = "CREATE TABLE ";
    private Table table;

    public SqliteHelper(Table table2) {
        this.table = table2;
    }

    public String getCreateSql() {
        String columns = fixColumns(this.table.getColumns());
        if (columns == null) {
            return null;
        }
        return "CREATE TABLE {name} ({columns});".replace("{name}", this.table.getName()).replace("{columns}", columns);
    }

    private String fixColumns(String[] columns) {
        if (columns == null || columns.length == 0) {
            return null;
        }
        String aux = fixColumn(columns[0]);
        if (columns.length > 1) {
            for (int i = 1; i < columns.length; i++) {
                aux = aux + ", " + fixColumn(columns[i]);
            }
        }
        return aux;
    }

    private String fixColumn(String column) {
        String[] aux = column.split(Constants.VALUE_SEPARATOR);
        if (aux.length != 2) {
            return null;
        }
        return aux[0].toLowerCase() + " " + aux[1].toUpperCase();
    }
}
