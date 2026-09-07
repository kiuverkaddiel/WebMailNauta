package kiu.webmailnauta.legacy.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;

import kiu.webmailnauta.legacy.utils.MyBase64;
import kiu.webmailnauta.legacy.utils.StringConverter;

public class CipherRsaDecrypt {
    private static final String privateKey128 = "-----BEGIN RSA PRIVATE KEY-----\nMGECAQACEQCKi29WwLVIurbUf3zTdSkDAgMBAAECED842uGBRhi+I6wfYOyhLREC\nCQDCBuDRG3bBUQIJALbL6ElWo9ATAggZrVFZOvo3UQIIb1kcQPg+xvMCCCUr+EFc\ngpRP\n-----END RSA PRIVATE KEY-----";
    public static final String privateKey256 = "-----BEGIN RSA PRIVATE KEY-----\nMIGrAgEAAiEA0ppMUH2Ck5rXG5X0ASVhkkXiDkByffts576KNAbBckcCAwEAAQIg\nOkET+u+8vreF6MuBww9j/WEreaunWX/SyVt9Ot80nAECEQD5usVBonc5I4dVh3QM\niuohAhEA1+QGdzqZ+ewn8oVCLJtfZwIRAIC6C7AXhQlJDRvDZMiKWsECED8Zm7Yv\nf5+noYll35CQHRMCEQDGXlBHc/0lx40W2SV1lkbe\n-----END RSA PRIVATE KEY-----";

    public static String doFinal(String text) {
        return doFinal(text, privateKey128);
    }

    public static String doFinal(String text, String password) {
        try {
            Cipher decrypt = Cipher.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            decrypt.init(2, RsaKeyHelper.parseKeyPair(password).getPrivate());
            return StringConverter.convertByteToString(decrypt.doFinal(MyBase64.decode(StringConverter.convertStringToByte(text), 8)));
        } catch (Exception e) {
            return null;
        }
    }
}
