const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');


// var keysize = 1024
// console.log("generating RSA " + keysize + "bit key pair...");
// kp = rs.KEYUTIL.generateKeypair("RSA", keysize);
// var prvKey = kp.prvKeyObj;
// var pubKey = kp.pubKeyObj;

// var prvKeyPEM, pubKeyPEM;
// prvKeyPEM = rs.KEYUTIL.getPEM(prvKey, "PKCS8PRV");
// pubKeyPEM = rs.KEYUTIL.getPEM(pubKey);
// console.log('Private key: ', prvKeyPEM)
// console.log('Public key: ', pubKeyPEM)

// rsu.saveFile('private_key_pkcs8.pem', prvKeyPEM)
// rsu.saveFile('public_key.pem', pubKeyPEM)
// console.log("done.");

var textOrFile = "185f8db32271fe25f561a6fc938b2e264306ec304eda518007d1764826381969"
var pubFile = "public_key_go.pem"
var sigFile = "fyWb0EdDoGZFkqTEqUvc7OKAElE4Ldm+RAu+gN3MRKTiWdi3WUETUxcfPO+7cUxrnlLAn2TwMAXSMJs79i0RMAM4fLYGfDKIZJRlGn6CRUG+6I/6U3G2/zFJirS7RiOzMlNjEe+5zQ7Okmae8Stg+e2L2OJ85i00lThdwKwoBOQ="
var hashAlg = "SHA256withRSA";

// 1. public key
var pubPEM = rsu.readFile(pubFile);
var pub = rs.KEYUTIL.getKey(pubPEM);
console.log(pub);

// 2. data to be verifid
var text;
try {
  text = rsu.readFile(textOrFile);
} catch(ex) {
  text = textOrFile;
}

// 3. load signature
var sig = new rs.KJUR.crypto.Signature({alg: hashAlg});
sig.init(pub);
sig.updateString(text);
var isValid = sig.verify(Buffer.from(sigFile, "base64").toString("hex"));

if (isValid) {
   console.log("signature is valid");
} else {
   console.log("signature is invalid");
}


class KeyGenerator {
  generateKeyPair() {
    var sig = new rs.KJUR.crypto.Signature({alg: hashAlg});
    return 
  }
}