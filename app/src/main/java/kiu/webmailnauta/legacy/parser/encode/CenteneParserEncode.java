package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class CenteneParserEncode extends Parser {
    public CenteneParserEncode(RowType type) {
        super(type);
    }

    /* access modifiers changed from: protected */
    public RowLineStringEncode getRowLine(Cursor cursor) throws Exception {
        if (!cursor.hasNext()) {
            return null;
        }
        String line = cursor.getNextLine();
        if (line.startsWith("-")) {
            this.created = false;
            return null;
        }
        String[] parts = line.split(" ");
        if (parts.length == 3) {
            return new RowLineStringEncode(this.type, parts[1], parts[2]);
        }
        throw new Exception("Parser error " + getType() + " in line " + cursor.getLineNumber());
    }

    public String getTypeString() {
        return "C";
    }
}
