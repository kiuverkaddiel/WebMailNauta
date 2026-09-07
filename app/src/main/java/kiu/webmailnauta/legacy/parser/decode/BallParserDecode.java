package kiu.webmailnauta.legacy.parser.decode;

import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class BallParserDecode extends Parser {
    public BallParserDecode(RowType type) {
        super(type);
    }

    /* access modifiers changed from: protected */
    public RowLineStringDecode getRowLine(Cursor cursor) throws Exception {
        if (!cursor.hasNext()) {
            return null;
        }
        String line = cursor.getNextLine();
        if (line.startsWith(".")) {
            this.created = false;
            return null;
        }
        int i = 2;
        String data = String.valueOf(line.charAt(0)) + line.charAt(1);
        if (line.charAt(2) == '!') {
            if (line.charAt(3) == '!') {
                data = data + "t" + line.charAt(4) + line.charAt(5);
                i = 6;
            } else {
                data = data + ")";
                i = 3;
            }
        }
        return new RowLineStringDecode(this.type, data + "S", line.substring(i));
    }

    public String getTypeString() {
        return "2131296263";
    }
}
