package kiu.webmailnauta.legacy.cipher;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RsaKeyHelper {
    private static String BEGIN = "-----BEGIN";
    private static Pattern PEM_DATA = Pattern.compile("-----BEGIN (.*)-----(.*)-----END (.*)-----", 32);
    private static final Pattern SSH_PUB_KEY = Pattern.compile("ssh-(rsa|dsa) ([A-Za-z0-9/+]+=*) (.*)");

    RsaKeyHelper() {
    }

    public static KeyPair parseKeyPair(String pemData) {
        PublicKey publicKey;
        Matcher m = PEM_DATA.matcher(pemData.trim());
        if (m.matches()) {
            String type = m.group(1);
            byte[] content = Codecs.b64Decode(Codecs.utf8Encode(m.group(2)));
            PrivateKey privateKey = null;
            try {
                KeyFactory fact = KeyFactory.getInstance("RSA");
                if (type.equals("RSA PRIVATE KEY")) {
                    ASN1Sequence seq = ASN1Sequence.getInstance(content);
                    if (seq.size() == 9) {
                        RSAPrivateKey key = RSAPrivateKey.getInstance(seq);
                        publicKey = fact.generatePublic(new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent()));
                        privateKey = fact.generatePrivate(new RSAPrivateCrtKeySpec(key.getModulus(), key.getPublicExponent(), key.getPrivateExponent(), key.getPrime1(), key.getPrime2(), key.getExponent1(), key.getExponent2(), key.getCoefficient()));
                    } else {
                        throw new IllegalArgumentException("Invalid RSA Private Key ASN1 sequence.");
                    }
                } else if (type.equals("PUBLIC KEY")) {
                    publicKey = fact.generatePublic(new X509EncodedKeySpec(content));
                } else if (type.equals("RSA PUBLIC KEY")) {
                    RSAPublicKey key2 = RSAPublicKey.getInstance(ASN1Sequence.getInstance(content));
                    publicKey = fact.generatePublic(new RSAPublicKeySpec(key2.getModulus(), key2.getPublicExponent()));
                } else {
                    throw new IllegalArgumentException(type + " is not a supported format");
                }
                return new KeyPair(publicKey, privateKey);
            } catch (InvalidKeySpecException var11) {
                throw new RuntimeException(var11);
            } catch (NoSuchAlgorithmException var12) {
                throw new IllegalStateException(var12);
            }
        } else {
            throw new IllegalArgumentException("String is not PEM encoded data");
        }
    }

    public static java.security.interfaces.RSAPublicKey parsePublicKey(String key) {
        Matcher m = SSH_PUB_KEY.matcher(key);
        if (m.matches()) {
            String alg = m.group(1);
            String encKey = m.group(2);
            if ("rsa".equalsIgnoreCase(alg)) {
                return parseSSHPublicKey(encKey);
            }
            throw new IllegalArgumentException("Only RSA is currently supported, but algorithm was " + alg);
        } else if (!key.startsWith(BEGIN)) {
            return parseSSHPublicKey(key);
        } else {
            KeyPair kp = parseKeyPair(key);
            if (kp.getPublic() != null) {
                return (java.security.interfaces.RSAPublicKey) kp.getPublic();
            }
            throw new IllegalArgumentException("Key data does not contain a public key");
        }
    }

    private static java.security.interfaces.RSAPublicKey parseSSHPublicKey(String encKey) {
        byte[] PREFIX = {0, 0, 0, 7, 115, 115, 104, 45, 114, 115, 97};
        ByteArrayInputStream in = new ByteArrayInputStream(Codecs.b64Decode(Codecs.utf8Encode(encKey)));
        byte[] prefix = new byte[11];
        try {
            if (in.read(prefix) != 11 || !Arrays.equals(PREFIX, prefix)) {
                throw new IllegalArgumentException("SSH key prefix not found");
            }
            return createPublicKey(new BigInteger(readBigInteger(in)), new BigInteger(readBigInteger(in)));
        } catch (IOException var6) {
            throw new RuntimeException(var6);
        }
    }

    static java.security.interfaces.RSAPublicKey createPublicKey(BigInteger n, BigInteger e) {
        try {
            return (java.security.interfaces.RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(n, e));
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    private static byte[] readBigInteger(ByteArrayInputStream in) throws IOException {
        byte[] b = new byte[4];
        if (in.read(b) == 4) {
            int l = (b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3];
            byte[] b2 = new byte[l];
            if (in.read(b2) == l) {
                return b2;
            }
            throw new IOException("Expected " + l + " key bytes");
        }
        throw new IOException("Expected length data as 4 bytes");
    }
}
