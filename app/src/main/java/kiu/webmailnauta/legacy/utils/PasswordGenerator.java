package kiu.webmailnauta.legacy.utils;

import java.util.Random;

public class PasswordGenerator {
    public static String getPass(int size) {
        char[] chars = "0123456789abcdef".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            sb.append(chars[random.nextInt(chars.length)]);
        }
        return sb.toString();
    }
}
