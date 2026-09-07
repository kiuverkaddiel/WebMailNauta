package kiu.webmailnauta.legacy.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import kiu.webmailnauta.legacy.Constants;

public final class MyCipher {
    private static final String ALGORITHM = "AES";
    private static final String PASSWORD = "0cca9d2065bf9af70a234b2fb6b637dd";

    public static String Encrypt(String password, String text) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(convertStringToByte(password), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(1, secretKey);
            return Base64.encodeToString(c.doFinal(convertStringToByte(text)), 11);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static String EncryptWrap(String text) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(convertStringToByte(PASSWORD), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(1, secretKey);
            return Base64.encodeToString(c.doFinal(convertStringToByte(text)), 9);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static String Encrypt(String text) {
        return Encrypt(PASSWORD, text);
    }

    public static String Decrypt(String password, String text) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(convertStringToByte(password), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(2, secretKey);
            return convertByteToString(c.doFinal(Base64.decode(text, 11)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static String DecryptWrap(String text) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(convertStringToByte(PASSWORD), ALGORITHM);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(2, secretKey);
            return convertByteToString(c.doFinal(Base64.decode(text, 9)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public static String Decrypt(String text) {
        return Decrypt(PASSWORD, text);
    }

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

    public static String MD5(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(text.getBytes());
            return Base64.encodeToString(md5.digest(), Base64.NO_CLOSE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertBytesToHex(byte[] bytes) {
        if (bytes == null) {
            return Constants.NULL_STRING;
        }
        String hex = Constants.NULL_STRING;
        for (byte b : bytes) {
            int i = Math.abs(b);
            hex = hex + Integer.toHexString(i);
        }
        while (hex.length() < 32) {
            hex = hex + "0";
        }
        return hex;
    }

    public static String MD5Exh(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(text.getBytes());
            return convertBytesToHex(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
