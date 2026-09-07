package kiu.webmailnauta.legacy.parser.decode;

import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class CenteneParserDecode extends Parser {
    public CenteneParserDecode(RowType type) {
        super(type);
    }

    /* access modifiers changed from: protected */
    public RowLineStringDecode getRowLine(Cursor cursor) throws Exception {
        int DATA_END;
        if (!cursor.hasNext()) {
            return null;
        }
        String line = cursor.getNextLine();
        if (line.startsWith(".")) {
            this.created = false;
            return null;
        }
        if (line.length() < 8) {
            DATA_END = 3;
        } else if (line.length() == 8) {
            if (line.contains(".") || line.contains("!") || line.contains(")")) {
                DATA_END = 3;
            } else {
                DATA_END = 7;
            }
        } else if (line.substring(0, 7).contains(".")) {
            DATA_END = 3;
        } else {
            DATA_END = 7;
        }
        return new RowLineStringDecode(this.type, line.substring(0, DATA_END) + "S", line.substring(DATA_END));
    }

    public String getTypeString() {
        return "2131296265";
    }
}
