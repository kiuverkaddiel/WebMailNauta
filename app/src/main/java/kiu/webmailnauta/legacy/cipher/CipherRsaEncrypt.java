package kiu.webmailnauta.legacy.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import javax.crypto.Cipher;

import kiu.webmailnauta.legacy.utils.MyBase64;
import kiu.webmailnauta.legacy.utils.StringConverter;

public class CipherRsaEncrypt {
    private static final String publicKey = "-----BEGIN RSA PUBLIC KEY-----\nMBgCEQCKi29WwLVIurbUf3zTdSkDAgMBAAE=\n-----END RSA PUBLIC KEY-----";

    public static String doFinal(String text) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher encrypt = Cipher.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            encrypt.init(1, RsaKeyHelper.parsePublicKey(publicKey));
            return StringConverter.convertByteToString(MyBase64.encode(encrypt.doFinal(text.getBytes()), 8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
