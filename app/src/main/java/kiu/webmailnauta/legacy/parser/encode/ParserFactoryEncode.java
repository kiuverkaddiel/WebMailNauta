package kiu.webmailnauta.legacy.parser.encode;

import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.IParserFactory;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;

public class ParserFactoryEncode implements IParserFactory {
    public Parser getParserByType(String type) {
        if (type.equals(IParserFactory.BALL)) {
            return new BallParserEncode(RowType.BALL);
        }
        if (type.equals(IParserFactory.BALL_BOTE)) {
            return new BallBoteParserEncode(RowType.BALL_BOTE);
        }
        if (type.equals(IParserFactory.PARLE)) {
            return new ParletParserEncode(RowType.PARLET);
        }
        if (type.equals(IParserFactory.PARLE_BOTE)) {
            return new ParletBoteParserEncode(RowType.PARLET_BOTE);
        }
        if (type.equals(IParserFactory.CENTENE)) {
            return new CenteneParserEncode(RowType.CENTENE);
        }
        if (type.equals(IParserFactory.CENTENE_BOTE)) {
            return new CenteneBoteParserEncode(RowType.CENTENE_BOTE);
        }
        return null;
    }

    public Parser getParserByLine(String line) {
        return getParserByType(line.replace("-", Constants.NULL_STRING));
    }

    public Parser createParser(Cursor cursor) {
        Parser parser = null;

        try {
            do {
                parser = getParserByLine(cursor.getNextLine());
            } while (parser == null && cursor.hasNext());

            if (parser != null && cursor.hasNext()) {
                cursor.getNextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parser;
    }
}
