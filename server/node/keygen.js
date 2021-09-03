// const EC = require('elliptic').ec;
const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');
// You can use any elliptic curve you want
// const ec = new EC('secp256k1');

var keysize = 1024
console.log("generating RSA " + keysize + "bit key pair...");
kp = rs.KEYUTIL.generateKeypair("RSA", keysize);
var prvKey = kp.prvKeyObj;
var pubKey = kp.pubKeyObj;

var prvKeyPEM, pubKeyPEM;
prvKeyPEM = rs.KEYUTIL.getPEM(prvKey, "PKCS8PRV");
pubKeyPEM = rs.KEYUTIL.getPEM(pubKey);
console.log('Private key: ', prvKeyPEM)
console.log('Public key: ', pubKeyPEM)

rsu.saveFile('private_key_pkcs8.pem', prvKeyPEM)
rsu.saveFile('public_key.pem', pubKeyPEM)
console.log("done.");