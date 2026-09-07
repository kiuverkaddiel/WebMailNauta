package kiu.webmailnauta.legacy.db;

public class DefaultTable implements Table {
    private String[] columns;
    private String name;

    public DefaultTable(String name2, String[] columns2) {
        this.name = name2;
        this.columns = columns2;
    }

    public String getName() {
        return this.name;
    }

    public String[] getColumns() {
        return this.columns;
    }
}
