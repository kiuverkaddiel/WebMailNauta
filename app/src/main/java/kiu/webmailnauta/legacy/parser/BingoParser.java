package kiu.webmailnauta.legacy.parser;

import kiu.webmailnauta.legacy.Constants;

public class BingoParser {
    private static final char _CENT = 'C';
    private static final char _COR1 = 'c';
    private static final char _COR2 = 'b';
    private static final char _FIJO = 'F';
    private static final char _PASS = '-';
    private String centene = Constants.NULL_STRING;
    private String corrido1 = Constants.NULL_STRING;
    private String corrido2 = Constants.NULL_STRING;
    private String fijo = Constants.NULL_STRING;

    public BingoParser(String bingo, String parser) throws Exception {
        for (int i = 0; i < parser.length(); i++) {
            char c = parser.charAt(i);
            switch (c) {
                case _PASS:
                    System.out.println("Ignore simbol: " + bingo.charAt(i));
                    break;
                case _CENT:
                    this.centene += bingo.charAt(i);
                    break;
                case _FIJO:
                    this.fijo += bingo.charAt(i);
                    break;
                case _COR2:
                    this.corrido2 += bingo.charAt(i);
                    break;
                case _COR1:
                    this.corrido1 += bingo.charAt(i);
                    break;
                default:
                    throw new Exception("Incompatible simbol '" + c + "'");
            }
        }
    }

    public String shortStringBingo() {
        return this.centene + this.fijo + this.corrido1 + this.corrido2;
    }

    public String simbolStringBingo(char simbol) {
        return this.centene + simbol + this.fijo + simbol + this.corrido1 + simbol + this.corrido2;
    }

    public String toString() {
        return simbolStringBingo('-');
    }

    public String getCentene() {
        return this.centene;
    }

    public String getFijo() {
        return this.fijo;
    }

    public String getCorrido1() {
        return this.corrido1;
    }

    public String getCorrido2() {
        return this.corrido2;
    }
}
