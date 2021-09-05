const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');

class Verification {

    verify(msg, signature, publicKey, format = "base64") {
        var sig = new rs.KJUR.crypto.Signature({ alg: "SHA256withRSA" });
        sig.init(publicKey);
        sig.updateString(msg);

        var isValid = null;
        if (format == "base64") isValid = sig.verify(Buffer.from(signature, "base64").toString("hex"));
        else if (format == "hex") isValid = sig.verify(signature);
        if (isValid) {
            return true;
        }
        return false;
    }

    parseKey(path, isFile = false) {
        if (isFile == true) try {
            //file format
            var pubPEM = rsu.readFile(path);
            var pub = rs.KEYUTIL.getKey(pubPEM);
        } catch (ex) {
            return null;
        } else {
            try {
                //text format
                //require the key to be exactly in PEM format
                var pub = rs.KEYUTIL.getKey(path);
            } catch (ex) {
                return null;
            }
        }
        return pub;
    }

    keyToString(key) {
        try {
            if (key.isPublic && !key.isPrivate) {
                var pubKeyPEM = rs.KEYUTIL.getPEM(key);
                return pubKeyPEM;
            } else if (key.isPrivate) {
                var prvKeyPEM = rs.KEYUTIL.getPEM(key, "PKCS8PRV");
                return prvKeyPEM;
            }
        } catch (ex) {
            console.log(ex.toString());
            return null;
        }
    }

}

module.exports.Verification = Verification

// Test parsing
// const ver = new Verification;
// const pub = ver.parseKey("public_key.pem-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGOllTab/mVTMs3353mOBwjDp+\nM6LYYHi+ttH7/diA5PA7ZqJ2NtOzZXWjdaCGrqT/f0vkjWxCzhb1UOGZsSH+jVhK\niAsag+n2e+xzOPoe7xfWqOn3fI2Rt9yGswJcPP0mHUWsnlOuew9T+yyC7RFEFTX7\nRnD6gyYD8gbWvlFfuwIDAQAB\n-----END PUBLIC KEY-----", false)
// // console.log(pub)
// const msgHex = "2d2d2d2d2d424547494e205055424c4943204b45592d2d2d2d2d0a4d4947664d413047435371475349623344514542415155414134474e4144434269514b42675144474f6c6c5461622f6d56544d73333335336d4f42776a44702b0a4d364c595948692b747448372f646941355041375a714a324e744f7a5a58576a646143477271542f6630766b6a5778437a686231554f475a7353482b6a56684b0a69417361672b6e32652b787a4f506f6537786657714f6e33664932527439794773774a635050306d485557736e6c4f75657739542b79794337524645465458370a526e44366779594438676257766c466675774944415141420a2d2d2d2d2d454e44205055424c4943204b45592d2d2d2d2d68756e673130"
// const sig = "ijA1JX6jnyd487Ba3ZsYCaan2XnuIqXkmx98HGpwFQkpzwzaZ3WskbJyFMGJkyogDYrnPqj+kCHL+qNJTEwE1gOsS3SWdG6+t78ce6eT0xFkJMS7N0Guu20ln9StCOio4pnKNz0ULH3epCn2VpfsDeS4/HcDJc4vKF2mUk1whM0="
// console.log(ver.verify(msgHex, sig, pub, "base64"))


