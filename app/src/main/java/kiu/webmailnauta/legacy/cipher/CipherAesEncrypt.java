package kiu.webmailnauta.legacy.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import kiu.webmailnauta.legacy.utils.MyBase64;
import kiu.webmailnauta.legacy.utils.StringConverter;

public class CipherAesEncrypt {
    public static String doFinal(String text, String password) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, secretKey);
            return StringConverter.convertByteToString(MyBase64.encode(cipher.doFinal(text.getBytes()), 10));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
