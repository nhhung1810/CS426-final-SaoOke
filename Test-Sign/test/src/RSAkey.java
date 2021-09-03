import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.function.ObjIntConsumer;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.Signature;

import org.json.JSONObject;

class RSAKey{

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
    
        return pair;
    }

    //require 2 file pem
    public static KeyPair parseKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, URISyntaxException {

        String privateKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("private_key_pkcs8.pem").toURI())));
        String publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.pem").toURI())));
        
        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replaceAll("\r", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replaceAll("\r", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        
        // System.out.println(privKey);
        // System.out.println(pubKey);

        return new KeyPair(pubKey, privKey);
    }

    private static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes());
    
        byte[] signature = privateSignature.sign();
    
        return Base64.getEncoder().encodeToString(signature);
    }

    //This just for test, we don't need verify at the client
    private static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes());
    
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
    
        return publicSignature.verify(signatureBytes);
    }

    //General signing function, return standard information 
    public static JSONObject sign(String plaintext, KeyPair keypair) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("msg", plaintext);
        obj.put("publicKey", keypair.getPublic().toString());
        obj.put("signature", sign(plaintext, keypair.getPrivate()));
        obj.put("algo", "SHA256withRSA");
        return null;
    }
    
    public static void main(String[] args){
        KeyPair kp = null;
        try{
            // kp = generateKeyPair();
            kp = parseKey();
            
        } catch (Exception ex){
            System.out.println(ex.toString());;
        }
        try{
            String signature = sign("Hello", kp.getPrivate());
            System.out.println(signature);
            System.out.println("\n\n");
            if(verify("Hello", signature, kp.getPublic()))
            System.out.println("True msg");
            else
            System.out.println("Wrong msg");
        } catch (Exception ex){
            System.out.println(ex.toString());
        }
        
        
    }
}