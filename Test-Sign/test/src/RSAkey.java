import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.SecureRandom;

import javax.sound.midi.SysexMessage;

class RSAKey{

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
    
        return pair;
    }
    public static void parseKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, URISyntaxException {

        String privateKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("private_key_pkcs8.pem").toURI())));
        String publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.pem").toURI())));
        
        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replaceAll("\r", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replaceAll("\r", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        System.out.println(privKey);
        System.out.println(pubKey);
    }
    public static void main(String[] args){
        KeyPair kp = null;
        try{
            // kp = generateKeyPair();
            parseKey();
            
        } catch (Exception ex){
            System.out.println(ex.toString());;
        }
        // PrivateKey privatekey = kp.getPrivate();
        // byte[] privateByte = privatekey.getEncoded();
        // String privKeyStr = new String(Base64.getEncoder().encode(privateByte));
        // System.out.println(privKeyStr);
    }
}