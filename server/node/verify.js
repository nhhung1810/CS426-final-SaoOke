const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');

class Verification{
    verify(msgHash, signature, publicKey, format = "base64"){
        var sig = new rs.KJUR.crypto.Signature({alg: "SHA256withRSA"});
        
        var publicPem = rs.KEYUTIL.getKey(publicKey)

        sig.init(publicPem);

        sig.updateString(msgHash);
        
        console.log(Buffer.from(signature, "base64").toString("hex"))
        
        var isValid = false;
        
        if (format == "base64") 
            isValid = sig.verify(Buffer.from(signature, "base64").toString("hex"));
        else if (format == "hex") 
            isValid = sig.verify(signature);
        
        if (isValid) {
           console.log("signature is valid");
        } else {
           console.log("signature is invalid");
        }
        return isValid
    }

    parsePublicKey(path, isFile = false){
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

    //can parse any key
    //but only for test
    //as private key don't reach
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

}

module.exports.Verification = Verification

//Test parsing
ver = new Verification;
pub = ver.parseKey("public_key.pem", true)
console.log(ver.keyToString(pub));