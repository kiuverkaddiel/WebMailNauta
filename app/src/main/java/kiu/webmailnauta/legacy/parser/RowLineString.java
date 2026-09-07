package kiu.webmailnauta.legacy.parser;

public abstract class RowLineString {
    protected final String data;
    protected final String prise;
    protected final RowType type;

    public abstract String process();

    public RowLineString(RowType type2, String data2, String prise2) {
        this.type = type2;
        this.data = data2;
        this.prise = prise2;
    }

    public RowType getType() {
        return this.type;
    }

    public String getData() {
        return this.data;
    }

    public String getPrise() {
        return this.prise;
    }

    public String toString() {
        return this.data + " " + this.prise;
    }
}
