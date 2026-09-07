package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class ParletParserEncode extends Parser {
    public ParletParserEncode(RowType type) {
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
        String data = Constants.NULL_STRING;
        String prise = null;
        while (!line.startsWith("-")) {
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                data = data + parts[1];
            } else if (parts.length == 3) {
                data = data + parts[1];
                prise = parts[2];
            } else {
                throw new Exception("Parser error " + getType() + " in line " + cursor.getLineNumber());
            }
            if (!cursor.hasNext()) {
                break;
            }
            line = cursor.getNextLine();
        }
        return new RowLineStringEncode(this.type, data, prise);
    }

    public String getTypeString() {
        return "P";
    }
}
