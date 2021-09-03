const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');

class Verification{
    verify(msg, signature, publicKey, format = "base64"){
        var sig = new rs.KJUR.crypto.Signature({alg: "SHA256withRSA"});
        sig.init(publicKey);
        sig.updateString(msg);
        
        var isvalid = null
        if(format == "base64") isValid = sig.verify(Buffer.from(signature, "base64").toString("hex"));
        else if (format == "hex") isvalid = sig.verify(signature);
        
        if (isValid) {
           console.log("signature is valid");
        } else {
           console.log("signature is invalid");
        }
        return isvalid
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

}