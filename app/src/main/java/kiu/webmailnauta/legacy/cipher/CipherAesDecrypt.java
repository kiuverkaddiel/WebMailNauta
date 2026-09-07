package kiu.webmailnauta.legacy.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import kiu.webmailnauta.legacy.utils.MyBase64;
import kiu.webmailnauta.legacy.utils.StringConverter;

public class CipherAesDecrypt {
    private static final String DEFAULT_KEY = "0cca9d2065bf9af7";
    private static final String DEFAULT_TRANSFER_KEY = "c38dbcd618cc0792";

    public static String doFinal(String text, String password) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(StringConverter.convertStringToByte(password), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKey);
            return StringConverter.convertByteToString(cipher.doFinal(MyBase64.decode(text, 10)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDefaultKey() {
        return DEFAULT_KEY;
    }

    public static String getDefaultTransferKey() {
        return DEFAULT_TRANSFER_KEY;
    }
}
