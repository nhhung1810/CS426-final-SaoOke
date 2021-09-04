const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');
const { UserFactory } = require("./user.js")

class Verification{


    verify(msg, signature, publicKey, format = "base64"){
        var sig = new rs.KJUR.crypto.Signature({alg: "SHA256withRSA"});
        sig.init(publicKey);
        sig.updateString(msg);

        var isValid = null;
        if(format == "base64") isValid = sig.verify(Buffer.from(signature, "base64").toString("hex"));
        else if (format == "hex") isValid = sig.verify(signature);
        
        if (isValid) {
           return true;
        }
        return false;
    }

    parseKey(path, isFile = false){
        if(isFile == true) try{
            //file format
            var pubPEM = rsu.readFile(path);
            var pub = rs.KEYUTIL.getKey(pubPEM);
        } catch (ex) {
            return null;
        } else {
            try{
                //text format
                //require the key to be exactly in PEM format
                var pub = rs.KEYUTIL.getKey(path);
            } catch(ex) {
                return null;
            }
        }
        return pub;
    }

    keyToString(key){
        try{
            if(key.isPublic && !key.isPrivate) {
                var pubKeyPEM = rs.KEYUTIL.getPEM(key);
                return pubKeyPEM;
            } else if (key.isPrivate) {
                var prvKeyPEM = rs.KEYUTIL.getPEM(key, "PKCS8PRV");
                return prvKeyPEM;
            }
        } catch(ex) {
            console.log(ex.toString());
            return null;
        }
    }

    keyFromUser(userFactory, username){
        return this.parseKey(userFactory.getKey(username))
    }

}

module.exports.Verification = Verification

// Test parsing
const ver = new Verification;
const pub = ver.parseKey("public_key.pem-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGOllTab/mVTMs3353mOBwjDp+\nM6LYYHi+ttH7/diA5PA7ZqJ2NtOzZXWjdaCGrqT/f0vkjWxCzhb1UOGZsSH+jVhK\niAsag+n2e+xzOPoe7xfWqOn3fI2Rt9yGswJcPP0mHUWsnlOuew9T+yyC7RFEFTX7\nRnD6gyYD8gbWvlFfuwIDAQAB\n-----END PUBLIC KEY-----", false)
// console.log(pub)
const msgHex = "61646d696e68756e673130"
const sig = "WvNTdJpmAH5rctxl9mgF4q1GNqtwLGlkO2/XLBY10AKZJOnij2nODZ2IkXQCkwIF9ZdeqOgqKMY41Rr+F/akbiVh2Fs7wbHV9exP9CTajcbWiITLj5S8e06cQdlR4cAk2XdCOA7iWqxpSQFjhr7Bxnyxw2WWQRdgrwMIdFS+AUs="
console.log(ver.verify(msgHex, sig, pub, "base64"))


