package kiu.webmailnauta.legacy.utils;

public class StringConverter {
    public static byte[] convertStringToByte(String s) {
        byte[] b = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            b[i] = (byte) s.charAt(i);
        }
        return b;
    }

    public static String convertByteToString(byte[] b) {
        StringBuilder s = new StringBuilder();
        for (byte c : b) {
            s.append((char) c);
        }
        return s.toString();
    }
}
