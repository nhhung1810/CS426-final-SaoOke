const EC = require('elliptic').ec;

// You can use any elliptic curve you want
const ec = new EC('secp256k1');

// Generate a new key pair and convert them to hex-strings
const key = ec.genKeyPair();
const publicKey = key.getPublic('hex');
const privateKey = key.getPrivate('hex');

// Print the keys to the console
console.log();
console.log('Your public key (also your wallet address, freely shareable)\n', publicKey);

console.log();
console.log('Your private key (keep this secret! To sign transactions)\n', privateKey);
// check if pub can be derived from pri
// tmp = ec.keyFromPrivate(privateKey).getPublic("hex")
// console.log()
// if(tmp == publicKey) console.log("Can be derived")
// else {
//     console.log("Something messed up")
//     console.log("Public-1", publicKey)
//     console.log("tmp: ", tmp)
// }


console.log(ec.keyFromPrivate("8955c93d5e5a33af207eed4907ec608ae85fbff89a6b6f795d36a49b26e29b01").getPublic("hex"))
