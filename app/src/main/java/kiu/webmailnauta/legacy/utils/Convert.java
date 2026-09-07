package kiu.webmailnauta.legacy.utils;

import kiu.webmailnauta.legacy.Constants;

public class Convert {
    public static String stringToAscii(String str) {
        String str2 = str.toUpperCase();
        String ascii = Constants.NULL_STRING;
        for (char c : str2.toCharArray()) {
            ascii = ascii + charToAscii(c);
        }
        return ascii;
    }

    public static String charToAscii(char c) {
        return Integer.valueOf(c).toString();
    }

    public static String asciiToString(String ascii) {
        String name = Constants.NULL_STRING;
        for (int i = 0; i < ascii.length(); i += 2) {
            int aux = Integer.valueOf(String.valueOf(ascii.charAt(i)) + String.valueOf(ascii.charAt(i + 1))).intValue();
            name = name + ((char) aux);
        }
        return capitalize(name);
    }

    public static String capitalize(String str) {
        return str.toUpperCase().charAt(0) + str.toLowerCase().substring(1);
    }
}
