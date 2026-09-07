package kiu.webmailnauta.legacy.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser {
    protected boolean created = true;
    protected final RowType type;

    /* access modifiers changed from: protected */
    public abstract RowLineString getRowLine(Cursor cursor) throws Exception;

    public abstract String getTypeString();

    public Parser(RowType type2) {
        this.type = type2;
    }

    public List<RowLineString> getRowsLines(Cursor cursor) throws Exception {
        List<RowLineString> rows_list = new ArrayList<>();
        RowLineString row_line = getRowLine(cursor);
        while (row_line != null) {
            rows_list.add(row_line);
            row_line = getRowLine(cursor);
        }
        return rows_list;
    }

    public RowType getType() {
        return this.type;
    }

    public boolean isCreated() {
        return this.created;
    }
}
