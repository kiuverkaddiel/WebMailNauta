package kiu.webmailnauta.legacy.cipher;

class Base64 {
    private static byte EQUALS = 61;

    Base64() {
    }

    static byte[] encode(byte[] bytes) {
        return Base64Codec.encode(bytes);
    }

    static byte[] decode(byte[] bytes) {
        return Base64Codec.decode(bytes);
    }

    static byte[] urlEncode(byte[] bytes) {
        byte[] b64Bytes = Base64Codec.encodeBytesToBytes(bytes, 0, bytes.length, 16);
        int length = b64Bytes.length;
        while (b64Bytes[length - 1] == EQUALS) {
            length--;
        }
        byte[] result = new byte[length];
        System.arraycopy(b64Bytes, 0, result, 0, length);
        return result;
    }

    static byte[] urlDecode(byte[] b64) {
        byte[] b64Bytes;
        int lMod4 = b64.length % 4;
        if (lMod4 == 0) {
            b64Bytes = b64;
        } else if (lMod4 == 2) {
            b64Bytes = pad(b64, 2);
        } else if (lMod4 == 3) {
            b64Bytes = pad(b64, 1);
        } else {
            throw new IllegalArgumentException("Invalid Base64 string");
        }
        return Base64Codec.decode(b64Bytes, 0, b64Bytes.length, 16);
    }

    private static byte[] pad(byte[] bytes, int n) {
        int l = bytes.length;
        byte[] padded = new byte[(l + n)];
        System.arraycopy(bytes, 0, padded, 0, l);
        for (int i = l; i < l + n; i++) {
            padded[i] = EQUALS;
        }
        return padded;
    }
}
