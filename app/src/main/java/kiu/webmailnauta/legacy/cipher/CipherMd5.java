package kiu.webmailnauta.legacy.cipher;

import java.security.MessageDigest;


public class CipherMd5 {
    public static String doFinal(String text) {
        try {
            byte[] aux = MessageDigest.getInstance("MD5").digest(text.getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte aa : aux) {
                builder.append(Integer.toHexString(Math.abs(aa)));
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
