import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;
// import org.web3j.crypto.Sign;
import java.math.BigInteger;

public class SignUtil {

    public static String compressPubKey(BigInteger pubKey) {
        String pubKeyYPrefix = pubKey.testBit(0) ? "03" : "02";
        String pubKeyHex = pubKey.toString(16);
        String pubKeyX = pubKeyHex.substring(0, 64);
        return pubKeyYPrefix + pubKeyX;
    }

    public static void main(String[] args) throws Exception {
        //BigInteger privKey = Keys.createEcKeyPair().getPrivateKey();
        BigInteger privKey = new BigInteger("97ddae0f3a25b92268175400149d65d6887b9cefaf28ea2c078e05cdc15a3c0a", 16);
        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
        System.out.println("Private key: " + privKey.toString(16));
        System.out.println("Public key: " + pubKey.toString(16));
        System.out.println("Public key (compressed): " + compressPubKey(pubKey));

        String msg = "Message for signing";
        byte[] msgHash = Hash.sha3(msg.getBytes());
        Sign.SignatureData signature = Sign.signMessage(msgHash, keyPair, false);
        System.out.println("Msg: " + msg);
        System.out.println("Msg hash: " + Hex.toHexString(msgHash));
        System.out.printf("Signature: [v = %d, r = %s, s = %s]\n",
                signature.getV() - 27,
                Hex.toHexString(signature.getR()),
                Hex.toHexString(signature.getS()));

        System.out.println();

        BigInteger pubKeyRecovered = Sign.signedMessageToKey(msg.getBytes(), signature);
        System.out.println("Recovered public key: " + pubKeyRecovered.toString(16));

        boolean validSig = pubKey.equals(pubKeyRecovered);
        System.out.println("Signature valid? " + validSig);
    }
}