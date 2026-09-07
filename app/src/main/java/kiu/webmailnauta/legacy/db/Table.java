package kiu.webmailnauta.legacy.db;

public interface Table {
    public static final String AUTOINCREMENT = " autoincrement";
    public static final String INTEGER = ":integer ";
    public static final String PRIMARY_KEY = " primary key ";
    public static final String TEXT = ":text ";

    String[] getColumns();

    String getName();
}
