// const EC = require('elliptic').ec;
const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');
// You can use any elliptic curve you want
// const ec = new EC('secp256k1');

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

var textOrFile = "Hello"
var pubFile = "public_key.pem"
var sigFile = "STY6EEVDf411GBxu7bhE3/t6m3tKNchC106U+JI8df0J756aLEUZGCA0xetPW5OEI+vt5CeS552FbdTV4IUz9IF9J2bV/yTU64eC1Pj+jp4pGuXpXQUz0Ew0dp9WOaJPC5IZ9or5RsnfHgiDNwI8bdVLl0etv4BR5O1y3NsoCAw="
var hashAlg = "SHA256withRSA";

// 1. public key
var pubPEM = rsu.readFile(pubFile);
var pub = rs.KEYUTIL.getKey(pubPEM);
//console.log(pub);

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