package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.parser.RowType;

public class BallBoteParserEncode extends BallParserEncode {
    public BallBoteParserEncode(RowType type) {
        super(type);
    }

    public String getTypeString() {
        return "BB";
    }
}
