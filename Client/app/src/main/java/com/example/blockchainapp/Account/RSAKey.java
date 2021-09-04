package com.example.blockchainapp.Account;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.blockchainapp.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.Signature;
import java.io.FileNotFoundException;
import java.security.Key;

import org.json.JSONObject;

public class RSAKey{

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();

        return pair;
    }

    private static void writePemFile(Context context, Key key, String description, String filename)
            throws FileNotFoundException, IOException {
        PemFile pemFile = new PemFile(key, description);
        pemFile.write(context, filename);
    }

    public static void writePemFile(Context context, KeyPair keyPair, String name){
        String publicPath = name + "-public-key.pem";
        String privatePath = name + "-private-key.pem";
        try {
            writePemFile(context, keyPair.getPrivate(), "PRIVATE KEY", privatePath);
            writePemFile(context, keyPair.getPublic(), "PUBLIC KEY", publicPath);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.toString());
        }
    }

    //require 2 file pem
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static KeyPair parseKey(Context context, String publicPath, String privatePath) throws Exception{

        File file = new File(context.getFilesDir(), Constants.RESOURCE_LOCATION);
        // Log.d("path", file.getAbsolutePath() + "/" + privatePath);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/" + privatePath));
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            // Log.d("readline", line);
            sb.append(line);
            sb.append('\n');
        }

        String privateKeyContent = sb.toString();

        bufferedReader.close();
        isr.close();

        isr = new InputStreamReader(new FileInputStream(file.getAbsolutePath() + "/" + publicPath));
        bufferedReader = new BufferedReader(isr);
        sb = new StringBuilder();
        line = "";
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }

        String publicKeyContent = sb.toString();

        bufferedReader.close();
        isr.close();

        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replaceAll("\r", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replaceAll("\r", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        System.out.println(privKey);
        System.out.println(pubKey);

        return new KeyPair(pubKey, privKey);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static KeyPair parseKey(Context context, String name){
        String publicPath = name + "-public-key.pem";
        String privatePath = name + "-private-key.pem";
        try {
            Log.d("Test", "Opened for input");
            return parseKey(context, publicPath, privatePath);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.toString());
            return null;
        }
    }

    //only for testing
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static KeyPair parseKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, URISyntaxException {



        String privateKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("private_key_pkcs8.pem").toURI())));
        String publicKeyContent = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("public_key.pem").toURI())));

        // Log.d("Path", String.valueOf(Paths.get(ClassLoader.getSystemResource("public_key.pem").toURI())));

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes());

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    //This just for test, we don't need verify at the client
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }

    //General signing function, return standard information 
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static JSONObject sign(String plaintext, KeyPair keypair) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("msg", plaintext);
        obj.put("publicKey", keypair.getPublic().toString());
        obj.put("signature", sign(plaintext, keypair.getPrivate()));
        obj.put("algo", "SHA256withRSA");
        return obj;
    }

    public static void main(String[] args){
        // KeyPair kp = null;
        try {
            // kp = generateKeyPair();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.toString());
        }
        // writePemFile(kp, "hung");

        //The key need to be move into the src before ready to parse
        //t khong biet trong android m se luu sao nen la m check code roi adapt lai nha
        // try{
        //     kp = parseKey("hung");
        // } catch(Exception ex){
        //     System.out.println(ex.toString());
    }
}
