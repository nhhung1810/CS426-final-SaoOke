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

}

module.exports.Verification = Verification