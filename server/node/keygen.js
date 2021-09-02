const EC = require('elliptic').ec;

// You can use any elliptic curve you want
const ec = new EC('secp256k1');

// Generate a new key pair and convert them to hex-strings
const key = ec.genKeyPair();
const publicKey = key.getPublic('hex');
const privateKey = key.getPrivate('hex');


// Print the keys to the console
console.log();
// console.log('Your public key (also your wallet address, freely shareable)\n', publicKey);

console.log();
// tmp = Buffer.from(key.getPrivate('hex')).toString('hex')
console.log('Your private key (keep this secret! To sign transactions)\n', Buffer.from(key.getPrivate('hex'), 'hex').toString('base64'));
// check if pub can be derived from pri
// tmp = ec.keyFromPrivate(privateKey).getPublic("hex")
// console.log()
// if(tmp == publicKey) console.log("Can be derived")
// else {
//     console.log("Something messed up")
//     console.log("Public-1", publicKey)
//     console.log("tmp: ", tmp)
// }

const base64key = 'G3M0b/LnQmNjrdVDH77HnhiV7aDpuzfk5x8twyyizMM=';

const base64tohex = (base64key) => {
    return Buffer.from(base64key, 'base64').toString('hex');
}

const keyPairGen = (base64key) => {
    let resKey = ec.keyFromPrivate(base64tohex(base64key))
    return resKey;
}

const hexto64 = (hexkey) => {
    return Buffer.from(hexkey, 'hex').toString('base64')
}



console.log(hexto64(keyPairGen(base64key).getPrivate('hex')))