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
var fromAddress = "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGOllTab/mVTMs3353mOBwjDp+\nM6LYYHi+ttH7/diA5PA7ZqJ2NtOzZXWjdaCGrqT/f0vkjWxCzhb1UOGZsSH+jVhK\niAsag+n2e+xzOPoe7xfWqOn3fI2Rt9yGswJcPP0mHUWsnlOuew9T+yyC7RFEFTX7\nRnD6gyYD8gbWvlFfuwIDAQAB\n-----END PUBLIC KEY-----"
var toAddress = "hung"
var amount = "10"

console.log(Buffer.from(fromAddress + toAddress + amount, 'utf-8').toString('hex'))
//ijA1JX6jnyd487Ba3ZsYCaan2XnuIqXkmx98HGpwFQkpzwzaZ3WskbJyFMGJkyogDYrnPqj+kCHL+qNJTEwE1gOsS3SWdG6+t78ce6eT0xFkJMS7N0Guu20ln9StCOio4pnKNz0ULH3epCn2VpfsDeS4/HcDJc4vKF2mUk1whM0= /