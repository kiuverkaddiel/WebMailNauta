package kiu.webmailnauta.legacy.cipher;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

public class Codecs {
    private static Charset UTF8 = Charset.forName("UTF-8");

    public static byte[] b64Encode(byte[] bytes) {
        return Base64Codec.encode(bytes);
    }

    public static byte[] b64Decode(byte[] bytes) {
        return Base64Codec.decode(bytes);
    }

    public static byte[] b64UrlEncode(byte[] bytes) {
        return Base64.urlEncode(bytes);
    }

    public static byte[] b64UrlEncode(CharSequence value) {
        return b64UrlEncode(utf8Encode(value));
    }

    public static byte[] b64UrlDecode(byte[] bytes) {
        return Base64.urlDecode(bytes);
    }

    public static byte[] b64UrlDecode(CharSequence value) {
        return b64UrlDecode(utf8Encode(value));
    }

    public static byte[] utf8Encode(CharSequence string) {
        try {
            ByteBuffer bytes = UTF8.newEncoder().encode(CharBuffer.wrap(string));
            byte[] bytesCopy = new byte[bytes.limit()];
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());
            return bytesCopy;
        } catch (CharacterCodingException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String utf8Decode(byte[] bytes) {
        return utf8Decode(ByteBuffer.wrap(bytes));
    }

    public static String utf8Decode(ByteBuffer bytes) {
        try {
            return UTF8.newDecoder().decode(bytes).toString();
        } catch (CharacterCodingException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static char[] hexEncode(byte[] bytes) {
        return Hex.encode(bytes);
    }

    public static byte[] hexDecode(CharSequence s) {
        return Hex.decode(s);
    }

    public static byte[] concat(byte[]... arrays) {
        int size = 0;
        byte[][] var2 = arrays;
        int index = arrays.length;
        for (int var4 = 0; var4 < index; var4++) {
            size += var2[var4].length;
        }
        byte[] result = new byte[size];
        int index2 = 0;
        byte[][] var9 = arrays;
        int var10 = arrays.length;
        for (int var6 = 0; var6 < var10; var6++) {
            byte[] a = var9[var6];
            System.arraycopy(a, 0, result, index2, a.length);
            index2 += a.length;
        }
        return result;
    }
}
