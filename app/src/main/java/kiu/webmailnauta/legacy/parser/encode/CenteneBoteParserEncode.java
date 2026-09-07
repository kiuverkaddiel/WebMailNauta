package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.parser.RowType;

public class CenteneBoteParserEncode extends CenteneParserEncode {
    public CenteneBoteParserEncode(RowType type) {
        super(type);
    }

    public String getTypeString() {
        return "CB";
    }
}
