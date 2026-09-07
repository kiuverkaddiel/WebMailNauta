package kiu.webmailnauta.legacy.cipher;

import kiu.webmailnauta.legacy.Constants;

class Hex {
    private static final char[] HEX = {Constants.NUMBER0, Constants.NUMBER1, Constants.NUMBER2, Constants.NUMBER3, Constants.NUMBER4, Constants.NUMBER5, Constants.NUMBER6, Constants.NUMBER7, Constants.NUMBER8, Constants.NUMBER9, 'a', 'b', 'c', 'd', 'e', 'f'};

    Hex() {
    }

    static char[] encode(byte[] bytes) {
        int nBytes = bytes.length;
        char[] result = new char[(nBytes * 2)];
        int j = 0;
        for (int i = 0; i < nBytes; i++) {
            char[] cArr = HEX;
            result[j] = cArr[(bytes[i] & 240) >>> 4];
            result[j + 1] = cArr[bytes[i] & 15];
            j += 2;
        }
        return result;
    }

    static byte[] decode(CharSequence s) {
        int nChars = s.length();
        if (nChars % 2 == 0) {
            byte[] result = new byte[(nChars / 2)];
            for (int i = 0; i < nChars; i += 2) {
                int msb = Character.digit(s.charAt(i), 16);
                int lsb = Character.digit(s.charAt(i + 1), 16);
                if (msb <= 0 || lsb <= 0) {
                    throw new IllegalArgumentException("Non-hex character in input: " + s);
                }
                result[i / 2] = (byte) ((msb << 4) | lsb);
            }
            return result;
        }
        throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
    }
}
