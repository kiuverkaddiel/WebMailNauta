package kiu.webmailnauta.legacy.parser.decode;

import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class ParletParserDecode extends Parser {
    public ParletParserDecode(RowType type) {
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
        String data = Constants.NULL_STRING;
        String prise = null;
        if (line.endsWith("!")) {
            int data_length = 3;
            if (line.length() % 2 == 0) {
                data_length = 2;
            }
            String aux_data = line.substring(0, line.length() - data_length);
            for (int i = 0; i < aux_data.length() - 1; i += 2) {
                data = data + String.valueOf(aux_data.charAt(i)) + String.valueOf(aux_data.charAt(i + 1)) + "S";
            }
            prise = line.substring(line.length() - data_length);
        } else {
            String[] line_parts = line.split(" ");
            if (line_parts.length == 2) {
                String aux_data2 = line_parts[0];
                for (int i2 = 0; i2 < aux_data2.length() - 1; i2 += 2) {
                    data = data + String.valueOf(aux_data2.charAt(i2)) + String.valueOf(aux_data2.charAt(i2 + 1)) + "S";
                }
                prise = line_parts[1];
            }
        }
        return new RowLineStringDecode(this.type, data, prise);
    }

    public String getTypeString() {
        return "2131296264";
    }
}
