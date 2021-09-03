// Compile with:  javac Ecc.java
// Run with:      java Ecc

import java.security.*;
import java.security.spec.*;

public class Ecc {

    public static void main(String[] args) throws Exception {

        // Create a public and private key for secp224r1.
        KeyPairGenerator g = KeyPairGenerator.getInstance("EC","SunEC");
        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp224k1");
        g.initialize(ecsp);

        KeyPair kp = g.genKeyPair();
        PrivateKey privKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();

        // Select the signature algorithm.
        Signature s = Signature.getInstance("SHA256withECDSA","SunEC");
        s.initSign(privKey);

        // Compute the signature.
        byte[] msg = "Hello, World!".getBytes("UTF-8");
        byte[] sig;
        s.update(msg);
        sig = s.sign();

        // Verify the signature.
        Signature sg = Signature.getInstance("SHA256withECDSA", "SunEC");
        sg.initVerify(pubKey);
        sg.update(msg);
        boolean validSignature = sg.verify(sig);

        System.out.println(validSignature);
    }
}