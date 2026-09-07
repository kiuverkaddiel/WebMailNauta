package kiu.webmailnauta.legacy.parser;

public interface IParserFactory {
    public static final String BALL = "Bola";
    public static final String BALL_BOTE = "Bola Bote";
    public static final String CENTENE = "Centena";
    public static final String CENTENE_BOTE = "Centena Bote";
    public static final String PARLE = "Parlet";
    public static final String PARLE_BOTE = "Parlet Bote";

    Parser createParser(Cursor cursor);

    Parser getParserByLine(String str);

    Parser getParserByType(String str);
}
