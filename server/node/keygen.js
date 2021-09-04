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

var textOrFile = "hello"
var pubFile = "public_key.pem"
var sigFile = "CRLxYTYa2vnNPsgyxhWYLcT+ubN+TWB8qnxpVIsa6O7ab7KeggUeUMlZNnrU1niKJYVozg4vz1bw94vULo/BYdE1Jou/akX1NSze1MiGErQcpMH5X21IuJdo6unI32nhto9jSISNR3gLNwbRLwokumhiuuDZyZ45A3VPSBxChXo="
var hashAlg = "SHA256withRSA";

test = () => {
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
}


// class KeyGenerator {
//   generateKeyPair() {
//     var sig = new rs.KJUR.crypto.Signature({alg: hashAlg});
//     return null
//   }
// }
var fromAddress = "admin"
var toAddress = "hung"
var amount = "10"

console.log(Buffer.from(fromAddress + toAddress + amount, 'utf-8').toString('hex'))
// WvNTdJpmAH5rctxl9mgF4q1GNqtwLGlkO2/XLBY10AKZJOnij2nODZ2IkXQCkwIF9ZdeqOgqKMY41Rr+F/akbiVh2Fs7wbHV9exP9CTajcbWiITLj5S8e06cQdlR4cAk2XdCOA7iWqxpSQFjhr7Bxnyxw2WWQRdgrwMIdFS+AUs=