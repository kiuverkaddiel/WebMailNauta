package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.parser.RowType;

public class ParletBoteParserEncode extends ParletParserEncode {
    public ParletBoteParserEncode(RowType type) {
        super(type);
    }

    public String getTypeString() {
        return "PB";
    }
}
