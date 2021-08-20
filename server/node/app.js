const express = require('express')
const { Blockchain, Transaction } = require('./blockchain');
const EC = require('elliptic').ec;
const ec = new EC('secp256k1');
const debug = require("debug")("main:debug")


const savjeeCoin = new Blockchain();
// Your private key goes here
const myKey = ec.keyFromPrivate('8955c93d5e5a33af207eed4907ec608ae85fbff89a6b6f795d36a49b26e29b01');
// From that we can calculate your public key (which doubles as your wallet address)
const myWalletAddress = myKey.getPublic('hex');


const app = express()
const port = 5000

app.use(express.json()) // for parsing application/json
app.use(express.urlencoded({ extended: true })) // for parsing application/x-www-form-urlencoded


app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
  test()
})

app.post("/history", function(req, res){
  if(!req.body || !req.body.address || req.body.address.length === 0){
    // if(req) console.log("Hmmm", req.body)
    res.status(404).send("There are no such user")
  }
  else {
    tmp = savjeeCoin.getBalanceOfAddress(myWalletAddress)
    // console.log(tmp, "wellll")
    res.status(200).send(tmp)
  }

  
})

/*
TEST SPACE
*/
test = () => {
    // Mine first block
  savjeeCoin.minePendingTransactions(myWalletAddress);

  // Create a transaction & sign it with your key
  const tx1 = new Transaction(myWalletAddress, 'address2', 100);
  tx1.signTransaction(myKey);
  savjeeCoin.addTransaction(tx1);

  // Mine block
  savjeeCoin.minePendingTransactions(myWalletAddress);

  // Create second transaction
  const tx2 = new Transaction(myWalletAddress, 'address1', 50);
  tx2.signTransaction(myKey);
  savjeeCoin.addTransaction(tx2);

  // Mine block
  savjeeCoin.minePendingTransactions(myWalletAddress);

  console.log();
  console.log(`Balance of xavier is ${savjeeCoin.getBalanceOfAddress(myWalletAddress)}`);

  // Uncomment this line if you want to test tampering with the chain
  // savjeeCoin.chain[1].transactions[0].amount = 10;

  // Check if the chain is valid
  console.log();
  console.log('Blockchain valid?', savjeeCoin.isChainValid() ? 'Yes' : 'No');
};
