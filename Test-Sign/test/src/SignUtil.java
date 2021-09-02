import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.jcajce.provider.keystore.BC;
import org.bouncycastle.jcajce.provider.symmetric.AES.KeyGen;
import org.bouncycastle.util.encoders.Hex;
import org.json.JSONObject;

// import jdk.jfr.events.ErrorThrownEvent;

/**
 *
 * @author metamug.com
 */
public class SignUtil {

    private static final String SPEC = "secp256k1";
    private static final String ALGO = "SHA256withECDSA";
    private static final ECGenParameterSpec ecSpec = new ECGenParameterSpec(SPEC);
    private static final String testPrivate = "8955c93d5e5a33af207eed4907ec608ae85fbff89a6b6f795d36a49b26e29b01";

    public static KeyPair keygen() 
    throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
        InvalidKeyException, UnsupportedEncodingException, SignatureException, NoSuchProviderException{

        KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
        g.initialize(ecSpec, new SecureRandom());
        KeyPair keypair = g.generateKeyPair();
        return keypair;
    }

    public static PrivateKey keyToValue(byte[] pkcs8key)
    throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
    InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, UnsupportedEncodingException {
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pkcs8key);
    KeyFactory factory = KeyFactory.getInstance("EC");
    PrivateKey privateKey = factory.generatePrivate(spec);
    return privateKey;
    }

    public static PrivateKey stringToKey(String privatekey)
    throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
    InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException{
        byte[] pkcs8key = null;
        PrivateKey pk = null;
        try {
            pkcs8key = Hex.decode(privatekey);
            pk = keyToValue(pkcs8key);
        } catch(Error e) {
            e.printStackTrace();
        }
        return pk;
    }

    public static JSONObject signing(){
        return null;
    }


    private JSONObject sender() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
                            InvalidKeyException, UnsupportedEncodingException, SignatureException, NoSuchProviderException {
 
        KeyPair keypair = keygen();
        PublicKey publicKey = keypair.getPublic();
        PrivateKey privateKey = keypair.getPrivate();

        String plaintext = "Hello";

        //...... sign
        Signature ecdsaSign = Signature.getInstance(ALGO);
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(plaintext.getBytes("UTF-8"));
        byte[] signature = ecdsaSign.sign();
        String pub = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String sig = Base64.getEncoder().encodeToString(signature);
        System.out.println(sig);
        System.out.println(pub);

        JSONObject obj = new JSONObject();
        obj.put("publicKey", pub);
        obj.put("signature", sig);
        obj.put("message", plaintext);
        obj.put("algorithm", ALGO);

        return obj;
    }

    private boolean receiver(JSONObject obj) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException {

        Signature ecdsaVerify = Signature.getInstance(obj.getString("algorithm"));
        KeyFactory kf = KeyFactory.getInstance("EC");

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")));

        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(obj.getString("message").getBytes("UTF-8"));
        boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(obj.getString("signature")));

        return result;
    }

     public static void main(String[] args){
        try{
            System.out.println("\n\n\n");
            byte[] privateKeyBytes = keygen().getPrivate().getEncoded();
            String privKeyStr = new String(Base64.getEncoder().encode(privateKeyBytes));
            System.out.println(privKeyStr);
            System.out.println(keygen().getPrivate().getFormat());
            // PrivateKey pk = SignUtil.stringToKey(testPrivate);
            // System.out.println(testPrivate);
            // System.out.println(pk.toString());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (InvalidKeySpecException ex) {
          //  Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (NoSuchProviderException ex) {
            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

}