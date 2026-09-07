package kiu.webmailnauta.legacy.parser.decode;

import kiu.webmailnauta.legacy.Constants;
import kiu.webmailnauta.legacy.parser.Cursor;
import kiu.webmailnauta.legacy.parser.IParserFactory;
import kiu.webmailnauta.legacy.parser.Parser;
import kiu.webmailnauta.legacy.parser.RowType;
import kiu.webmailnauta.legacy.utils.Convert;

public class ParserFactoryDecode implements IParserFactory {
    private String collector;
    private String list_number;
    private String list_version;
    private String play_name;

    public Parser getParserByType(String type) {
        if (type.equals(IParserFactory.BALL)) {
            return new BallParserDecode(RowType.BALL);
        }
        if (type.equals(IParserFactory.BALL_BOTE)) {
            return new BallBoteParserDecode(RowType.BALL_BOTE);
        }
        if (type.equals(IParserFactory.PARLE)) {
            return new ParletParserDecode(RowType.PARLET);
        }
        if (type.equals(IParserFactory.PARLE_BOTE)) {
            return new ParletBoteParserDecode(RowType.PARLET_BOTE);
        }
        if (type.equals(IParserFactory.CENTENE)) {
            return new CenteneParserDecode(RowType.CENTENE);
        }
        if (type.equals(IParserFactory.CENTENE_BOTE)) {
            return new CenteneBoteParserDecode(RowType.CENTENE_BOTE);
        }
        return null;
    }

    public Parser getParserByLine(String line) {
        if (line.startsWith("..")) {
            this.collector = Convert.asciiToString(line.replace("..", Constants.NULL_STRING));
            return null;
        } else if (!line.startsWith(".")) {
            return null;
        } else {
            String[] aux = line.replace(".", Constants.NULL_STRING).split("X");
            if (aux.length == 2) {
                this.play_name = Convert.asciiToString(aux[0]);
                this.list_number = aux[1];
                this.list_version = "v4";
                return null;
            } else if (aux.length != 3) {
                return getParserByType(getType(Convert.asciiToString(line.replace(".", Constants.NULL_STRING)).toUpperCase()));
            } else {
                this.play_name = Convert.asciiToString(aux[0]);
                this.list_number = aux[1];
                this.list_version = aux[2];
                return null;
            }
        }
    }

    private String getType(String type) {
        if (type.equals("B")) {
            return IParserFactory.BALL;
        }
        if (type.equals("BB")) {
            return IParserFactory.BALL_BOTE;
        }
        if (type.equals("P")) {
            return IParserFactory.PARLE;
        }
        if (type.equals("PB")) {
            return IParserFactory.PARLE_BOTE;
        }
        if (type.equals("C")) {
            return IParserFactory.CENTENE;
        }
        if (type.equals("CB")) {
            return IParserFactory.CENTENE_BOTE;
        }
        return null;
    }

    public String getPlayName() {
        return this.play_name;
    }

    public String getListNumber() {
        return this.list_number;
    }

    public String getCollector() {
        return this.collector;
    }

    public String getListVersion() {
        return this.list_version;
    }

    public Parser createParser(Cursor cursor) {
        String line;
        try {
            if (cursor.getLineNumber() == -1) {
                line = cursor.getNextLine();
            } else {
                line = cursor.getLine();
            }
            Parser parser = getParserByLine(line);
            while (cursor.hasNext() && parser == null) {
                parser = getParserByLine(cursor.getNextLine());
            }
            return parser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
