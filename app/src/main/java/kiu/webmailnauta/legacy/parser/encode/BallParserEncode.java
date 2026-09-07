package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class BallParserEncode extends Parser {
    public BallParserEncode(RowType type) {
        super(type);
    }

    /* access modifiers changed from: protected */
    public RowLineStringEncode getRowLine(Cursor cursor) throws Exception {
        String prise;
        String data;
        if (!cursor.hasNext()) {
            return null;
        }
        String line = cursor.getNextLine();
        if (line.startsWith("-")) {
            this.created = false;
            return null;
        }
        String[] parts = line.split(" ");
        if (parts.length == 2) {
            String data2 = parts[1];
            String[] parts2 = cursor.getNextLine().split(" ");
            String data3 = data2 + "+";
            prise = parts2[2];
            if (parts2.length == 4) {
                prise = prise + " " + parts2[3];
            }
            String line2 = cursor.getNextLine();
            data = data3 + line2.substring(1);
        } else if (parts.length == 3) {
            data = parts[1];
            prise = parts[2];
        } else if (parts.length == 4) {
            prise = parts[2] + " " + parts[3];
            data = parts[1];
        } else {
            throw new Exception("Parser error " + getType() + " in line " + cursor.getLineNumber());
        }
        return new RowLineStringEncode(this.type, data, prise);
    }

    public String getTypeString() {
        return "B";
    }
}
